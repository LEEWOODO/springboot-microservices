package woodo.practice.organizationservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "Organization Data Transfer Object")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDto {
    @Schema(description = "Organization ID", example = "1")
    private Long id;

    @Schema(description = "Organization Name", example = "ABC NAME")
    private String organizationName;

    @Schema(description = "Organization Description", example = "ABC Description")
    private String organizationDescription;

    @Schema(description = "Organization Code", example = "ABC_ORG")
    private String organizationCode;

    @Schema(description = "Created Date", example = "2021-07-01T00:00:00")
    private LocalDateTime createdDate;
}
