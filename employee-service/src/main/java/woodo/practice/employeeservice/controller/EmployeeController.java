package woodo.practice.employeeservice.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import woodo.practice.employeeservice.dto.APIResponseDto;
import woodo.practice.employeeservice.dto.EmployeeDto;
import woodo.practice.employeeservice.service.EmployeeService;

@Tag(name = "Employee", description = "Employee Rest API")
@RestController
@RequestMapping("api/employees")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    @Operation(summary = "Save Employee", description = "Save Employee")
    @ApiResponse(responseCode = "201", description = "Employee saved successfully")
    @PostMapping
    public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto){
        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @Operation(summary = "Get Employee", description = "Get Employee by employee id")
    @ApiResponse(responseCode = "200", description = "Employee found successfully")
    @GetMapping("{id}")
    public ResponseEntity<APIResponseDto> getEmployee(@PathVariable("id") Long employeeId){
        APIResponseDto employeeDto = employeeService.getEmployeeById(employeeId);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request){
        // 포트 확인용
        return "Employee Service is working on port " + request.getServerPort();
    }
}
