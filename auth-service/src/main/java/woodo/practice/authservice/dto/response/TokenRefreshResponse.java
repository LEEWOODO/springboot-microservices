package woodo.practice.authservice.dto.response;

import lombok.Builder;
import lombok.Data;

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
@Data
@Builder
public class TokenRefreshResponse {
	private String accessToken;
	private String refreshToken;
	private String tokenType;
}