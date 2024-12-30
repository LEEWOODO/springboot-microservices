package woodo.practice.departmentservice.controller;

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
import lombok.extern.slf4j.Slf4j;
import woodo.practice.departmentservice.dto.DepartmentDto;
import woodo.practice.departmentservice.service.DepartmentService;

@Tag(name = "Department", description = "Department Rest API")
@RestController
@RequestMapping("api/departments")
@AllArgsConstructor
@Slf4j
public class DepartmentController {

    private DepartmentService departmentService;

    @Operation(summary = "Save Department", description = "Save Department")
    @ApiResponse(responseCode = "201", description = "Department saved successfully")
    @PostMapping
    public ResponseEntity<DepartmentDto> saveDepartment(@RequestBody DepartmentDto departmentDto) {
        DepartmentDto savedDepartment = departmentService.saveDepartment(departmentDto);
        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

    @Operation(summary = "Get Department", description = "Get Department by department code")
    @ApiResponse(responseCode = "200", description = "Department found successfully")
    @GetMapping("{department-code}")
    public ResponseEntity<DepartmentDto> getDepartment(@PathVariable("department-code") String departmentCode) {
        DepartmentDto departmentDto = departmentService.getDepartmentByCode(departmentCode);
        return new ResponseEntity<>(departmentDto, HttpStatus.OK);
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request){
        // 포트 확인용
        return "Department Service is working on port " + request.getServerPort();
    }
}
