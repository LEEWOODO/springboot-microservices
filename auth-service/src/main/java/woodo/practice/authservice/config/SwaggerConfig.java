package woodo.practice.authservice.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

/**
 * Project        : springboot-microservices
 * DATE           : 5/28/24
 * AUTHOR         : dnejdzlr2 (Woodo Lee)
 * EMAIL          : dnejdzlr2@virnect.com
 * DESCRIPTION    :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 5/28/24      dnejdzlr2          최초 생성
 */
@OpenAPIDefinition(
	info = @Info(
		title = "Authorization Service API",
		description = "Documentation Authorization API v1.0",
		version = "1.0",
		contact = @Contact(
			name = "Woodo",
			email = "Woodo@gmail.com",
			url = "https://www.naver.com"
		),
		license = @License(
			name = "Apache 2.0",
			url = "https://www.apache.org/licenses/LICENSE-2.0"
		)
	),
	externalDocs = @ExternalDocumentation(
		description = "Authorization Service Wiki",
		url = "https://www.google.com"
	)
)
@Configuration
public class SwaggerConfig {
}
