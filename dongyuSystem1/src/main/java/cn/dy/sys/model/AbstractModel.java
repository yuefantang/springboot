package cn.dy.sys.model;

import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * 抽象模型类（不带审计字段）
 * <p>
 */
@MappedSuperclass
public abstract class AbstractModel extends AbstractPersistable<Long> {
    private static final long serialVersionUID = 7205853521241442700L;

    // TODO 需要实现集群化的ID生成机制
}
