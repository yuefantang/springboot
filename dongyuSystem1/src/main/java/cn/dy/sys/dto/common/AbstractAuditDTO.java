package cn.dy.sys.dto.common;

import java.util.Date;

/**
 * 包含审计信息的抽象 DTO 类
 */
public abstract class AbstractAuditDTO extends AbstractDTO implements DateAuditable {

    /**
     * 创建者
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改者
     */
    private String lastModifiedBy;

    /**
     * 修改时间
     */
    private Date lastModifiedDate;

    //////////////////////////////////////////////////
    /// Getter and Setter
    //////////////////////////////////////////////////
    public String getCreatedBy() {
        return this.createdBy;
    }

    public final void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Date getCreatedDate() {
        return this.createdDate;
    }

    public final void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public final void setLastModifiedBy(final String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public final void setLastModifiedDate(final Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
