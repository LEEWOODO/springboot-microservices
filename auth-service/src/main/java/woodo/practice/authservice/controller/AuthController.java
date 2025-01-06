package woodo.practice.authservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import woodo.practice.authservice.dto.request.LoginRequest;
import woodo.practice.authservice.dto.request.TokenRefreshRequest;
import woodo.practice.authservice.dto.response.LoginResponse;
import woodo.practice.authservice.dto.response.TokenRefreshResponse;
import woodo.practice.authservice.service.AuthService;

/**
 * Project        : springboot-microservices
 * DATE           : 2024. 12. 31.
 * AUTHOR         : dnejdzlr2 (Woodo Lee)
 * EMAIL          : dnejdzlr2@virnect.com
 * DESCRIPTION    :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024. 12. 31.      dnejdzlr2          최초 생성
 */
@Tag(name = "Auth", description = "Auth Rest API")
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}

	@PostMapping("/refresh")
	public ResponseEntity<TokenRefreshResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
		return ResponseEntity.ok(authService.refreshToken(request));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
		authService.logout(token.replace("Bearer ", ""));
		return ResponseEntity.ok().build();
	}
}
