package cn.dy.sys.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.dy.sys.dto.common.Searchable;
import cn.dy.sys.model.employee.Employee;

public interface EmployeeRepositoryEmplo {

    Page<Employee> findAll(Searchable searchable, Pageable pageable);

    List<Object[]> findAllEmployee(Searchable searchable);
}
