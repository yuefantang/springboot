package cn.dy.sys.service;

import java.util.List;

import cn.dy.sys.dto.common.Searchable;
import cn.dy.sys.model.employee.Employee;

public interface EmployeeService {

    public List<Employee> findAll(Searchable searchable);
}
