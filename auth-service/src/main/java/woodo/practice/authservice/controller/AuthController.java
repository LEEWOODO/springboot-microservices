package woodo.practice.authservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

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

}
