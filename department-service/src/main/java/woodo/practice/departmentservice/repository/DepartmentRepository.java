package woodo.practice.departmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woodo.practice.departmentservice.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByDepartmentCode(String departmentCode);
}
