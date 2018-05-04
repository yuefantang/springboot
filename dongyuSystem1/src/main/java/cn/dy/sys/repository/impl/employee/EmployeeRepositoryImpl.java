package cn.dy.sys.repository.impl.employee;

import java.util.List;

import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import cn.dy.sys.dto.common.Searchable;
import cn.dy.sys.model.employee.Employee;
import cn.dy.sys.repository.EmployeeRepositoryEmplo;
import cn.dy.sys.repository.common.AbstractRepository;

@Component
public class EmployeeRepositoryImpl extends AbstractRepository implements EmployeeRepositoryEmplo {

    @Override
    public Page<Employee> findAll(final Searchable searchable, final Pageable pageable) {
        //       QEmployee employee = QEmployee.employee;
        //        final QCustomer qCustomer = QCustomer.customer;
        //
        //        final BooleanBuilder where = new BooleanBuilder();
        //
        //        if (searchable.hasKey("province")) {
        //            where.and(qCustomer.contact.province.eq(searchable.getStrValue("province")));
        //        }
        //
        //        if (searchable.hasKey("city")) {
        //            where.and(qCustomer.contact.city.eq(searchable.getStrValue("city")));
        //        }
        //
        //        if (searchable.hasKey("type")) {
        //            where.and(qCustomer.type.eq(searchable.getStrValue("type")));
        //        }
        //
        //        if (searchable.hasKey("identityType")) {
        //            where.and(qCustomer.identityType.eq(searchable.getStrValue("identityType")));
        //        }
        //
        //        if (searchable.hasKey("name")) {
        //            where.and(qCustomer.name.contains(searchable.getStrValue("name"))
        //                    .or(qCustomer.contact.mobile.contains(searchable.getStrValue("name"))));
        //        }
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
        //
        //        final Page<Customer> pageResult = this.search(where, pageable, qCustomer);
        //        return pageResult;
        return null;

    }

    @Override
    protected Class<?> getModelClass() {
        return Employee.class;
    }

    @Override
    public List<Object[]> findAllEmployee(final Searchable searchable) {
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
            sql.append(" AND employee_job='" + searchable.getStrValue("job") + "' ");
        }

        final Query query = this.entityManager.createNativeQuery(sql.toString());
        final List<Object[]> resultList = query.getResultList();
        //        final Page<T> pageResult = new PageImpl<>(listResult, pageable, count);
        return resultList;
    }

}
