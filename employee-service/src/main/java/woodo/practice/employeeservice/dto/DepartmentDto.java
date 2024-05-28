package woodo.practice.employeeservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "Department Data Transfer Object")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DepartmentDto {

    @Schema(description = "Department ID", example = "1")
    private Long id;

    @Schema(description = "Department Name", example = "IT")
    private String departmentName;

    @Schema(description = "Department Description", example = "Information Technology")
    private String departmentDescription;

    @Schema(description = "Department Code", example = "IT001")
    private String departmentCode;
}
