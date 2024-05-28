package woodo.practice.employeeservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Project        : springboot-microservices
 * DATE           : 2024/05/13
 * AUTHOR         : dnejdzlr2 (Woodo Lee)
 * EMAIL          : dnejdzlr2@virnect.com
 * DESCRIPTION    :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/13      dnejdzlr2          최초 생성
 */
@Schema(description = "API Response Data Transfer Object")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class APIResponseDto {
	private EmployeeDto employee;
	private DepartmentDto department;
	private OrganizationDto organization;
}
