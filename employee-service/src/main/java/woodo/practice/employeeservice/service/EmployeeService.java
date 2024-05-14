package woodo.practice.employeeservice.service;


import woodo.practice.employeeservice.dto.APIResponseDto;
import woodo.practice.employeeservice.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto);

    APIResponseDto getEmployeeById(Long employeeId);
}
