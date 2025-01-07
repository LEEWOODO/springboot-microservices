package woodo.practice.authservice.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import woodo.practice.authservice.domain.User;
import woodo.practice.authservice.dto.request.LoginRequest;
import woodo.practice.authservice.dto.request.SignupRequest;
import woodo.practice.authservice.dto.request.TokenRefreshRequest;
import woodo.practice.authservice.dto.response.LoginResponse;
import woodo.practice.authservice.dto.response.SignupResponse;
import woodo.practice.authservice.dto.response.TokenRefreshResponse;
import woodo.practice.authservice.global.util.JwtTokenProvider;
import woodo.practice.authservice.repository.LoginAttemptRepository;
import woodo.practice.authservice.repository.LogoutTokenRepository;
import woodo.practice.authservice.repository.RefreshTokenRepository;
import woodo.practice.authservice.repository.TokenReplayRepository;
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
	private final LoginAttemptRepository loginAttemptRepository;
	private final TokenReplayRepository tokenReplayRepository;
	private final LogoutTokenRepository logoutTokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;

	public LoginResponse login(LoginRequest loginRequest) {
		// 계정 잠금 확인
		if (loginAttemptRepository.isAccountLocked(loginRequest.getUsername())) {
			throw new IllegalStateException("Account is locked. Please try again later.");
		}

		try {
			User user = userRepository.findByUsername(loginRequest.getUsername())
				.orElseThrow(() -> new IllegalArgumentException("User not found."));

			if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
				throw new IllegalArgumentException("Invalid password");
			}

			// 로그인 성공시 실패 횟수 초기화
			loginAttemptRepository.resetAttempts(user.getUsername());

			// 기존 리프레시 토큰 삭제
			refreshTokenRepository.deleteByUsername(user.getUsername());

			String accessToken = jwtTokenProvider.generateToken(user);
			String refreshToken = jwtTokenProvider.generateRefreshToken(user);

			// Redis에 리프레시 토큰 저장
			refreshTokenRepository.save(user.getUsername(), refreshToken, jwtTokenProvider.getRefreshTokenExpiration());

			return LoginResponse.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.tokenType("Bearer")
				.build();
		} catch (IllegalArgumentException e) {
			loginAttemptRepository.recordFailedAttempt(loginRequest.getUsername());
			throw e;
		}
	}

	public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
		// 토큰 재사용 감지
		if (tokenReplayRepository.isTokenReused(request.getRefreshToken())) {
			String username = jwtTokenProvider.validateRefreshToken(request.getRefreshToken());
			loginAttemptRepository.lockAccount(username);
			refreshTokenRepository.deleteByUsername(username);
			throw new IllegalStateException("Token reuse detected. Account has been locked.");
		}

		// 리프레시 토큰 검증
		String username = jwtTokenProvider.validateRefreshToken(request.getRefreshToken());

		// Redis에서 저장된 리프레시 토큰 확인
		String savedToken = refreshTokenRepository.findByUsername(username)
			.orElseThrow(() -> new IllegalArgumentException("Refresh token not found"));

		if (!savedToken.equals(request.getRefreshToken())) {
			loginAttemptRepository.lockAccount(username);
			throw new IllegalStateException("Invalid refresh token. Account has been locked.");
		}

		// 사용된 토큰 마킹
		tokenReplayRepository.markTokenAsUsed(request.getRefreshToken());

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

	public SignupResponse signup(@Valid SignupRequest request) {
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new IllegalArgumentException("Username already exists");
		}

		if (userRepository.existsByEmail(request.getEmail())) {
			throw new IllegalArgumentException("Email already exists");
		}

		User user = User.builder()
			.username(request.getUsername())
			.email(request.getEmail())
			.password(passwordEncoder.encode(request.getPassword()))
			.role("USER")
			.build();

		userRepository.save(user);

		return SignupResponse.builder()
			.username(user.getUsername())
			.email(user.getEmail())
			.message("User registered successfully")
			.build();
	}
}
