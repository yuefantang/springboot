package cn.dy.sys.dto.convertor;

import org.springframework.stereotype.Component;

import cn.dy.sys.dto.EmployeeDTO;
import cn.dy.sys.dto.common.AbstractConvertor;
import cn.dy.sys.model.employee.Employee;

@Component
public class EmployeeConvertor extends AbstractConvertor<Employee, EmployeeDTO> {

    @Override
    public Employee toModel(final EmployeeDTO dto) {
        final Employee model = new Employee();
        model.setEmployeeAddress(dto.getEmployeeAddress());
        model.setEmployeeBirthday(dto.getEmployeeBirthday());
        model.setEmployeeBirthPlace(dto.getEmployeeBirthPlace());
        model.setEmployeeContact(dto.getEmployeeContact());
        model.setEmployeeDepartment(dto.getEmployeeDepartment());
        model.setEmployeeEducation(dto.getEmployeeEducation());
        model.setEmployeeEntryDate(dto.getEmployeeEntryDate());
        model.setEmployeeGender(dto.getEmployeeGender());
        model.setEmployeeIdcard(dto.getEmployeeIdcard());
        model.setEmployeeMember(dto.getEmployeeMember());
        model.setEmployeeName(dto.getEmployeeName());
        model.setEmployeePost(dto.getEmployeePost());
        model.setEmployeeRemark(dto.getEmployeeRemark());
        model.setEmployeeJob(dto.getEmployeeJob());
        return model;
    }

    @Override
    public EmployeeDTO toDTO(final Employee model) {
        final EmployeeDTO dto = new EmployeeDTO();
        dto.setId(model.getId());
        dto.setEmployeeAddress(model.getEmployeeAddress());
        dto.setEmployeeBirthday(model.getEmployeeBirthday());
        dto.setEmployeeBirthPlace(model.getEmployeeBirthPlace());
        dto.setEmployeeContact(model.getEmployeeContact());
        dto.setEmployeeDepartment(model.getEmployeeDepartment());
        dto.setEmployeeEducation(model.getEmployeeEducation());
        dto.setEmployeeEntryDate(model.getEmployeeEntryDate());
        dto.setEmployeeGender(model.getEmployeeGender());
        dto.setEmployeeIdcard(model.getEmployeeIdcard());
        dto.setEmployeeMember(model.getEmployeeMember());
        dto.setEmployeeName(model.getEmployeeName());
        dto.setEmployeePost(model.getEmployeePost());
        dto.setEmployeeRemark(model.getEmployeeRemark());
        dto.setIsDelete(model.getIsDeleted());
        return dto;
    }

    public Employee update(final EmployeeDTO dto, final Employee model) {
        model.setEmployeeAddress(dto.getEmployeeAddress());
        model.setEmployeeBirthday(dto.getEmployeeBirthday());
        model.setEmployeeBirthPlace(dto.getEmployeeBirthPlace());
        model.setEmployeeContact(dto.getEmployeeContact());
        model.setEmployeeDepartment(dto.getEmployeeDepartment());
        model.setEmployeeEducation(dto.getEmployeeEducation());
        model.setEmployeeEntryDate(dto.getEmployeeEntryDate());
        model.setEmployeeGender(dto.getEmployeeGender());
        model.setEmployeeIdcard(dto.getEmployeeIdcard());
        model.setEmployeeMember(dto.getEmployeeMember());
        model.setEmployeeName(dto.getEmployeeName());
        model.setEmployeePost(dto.getEmployeePost());
        model.setEmployeeRemark(dto.getEmployeeRemark());

        return model;
    }

}
