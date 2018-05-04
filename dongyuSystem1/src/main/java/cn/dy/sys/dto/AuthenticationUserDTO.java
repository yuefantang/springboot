package cn.dy.sys.dto;

import java.util.List;

import cn.dy.sys.dto.common.AbstractDTO;

/**
 * 认证用的用户DTO
 *
 * @author liuyg
 */
public class AuthenticationUserDTO extends AbstractDTO {

    // 账号信息
    private String userID;
    private String loginId;
    private String password;
    private String userType;

    private List<String> roles;

    // 帐号状态
    private boolean enabled;
    private boolean accountLocked;
    private boolean accountExpired;
    private boolean credentialsExpired;

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(final String userID) {
        this.userID = userID;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setUserType(final String userType) {
        this.userType = userType;
    }

    public Boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountLocked() {
        return this.accountLocked;
    }

    public void setAccountLocked(final boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public boolean isAccountExpired() {
        return this.accountExpired;
    }

    public void setAccountExpired(final boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public boolean isCredentialsExpired() {
        return this.credentialsExpired;
    }

    public void setCredentialsExpired(final boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    public Boolean getEnabled() {
        return this.enabled;
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

    public List<String> getRoles() {
        return this.roles;
    }

    public void setRoles(final List<String> roles) {
        this.roles = roles;
    }
}
