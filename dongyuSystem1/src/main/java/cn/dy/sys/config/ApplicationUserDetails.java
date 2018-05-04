package cn.dy.sys.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.dy.sys.dto.AuthenticationUserDTO;

public class ApplicationUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final Collection<GrantedAuthority> authorities;

    // 基本信息
    private String memberIdentityType;//会员身份 follower(粉丝) | potentialCustomer(潜客 ) | carOwner(车主)
    private final String userType;
    private final String loginId;

    private List<String> roles;

    @JsonIgnore
    private final String password;

    // 帐号状态
    private final boolean enabled;
    private final boolean accountLocked;
    private final boolean accountExpired;
    private final boolean credentialsExpired;

    public ApplicationUserDetails(final AuthenticationUserDTO user) {
        this.loginId = user.getLoginId();
        this.password = user.getPassword();
        this.roles = user.getRoles();
        this.userType = user.getUserType();

        this.enabled = user.getEnabled() != null ? user.getEnabled() : false;
        this.accountLocked = user.isAccountLocked();
        this.accountExpired = user.isAccountExpired();
        this.credentialsExpired = user.isCredentialsExpired();
        this.authorities = new ArrayList<>();
        //        user.getRoles().forEach(role -> {
        //            this.authorities.add(new SimpleGrantedAuthority(role));
        //        });
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public String getUserType() {
        return this.userType;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return !this.accountExpired;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return !this.accountLocked;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return !this.credentialsExpired;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return this.enabled;
    }

    public String getMemberIdentityType() {
        return this.memberIdentityType;
    }

    public void setMemberIdentityType(final String memberIdentityType) {
        this.memberIdentityType = memberIdentityType;
    }

    public List<String> getRoles() {
        return this.roles;
    }

    public void setRoles(final List<String> roles) {
        this.roles = roles;
    }

    public String getLoginId() {
        return this.loginId;
    }

    @Override
    public String getUsername() {
        return this.loginId;
    }

}
