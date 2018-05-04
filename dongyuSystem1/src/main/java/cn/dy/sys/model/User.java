package cn.dy.sys.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Length;

/**
 * 基本用户
 */
@Entity
public class User extends AbstractAuditModel {
    private static final long serialVersionUID = -8340085416786021618L;

    /**
     * 登录名称
     */
    @Length(max = 32)
    @Column(unique = true, length = 32)
    private String loginId;

    /**
     * 登录密码
     */
    @Length(max = 80)
    @Column(length = 80)
    private String password;

    /**
     * 帐号是否锁定
     */
    private boolean accountLocked = false;
    /**
     * 帐号是否启用
     */
    private final boolean enabled = true;

    /**
     * 帐号是否过期
     */
    private final boolean accountExpired = false;

    /**
     * 密码是否过期
     */
    private final boolean credentialsExpired = false;

    /**
     * 允许尝试登陆次数
     */
    private int remainLoginTimes;

    /**
     * 上次登录时间
     */
    private Date lastLoginTime = new Date();

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isAccountExpired() {
        return this.accountExpired;
    }

    public boolean isCredentialsExpired() {
        return this.credentialsExpired;
    }

    public boolean isAccountLocked() {
        return this.accountLocked;
    }

    public void setAccountLocked(final boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public String getLoginId() {
        return this.loginId;
    }

    public void setLoginId(final String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public int getRemainLoginTimes() {
        return this.remainLoginTimes;
    }

    public void setRemainLoginTimes(final int remainLoginTimes) {
        this.remainLoginTimes = remainLoginTimes;
    }

    public Date getLastLoginTime() {
        return this.lastLoginTime;
    }

    public void setLastLoginTime(final Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

}
