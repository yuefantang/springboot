package cn.dy.sys.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AccountAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 1L;

    public AccountAuthenticationToken() {
        super(null);
    }

    private String userName;
    private String idCode;
    private String password;
    private String errCode;

    @Override
    public Object getCredentials() {
        return this.password;
    }

    @Override
    public Object getPrincipal() {
        return this.userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getIdCode() {
        return this.idCode;
    }

    public void setIdCode(final String idCode) {
        this.idCode = idCode;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public void setErrCode(final String errCode) {
        this.errCode = errCode;
    }

}
