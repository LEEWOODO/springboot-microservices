package woodo.practice.organizationservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import woodo.practice.organizationservice.dto.OrganizationDto;
import woodo.practice.organizationservice.service.OrganizationService;

@Tag(name = "Organization", description = "Organization Rest API")
@RestController
@RequestMapping("api/organizations")
@AllArgsConstructor
public class OrganizationController {

	private OrganizationService organizationService;

	@Operation(summary = "Save Organization", description = "Save Organization")
	@ApiResponse(responseCode = "201", description = "Organization saved successfully")
	@PostMapping
	public ResponseEntity<OrganizationDto> saveOrganization(@RequestBody OrganizationDto organizationDto) {
		OrganizationDto savedOrganization = organizationService.saveOrganization(organizationDto);
		return new ResponseEntity<>(savedOrganization, HttpStatus.CREATED);
	}

	@Operation(summary = "Get Organization", description = "Get Organization by organization code")
	@ApiResponse(responseCode = "200", description = "Organization found successfully")
	@GetMapping("{code}")
	public ResponseEntity<OrganizationDto> getOrganization(@PathVariable("code") String organizationCode) {
		OrganizationDto organizationDto = organizationService.getOrganizationByCode(organizationCode);
		return ResponseEntity.ok(organizationDto);
	}

	@GetMapping("/check")
	public String check(HttpServletRequest request){
		// 포트 확인용
		return "Organization Service is working on port " + request.getServerPort();
	}

}
