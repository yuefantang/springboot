package cn.dy.sys.repository.impl.employee;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.mysema.query.BooleanBuilder;

import cn.dy.sys.dto.common.Searchable;
import cn.dy.sys.model.employee.Employee;
import cn.dy.sys.model.employee.QEmployee;
import cn.dy.sys.repository.EmployeeRepositoryEmplo;
import cn.dy.sys.repository.common.AbstractRepository;

@Component
public class EmployeeRepositoryImpl extends AbstractRepository implements EmployeeRepositoryEmplo {

    @Override
    public Page<Employee> findAll(final Searchable searchable, final Pageable pageable) {
        final QEmployee qemployee = QEmployee.employee;
        final BooleanBuilder where = new BooleanBuilder();

        if (searchable.hasKey("job")) {
            where.and(qemployee.employeeJob.eq(searchable.getStrValue("job")));
        }

        if (searchable.hasKey("member")) {
            where.and(qemployee.employeeMember.eq(searchable.getStrValue("member")));
        }

        if (searchable.hasKey("idcard")) {
            where.and(qemployee.employeeIdcard.eq(searchable.getStrValue("idcard")));
        }

        //        if (searchable.hasKey("fromDate")) {
        //            final Calendar cFrom = Calendar.getInstance();
        //            cFrom.setTimeInMillis(searchable.getLongValue("fromDate"));
        //            where.and(qCustomer.lastModifiedDate.goe(cFrom.getTime()));
        //        }
        //        if (searchable.hasKey("toDate")) {
        //            final Calendar cTo = Calendar.getInstance();
        //            cTo.setTimeInMillis(searchable.getLongValue("toDate"));
        //            where.and(qCustomer.lastModifiedDate.loe(cTo.getTime()));
        //        }
        //        where.and(qCustomer.contact.mobile.notLike("0%"));
        //        where.and(qCustomer.contact.mobile.notLike("9%"));
        //        where.and(qCustomer.deleted.eq(false));

        final Page<Employee> pageResult = this.search(where, pageable, qemployee);
        return pageResult;

    }

    @Override
    protected Class<?> getModelClass() {
        return Employee.class;
    }

    @Override
    public List<Object[]> findAllEmployee(final Searchable searchable, final String job, final String member,
            final String idcard) {
        final StringBuffer sql = new StringBuffer();
        sql.append(" SELECT employee_member,employee_name,employee_gender,employee_birthday, ");
        sql.append(" employee_birth_place,employee_job,employee_education,employee_entry_date, ");
        sql.append(
                " employee_idcard,employee_address,employee_contact,employee_department,employee_post,employee_remark ");
        sql.append(" FROM employee ");
        sql.append(" WHERE ");
        if (searchable.hasKey("member")) {
            sql.append(" employee_member='" + searchable.getStrValue("member") + "' ");
        }
        if (searchable.hasKey("idcard")) {
            sql.append(" AND employee_idcard LIKE '%" + searchable.getStrValue("idcard") + "%' ");
        }
        if (searchable.hasKey("job")) {
            sql.append(" AND employee_job='" + job + "' ");
        }
        sql.append("employee_job='" + job + "' ");
        if (StringUtils.isNotEmpty(member)) {
            sql.append(" AND employee_member='" + member + "' ");
        }
        if (StringUtils.isNotEmpty(idcard)) {
            sql.append(" AND employee_idcard LIKE '%" + idcard + "%' ");
        }

        final Query query = this.entityManager.createNativeQuery(sql.toString());
        System.out.println("查询语句：" + sql.toString());
        final List<Object[]> resultList = query.getResultList();
        System.out.println("查询结果1：" + resultList.size());
        //        final Page<T> pageResult = new PageImpl<>(listResult, pageable, count);
        return resultList;
    }

}
