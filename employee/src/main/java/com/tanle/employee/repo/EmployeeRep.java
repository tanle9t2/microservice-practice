package com.tanle.employee.repo;

import com.tanle.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRep extends JpaRepository<Employee, Integer> {
}
