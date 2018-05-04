package cn.dy.sys.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import cn.dy.sys.dto.AuthenticationDTO;
import cn.dy.sys.exception.CustomRuntimeException;

public class AccountAuthenticationProvider implements AuthenticationProvider {
    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final AccountAuthenticationToken authToken = (AccountAuthenticationToken) authentication;
        this.checkAuthParams(authToken);
        final ApplicationUserDetails user = this.retrieveUser(authToken);

        this.checkUser(user, authToken);

        final AccountAuthenticationToken result = new AccountAuthenticationToken();
        result.setDetails(user);
        //        result.setPrincipal(user.getNo());
        result.setAuthenticated(true);
        return result;
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return AccountAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private void checkAuthParams(final AccountAuthenticationToken authToken) {
        // 用户名或密码为空
        final boolean hasUsername = StringUtils.hasText(authToken.getUserName());
        final boolean hasPassword = StringUtils.hasText(authToken.getPassword());
        if (!hasUsername && !hasPassword) {
            throw new CustomRuntimeException("400", "用户名、密码不能为空.");
        }
    }

    private ApplicationUserDetails retrieveUser(final AccountAuthenticationToken authToken)
            throws AuthenticationException {
        final AuthenticationDTO authDTO = new AuthenticationDTO();
        String username = "";
        if (StringUtils.hasText(authToken.getUserName())) {
            username = authToken.getUserName();
        }
        authDTO.setUsername(username.trim());

        authDTO.setPassword(authToken.getPassword());

        final ApplicationUserDetails user = this.userDetailsService.login(authDTO);
        //        final ResultDTO<CustomerDTO> customerDTORS = this.crmApiService.getMemberDetail(user.getNo());
        //        if (customerDTORS.isSuccess()) {
        //            if (customerDTORS.getData() != null) {
        //                final String memberType = customerDTORS.getData().getIdentityType();
        //                user.setMemberIdentityType(memberType);
        //            }
        //        }
        return user;
    }

    private void checkUser(final UserDetails user, final AccountAuthenticationToken authToken) {
        if (!user.isAccountNonLocked()) {
            AccountAuthenticationProvider.this.logger.debug("User account is locked");

            throw new LockedException("账户已锁定");
        }

        if (!user.isEnabled()) {
            AccountAuthenticationProvider.this.logger.debug("User account is disabled");

            throw new DisabledException("账户未生效");
        }

        if (!user.isAccountNonExpired()) {
            AccountAuthenticationProvider.this.logger.debug("User account is expired");

            throw new AccountExpiredException("账户已过期");
        }
    }
}
