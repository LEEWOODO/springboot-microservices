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
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;


	public LoginResponse login (LoginRequest loginRequest) {
		User user = userRepository.findByUsername(loginRequest.getUsername())
			.orElseThrow(() -> new IllegalArgumentException("User not found."));

		if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			throw new IllegalArgumentException("Invalid password");
		}

		return LoginResponse.builder()
			.accessToken(jwtTokenProvider.generateToken(user))
			.refreshToken(jwtTokenProvider.generateRefreshToken(user))
			.tokenType("Bearer")
			.build();
	}

	public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
		String username = jwtTokenProvider.validateRefreshToken(request.getRefreshToken());
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new RuntimeException("User not found"));

		return TokenRefreshResponse.builder()
			.accessToken(jwtTokenProvider.generateToken(user))
			.tokenType("Bearer")
			.build();
	}
}
