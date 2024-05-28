package woodo.practice.employeeservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Employee Data Transfer Object")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    @Schema(description = "Employee ID", example = "1")
    private Long id;

    @Schema(description = "First Name", example = "John")
    private String firstName;

    @Schema(description = "Last Name", example = "Doe")
    private String lastName;

    @Schema(description = "Email", example = "adb@naver.com")
    private String email;

    @Schema(description = "Department Code", example = "D001")
    private String departmentCode;

    @Schema(description = "Organization Code", example = "ABC_ORG")
    private String organizationCode;
}
