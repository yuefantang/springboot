package cn.dy.sys.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Role extends AbstractModel {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    @NotBlank
    @Column(unique = true)
    private String name;

    @NotBlank
    @Column(length = 20)
    private String descript;

    @NotBlank
    @Column(length = 20)
    private String userId;

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescript() {
        return this.descript;
    }

    public void setDescript(final String descript) {
        this.descript = descript;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

}
