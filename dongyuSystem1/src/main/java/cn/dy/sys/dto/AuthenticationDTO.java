package cn.dy.sys.dto;

public class AuthenticationDTO {

    // 账号信息
    private String username;
    private String password; // 登陆密码

    public String getUsername() {
        return this.username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
