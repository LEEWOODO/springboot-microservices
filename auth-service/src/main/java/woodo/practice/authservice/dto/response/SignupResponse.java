package woodo.practice.authservice.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * Project        : springboot-microservices
 * DATE           : 2025. 1. 6.
 * AUTHOR         : dnejdzlr2 (Woodo Lee)
 * EMAIL          : dnejdzlr2@virnect.com
 * DESCRIPTION    :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 1. 6.      dnejdzlr2          최초 생성
 */
@Getter
@Builder
public class SignupResponse {
	private String username;
	private String email;
	private String message;
}
