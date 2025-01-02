package woodo.practice.authservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import woodo.practice.authservice.dto.request.LoginRequest;
import woodo.practice.authservice.dto.response.LoginResponse;
import woodo.practice.authservice.global.util.JwtUtil;
import woodo.practice.authservice.security.JwtTokenProvider;

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
@AllArgsConstructor
public class AuthController {
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider tokenProvider;

	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}

	// sign up
	@PostMapping("/signup")
	public String signUp() {
		return "sign up";
	}

	// sign in
	@PostMapping("/signin")
	public String signIn() {
		return "sign in";
	}

	@GetMapping("/check")
	public String check(HttpServletRequest request){
		// 포트 확인용
		return "Employee Service is working on port " + request.getServerPort();
	}

	// @PostMapping("/login")
	// public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
	// 	// 사용자 인증 로직
	// 	if (isValidUser(loginRequest.getUsername(), loginRequest.getPassword())) {
	// 		String accessToken = JwtUtil.generateToken(loginRequest.getUsername());
	// 		return ResponseEntity.ok(new LoginResponse(accessToken, "refreshToken", 3600L));
	// 	} else {
	// 		return ResponseEntity.status(401).body("Invalid credentials");
	// 	}
	// }
	//
	// private boolean isValidUser(String username, String password) {
	// 	// 실제 사용자 인증 로직 (DB 조회 등)
	// 	return "test_user".equals(username) && "password123".equals(password);
	// }

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				loginRequest.getUsername(),
				loginRequest.getPassword()
			)
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken((UserDetails) authentication.getPrincipal());

		return ResponseEntity.ok(LoginResponse.builder()
			.accessToken(jwt)
			.tokenType("Bearer")
			.username(loginRequest.getUsername())
			.build());
	}
}
