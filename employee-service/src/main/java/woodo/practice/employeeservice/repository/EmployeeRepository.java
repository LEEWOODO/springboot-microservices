package woodo.practice.employeeservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import woodo.practice.employeeservice.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
