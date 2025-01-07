package woodo.practice.authservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
	@NotBlank
	@Size(min = 4, max = 20)
	private String username;

	@NotBlank
	@Size(min = 6, max = 40)
	private String password;

	@NotBlank
	@Email
	private String email;
}
