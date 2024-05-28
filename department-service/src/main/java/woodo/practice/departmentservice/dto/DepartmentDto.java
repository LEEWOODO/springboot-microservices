package woodo.practice.departmentservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
	description = "DepartmentDto Model Information"
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {
	@Schema(description = "Department ID", example = "1")
	private Long id;

	@Schema(description = "Department Name", example = "IT")
	@NotNull(message = "Department Name cannot be null")
	private String departmentName;

	@Schema(description = "Department Description", example = "Information Technology")
	@NotNull(message = "Department Description cannot be null")
	private String departmentDescription;

	@Schema(description = "Department Code", example = "IT001")
	@NotNull(message = "Department Code cannot be null")
	private String departmentCode;
}
