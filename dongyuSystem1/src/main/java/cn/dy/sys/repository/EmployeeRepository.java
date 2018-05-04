package cn.dy.sys.repository;

import org.springframework.data.repository.Repository;

import cn.dy.sys.model.employee.Employee;

public interface EmployeeRepository extends Repository<Employee, Long>, EmployeeRepositoryEmplo {

    Employee save(Employee model);

    Employee findOne(Long id);

    void delete(Long id);

}
