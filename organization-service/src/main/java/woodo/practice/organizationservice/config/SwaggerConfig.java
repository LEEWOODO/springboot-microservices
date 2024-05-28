package woodo.practice.organizationservice.config;

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
		title = "Organization Service API",
		description = "Organization Service API v1.0",
		version = "1.0",
		contact = @Contact(
			name = "Woodo",
			email = "",
			url = ""
		),
		license = @License(
			name = "Apache 2.0",
			url = "https://www.apache.org/licenses/LICENSE-2.0"
		)
	),
	externalDocs = @ExternalDocumentation(
		description = "Organization Service API Documentation",
		url = "https://www.google.com"
	)
)
@Configuration
public class SwaggerConfig {
}
