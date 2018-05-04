package cn.dy.sys.repository.common;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.QSort;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.ConstructorExpression;
import com.mysema.query.types.Expression;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.QBean;
import com.mysema.query.types.path.EntityPathBase;
import com.mysema.query.types.path.PathBuilderFactory;

/**
 * Repository的基类，封装对复杂查询的支持
 *
 * @author liuyg
 */
public abstract class AbstractRepository {

    @Autowired
    protected EntityManager entityManager;

    private Querydsl querydsl;

    protected JPQLQuery query() {
        return this.querydsl().createQuery();
    }

    /**
     * 按照传入的参数，调用Bean相应的setter，生成Bean实例。
     *
     * @see com.mysema.query.types.Projections.bean(Class<T> type, Expression
     *      <?>... exprs)
     * @param type
     * @param exprs
     * @return
     */
    protected <T> QBean<T> bean(final Class<T> type, final Expression<?>... exprs) {
        return new QBean<T>(type, exprs);
    }

    /**
     * 按照传入的参数，调用Bean的构造函数，生成Bean实例。
     *
     * @see com.mysema.query.types.Projections.constructor(Class<T> type,
     *      Expression<?>... exprs)
     * @param type
     * @param exprs
     * @return
     */
    protected <T> ConstructorExpression<T> beanConstructor(final Class<T> type, final Expression<?>... exprs) {
        return ConstructorExpression.create(type, exprs);
    }

    /**
     * 排序
     *
     * @param query
     * @param orders
     * @return
     */
    protected JPQLQuery applySorting(final JPQLQuery query, final OrderSpecifier<?>... orders) {
        return this.querydsl().applySorting(new QSort(orders), query);
    }

    /**
     * 共通的分页查询处理
     *
     * @param predicate 查询条件断言
     * @param pageable 分页 + 排序
     * @param entityPath 查询实体对应的QModel
     * @return 分页数据
     */
    protected <T> Page<T> search(final Predicate predicate, final Pageable pageable,
            final EntityPathBase<T> entityPath) {
        JPQLQuery query = this.query().from(entityPath);

        query.where(predicate);
        final long count = query.count();
        query = this.applyPagination(query, pageable);
        final List<T> listResult = query.list(entityPath);
        final Page<T> pageResult = new PageImpl<>(listResult, pageable, count);
        return pageResult;
    }

    /**
     * 共通的分页查询处理
     *
     * @param predicate 查询条件断言
     * @param pageable 分页 + 排序
     * @param entityPath 查询实体对应的QModel
     * @param orderbys 排序字段
     * @return 分页数据
     */
    protected <T> Page<T> search(final Predicate predicate, final Pageable pageable,
            final EntityPathBase<T> entityPath, final OrderSpecifier<?>... orderbys) {
        JPQLQuery query = this.query().from(entityPath);

        query.where(predicate);
        final long count = query.count();
        query = this.applyPagination(query, pageable);
        query.orderBy(orderbys);
        final List<T> listResult = query.list(entityPath);
        final Page<T> pageResult = new PageImpl<>(listResult, pageable, count);
        return pageResult;
    }

    /**
     * 分页 + 排序
     *
     * @param query
     * @param pageable
     * @return
     */
    protected JPQLQuery applyPagination(final JPQLQuery query, final Pageable pageable) {
        return this.querydsl().applyPagination(pageable, query);
    }

    private <T> Querydsl querydsl() {
        if (this.querydsl == null) {
            this.querydsl = new Querydsl(this.entityManager, new PathBuilderFactory().create(this.getModelClass()));
        }
        return this.querydsl;
    }

    protected abstract Class<?> getModelClass();
}
