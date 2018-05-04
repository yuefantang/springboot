package cn.dy.sys.model.employee;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Length;

import cn.dy.sys.model.AbstractAuditModel;

/**
 * 员工信息
 */
@Entity
public class Employee extends AbstractAuditModel {

    private static final long serialVersionUID = 3318153504415231218L;

    /**
     * 员工编号
     */
    @Length(max = 32)
    @Column(length = 32)
    private String employeeMember;

    /**
     * 员工姓名
     */
    @Length(max = 50)
    @Column(length = 50)
    private String employeeName;

    /**
     * 员工性别
     */
    @Length(max = 50)
    @Column(length = 50)
    private String employeeGender;

    /**
     * 员工出生日期
     */
    @Length(max = 50)
    @Column(length = 50)
    private String employeeBirthday;

    /**
     * 员工籍贯
     */
    @Length(max = 255)
    @Column(length = 255)
    private String employeeBirthPlace;

    /**
     * 员工是否在职
     */
    @Length(max = 255)
    @Column(length = 255)
    private String employeeJob;
    /**
     * 员工学历
     */
    @Length(max = 255)
    @Column(length = 255)
    private String employeeEducation;
    /**
     * 员工入职时间
     */
    @Length(max = 255)
    @Column(length = 255)
    private String employeeEntryDate;
    /**
     * 员工身份证号
     */
    @Length(max = 255)
    @Column(length = 255)
    private String employeeIdcard;
    /**
     * 员工家庭住址
     */
    @Length(max = 255)
    @Column(length = 255)
    private String employeeAddress;
    /**
     * 员工联系人信息
     */
    @Length(max = 255)
    @Column(length = 255)
    private String employeeContact;
    /**
     * 员工部门
     */
    @Length(max = 255)
    @Column(length = 255)
    private String employeeDepartment;
    /**
     * 员工岗位
     */
    @Length(max = 255)
    @Column(length = 255)
    private String employeePost;
    /**
     * 员工备注
     */
    @Length(max = 255)
    @Column(length = 255)
    private String employeeRemark;

    /**
     * 员工是否删除
     */
    @Length(max = 255)
    @Column(length = 255)
    private String isDeleted;

    public String getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getEmployeeJob() {
        return this.employeeJob;
    }

    public void setEmployeeJob(final String employeeJob) {
        this.employeeJob = employeeJob;
    }

    public String getEmployeeMember() {
        return this.employeeMember;
    }

    public void setEmployeeMember(final String employeeMember) {
        this.employeeMember = employeeMember;
    }

    public String getEmployeeName() {
        return this.employeeName;
    }

    public void setEmployeeName(final String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeGender() {
        return this.employeeGender;
    }

    public void setEmployeeGender(final String employeeGender) {
        this.employeeGender = employeeGender;
    }

    public String getEmployeeBirthday() {
        return this.employeeBirthday;
    }

    public void setEmployeeBirthday(final String employeeBirthday) {
        this.employeeBirthday = employeeBirthday;
    }

    public String getEmployeeBirthPlace() {
        return this.employeeBirthPlace;
    }

    public void setEmployeeBirthPlace(final String employeeBirthPlace) {
        this.employeeBirthPlace = employeeBirthPlace;
    }

    public String getEmployeeEducation() {
        return this.employeeEducation;
    }

    public void setEmployeeEducation(final String employeeEducation) {
        this.employeeEducation = employeeEducation;
    }

    public String getEmployeeEntryDate() {
        return this.employeeEntryDate;
    }

    public void setEmployeeEntryDate(final String employeeEntryDate) {
        this.employeeEntryDate = employeeEntryDate;
    }

    public String getEmployeeIdcard() {
        return this.employeeIdcard;
    }

    public void setEmployeeIdcard(final String employeeIdcard) {
        this.employeeIdcard = employeeIdcard;
    }

    public String getEmployeeAddress() {
        return this.employeeAddress;
    }

    public void setEmployeeAddress(final String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeeContact() {
        return this.employeeContact;
    }

    public void setEmployeeContact(final String employeeContact) {
        this.employeeContact = employeeContact;
    }

    public String getEmployeeDepartment() {
        return this.employeeDepartment;
    }

    public void setEmployeeDepartment(final String employeeDepartment) {
        this.employeeDepartment = employeeDepartment;
    }

    public String getEmployeePost() {
        return this.employeePost;
    }

    public void setEmployeePost(final String employeePost) {
        this.employeePost = employeePost;
    }

    public String getEmployeeRemark() {
        return this.employeeRemark;
    }

    public void setEmployeeRemark(final String employeeRemark) {
        this.employeeRemark = employeeRemark;
    }

}
