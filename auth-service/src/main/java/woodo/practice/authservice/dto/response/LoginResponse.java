package woodo.practice.authservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Project        : springboot-microservices
 * DATE           : 2025. 1. 2.
 * AUTHOR         : dnejdzlr2 (Woodo Lee)
 * EMAIL          : dnejdzlr2@virnect.com
 * DESCRIPTION    :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 1. 2.      dnejdzlr2          최초 생성
 */
@AllArgsConstructor
@Getter
public class LoginResponse {
	private String accessToken;
	private String refreshToken;
	private long expiresIn;
}
