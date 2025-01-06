package woodo.practice.authservice.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import woodo.practice.authservice.domain.User;
import woodo.practice.authservice.dto.request.LoginRequest;
import woodo.practice.authservice.dto.request.TokenRefreshRequest;
import woodo.practice.authservice.dto.response.LoginResponse;
import woodo.practice.authservice.dto.response.TokenRefreshResponse;
import woodo.practice.authservice.global.util.JwtTokenProvider;
import woodo.practice.authservice.repository.LogoutTokenRepository;
import woodo.practice.authservice.repository.RefreshTokenRepository;
import woodo.practice.authservice.repository.UserRepository;

/**
 * Project        : springboot-microservices
 * DATE           : 2025. 1. 3.
 * AUTHOR         : dnejdzlr2 (Woodo Lee)
 * EMAIL          : dnejdzlr2@virnect.com
 * DESCRIPTION    :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 1. 3.      dnejdzlr2          최초 생성
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final LogoutTokenRepository logoutTokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;

	public LoginResponse login(LoginRequest loginRequest) {
		User user = userRepository.findByUsername(loginRequest.getUsername())
			.orElseThrow(() -> new IllegalArgumentException("User not found."));

		if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			throw new IllegalArgumentException("Invalid password");
		}

		// 기존 리프레시 토큰 삭제
		refreshTokenRepository.deleteByUsername(user.getUsername());

		// Redis에 리프레시 토큰 저장
		refreshTokenRepository.save(user.getUsername(), jwtTokenProvider.generateRefreshToken(user), jwtTokenProvider.getRefreshTokenExpiration());

		return LoginResponse.builder()
			.accessToken(jwtTokenProvider.generateToken(user))
			.refreshToken(jwtTokenProvider.generateRefreshToken(user))
			.tokenType("Bearer")
			.build();
	}

	public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
		// 리프레시 토큰 검증
		String username = jwtTokenProvider.validateRefreshToken(request.getRefreshToken());

		// Redis에서 저장된 리프레시 토큰 확인
		String savedToken = refreshTokenRepository.findByUsername(username)
			.orElseThrow(() -> new IllegalArgumentException("Refresh token not found"));

		if (!savedToken.equals(request.getRefreshToken())) {
			throw new IllegalArgumentException("Refresh token mismatch");
		}

		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

		// 새로운 액세스 토큰과 리프레시 토큰 생성 (Rotation)
		String newAccessToken = jwtTokenProvider.generateToken(user);
		String newRefreshToken = jwtTokenProvider.generateRefreshToken(user);

		// 새로운 리프레시 토큰을 Redis에 저장
		refreshTokenRepository.save(username, newRefreshToken, jwtTokenProvider.getRefreshTokenExpiration());

		return TokenRefreshResponse.builder()
			.accessToken(newAccessToken)
			.refreshToken(newRefreshToken)
			.tokenType("Bearer")
			.build();
	}

	public void logout(String accessToken) {
		// 토큰에서 사용자 정보 추출
		String username = jwtTokenProvider.getUsernameFromToken(accessToken);

		// 리프레시 토큰 삭제
		refreshTokenRepository.deleteByUsername(username);

		// 액세스 토큰을 블랙리스트에 추가
		long remainingTime = jwtTokenProvider.getRemainingTime(accessToken);
		logoutTokenRepository.saveLogoutToken(accessToken, remainingTime);
	}
}
