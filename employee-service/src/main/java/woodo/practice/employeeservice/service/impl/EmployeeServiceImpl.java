package woodo.practice.employeeservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import woodo.practice.employeeservice.dto.APIResponseDto;
import woodo.practice.employeeservice.dto.DepartmentDto;
import woodo.practice.employeeservice.dto.EmployeeDto;
import woodo.practice.employeeservice.dto.OrganizationDto;
import woodo.practice.employeeservice.entity.Employee;
import woodo.practice.employeeservice.mapper.EmployeeMapper;
import woodo.practice.employeeservice.repository.EmployeeRepository;
import woodo.practice.employeeservice.service.APIClient;
import woodo.practice.employeeservice.service.EmployeeService;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	private EmployeeRepository employeeRepository;

	// private RestTemplate restTemplate;
	private WebClient webClient;
	private APIClient apiClient;

	@Override
	public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
		Employee saveDEmployee = employeeRepository.save(EmployeeMapper.mapToEmployee(employeeDto));
		return EmployeeMapper.mapToEmployeeDto(saveDEmployee);
	}

	// @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
	@Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
	@Override
	public APIResponseDto getEmployeeById(Long employeeId) {

		LOGGER.info("Fetching employee with id: {}", employeeId);

		Employee employee = employeeRepository.findById(employeeId).get();

		// restTemplate
		//        ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/departments/" + employee.getDepartmentCode(),
		//                DepartmentDto.class);
		//        DepartmentDto departmentDto = responseEntity.getBody();

		// apiClient
		DepartmentDto departmentDto = apiClient.getDepartment(employee.getDepartmentCode());

		// DepartmentDto departmentDto = webClient.get()
		// 	.uri("http://localhost:9191/api/departments/" + employee.getDepartmentCode())
		// 	.retrieve()
		// 	.bodyToMono(DepartmentDto.class)
		// 	.block();

		//  webClient
		OrganizationDto organizationDto = webClient.get()
			.uri("http://localhost:9191/api/organizations/" + employee.getOrganizationCode())
			.retrieve()
			.bodyToMono(OrganizationDto.class)
			.block();

		EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDto(employee);

		APIResponseDto apiResponseDto = new APIResponseDto();
		apiResponseDto.setEmployee(employeeDto);
		apiResponseDto.setDepartment(departmentDto);
		apiResponseDto.setOrganization(organizationDto);

		return apiResponseDto;
	}

	public APIResponseDto getDefaultDepartment(Long employeeId, Exception exception) {
		LOGGER.info("Returning default department as fallback due to exception: {}", exception.getMessage());

		Employee employee = employeeRepository.findById(employeeId).get();

		DepartmentDto departmentDto = new DepartmentDto();
		departmentDto.setDepartmentCode("R&D");
		departmentDto.setDepartmentName("Research & Development");
		departmentDto.setDepartmentDescription("Research & Development Department");

		EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDto(employee);

		APIResponseDto apiResponseDto = new APIResponseDto();
		apiResponseDto.setEmployee(employeeDto);
		apiResponseDto.setDepartment(departmentDto);

		return apiResponseDto;
	}

}
