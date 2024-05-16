package woodo.practice.employeeservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Project        : springboot-microservices
 * DATE           : 2024/05/16
 * AUTHOR         : dnejdzlr2 (Woodo Lee)
 * EMAIL          : dnejdzlr2@virnect.com
 * DESCRIPTION    :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/16      dnejdzlr2          최초 생성
 */
@RefreshScope
@RestController
public class MessageController {

	@Value("${spring.boot.message}")
	private String message;

	@GetMapping("message")
	public String message(){
		return message;
	}
}
