package cn.dy.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dy.sys.dto.common.Searchable;
import cn.dy.sys.model.employee.Employee;
import cn.dy.sys.repository.EmployeeRepository;
import cn.dy.sys.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAll(final Searchable searchable, final String job, final String member,
            final String idcard) {
        System.out.println("开始查询：");
        final List<Object[]> findAllEmployee = this.employeeRepository.findAllEmployee(searchable, job, member, idcard);
        System.out.println("查询结果2:" + findAllEmployee.size());
        final List<Employee> Employees = new ArrayList<>();
        if (findAllEmployee.size() > 0) {
            for (int i = 0; i < findAllEmployee.size(); i++) {
                final Object[] tuple = findAllEmployee.get(i);
                final Employee model = new Employee();

                model.setEmployeeMember(tuple[0] == null ? null : tuple[0].toString());
                model.setEmployeeName(tuple[1] == null ? null : tuple[1].toString());
                model.setEmployeeGender(tuple[2] == null ? null : tuple[2].toString());
                model.setEmployeeBirthday(tuple[3] == null ? null : tuple[3].toString());
                model.setEmployeeBirthPlace(tuple[4] == null ? null : tuple[4].toString());
                model.setEmployeeJob(tuple[5] == null ? null : tuple[5].toString());
                model.setEmployeeEducation(tuple[6] == null ? null : tuple[6].toString());
                model.setEmployeeEntryDate(tuple[7] == null ? null : tuple[7].toString());
                model.setEmployeeIdcard(tuple[8] == null ? null : tuple[8].toString());
                model.setEmployeeAddress(tuple[9] == null ? null : tuple[9].toString());
                model.setEmployeeContact(tuple[10] == null ? null : tuple[10].toString());
                model.setEmployeeDepartment(tuple[11] == null ? null : tuple[11].toString());
                model.setEmployeePost(tuple[12] == null ? null : tuple[12].toString());
                model.setEmployeeRemark(tuple[13] == null ? null : tuple[13].toString());

                Employees.add(model);
            }
            return Employees;
        }
        return Employees;
    }

}
