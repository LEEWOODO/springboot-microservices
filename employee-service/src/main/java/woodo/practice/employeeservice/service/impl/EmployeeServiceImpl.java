package woodo.practice.employeeservice.service.impl;


import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import woodo.practice.employeeservice.dto.EmployeeDto;
import woodo.practice.employeeservice.entity.Employee;
import woodo.practice.employeeservice.mapper.EmployeeMapper;
import woodo.practice.employeeservice.repository.EmployeeRepository;
import woodo.practice.employeeservice.service.EmployeeService;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private EmployeeRepository employeeRepository;

   // private RestTemplate restTemplate;
//    private WebClient webClient;
//    private APIClient apiClient;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);

        Employee saveDEmployee = employeeRepository.save(employee);

        EmployeeDto savedEmployeeDto = EmployeeMapper.mapToEmployeeDto(saveDEmployee);

        return savedEmployeeDto;
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        return EmployeeMapper.mapToEmployeeDto(employee);

    }

//    //@CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
//    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
//    @Override
//    public APIResponseDto getEmployeeById(Long employeeId) {
//
//        LOGGER.info("inside getEmployeeById() method");
//        Employee employee = employeeRepository.findById(employeeId).get();
//
////        ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity("http://DEPARTMENT-SERVICE/api/departments/" + employee.getDepartmentCode(),
////                DepartmentDto.class);
////
////        DepartmentDto departmentDto = responseEntity.getBody();
//
//        DepartmentDto departmentDto = webClient.get()
//                .uri("http://localhost:8080/api/departments/" + employee.getDepartmentCode())
//                .retrieve()
//                .bodyToMono(DepartmentDto.class)
//                .block();
//
//      //  DepartmentDto departmentDto = apiClient.getDepartment(employee.getDepartmentCode());
//
//        OrganizationDto organizationDto = webClient.get()
//                .uri("http://localhost:8083/api/organizations/" + employee.getOrganizationCode())
//                .retrieve()
//                .bodyToMono(OrganizationDto.class)
//                .block();
//
//        EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDto(employee);
//
//        APIResponseDto apiResponseDto = new APIResponseDto();
//        apiResponseDto.setEmployee(employeeDto);
//        apiResponseDto.setDepartment(departmentDto);
//        apiResponseDto.setOrganization(organizationDto);
//        return apiResponseDto;
//    }
//
//    public APIResponseDto getDefaultDepartment(Long employeeId, Exception exception) {
//
//        LOGGER.info("inside getDefaultDepartment() method");
//
//        Employee employee = employeeRepository.findById(employeeId).get();
//
//        DepartmentDto departmentDto = new DepartmentDto();
//        departmentDto.setDepartmentName("R&D Department");
//        departmentDto.setDepartmentCode("RD001");
//        departmentDto.setDepartmentDescription("Research and Development Department");
//
//        EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDto(employee);
//
//        APIResponseDto apiResponseDto = new APIResponseDto();
//        apiResponseDto.setEmployee(employeeDto);
//        apiResponseDto.setDepartment(departmentDto);
//        return apiResponseDto;
//    }
}
