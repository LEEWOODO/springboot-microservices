package woodo.practice.employeeservice.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Organization Data Transfer Object")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDto {
    @Schema(description = "Organization ID", example = "1")
    private Long id;

    @Schema(description = "Organization Name", example = "ABC Name")
    private String organizationName;

    @Schema(description = "Organization Description", example = "ABC Description")
    private String organizationDescription;

    @Schema(description = "Organization Code", example = "ABC_ORG")
    private String organizationCode;

    @Schema(description = "Created Date", example = "2021-07-01T10:00:00")
    private LocalDateTime createdDate;
}
