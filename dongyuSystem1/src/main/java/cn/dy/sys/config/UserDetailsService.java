package cn.dy.sys.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;

import cn.dy.sys.dto.AuthenticationDTO;
import cn.dy.sys.dto.AuthenticationUserDTO;
import cn.dy.sys.dto.common.ResultDTO;
import cn.dy.sys.exception.CustomRuntimeException;
import cn.dy.sys.model.Role;
import cn.dy.sys.model.User;
import cn.dy.sys.repository.RoleRepository;
import cn.dy.sys.repository.UserRepository;
import cn.dy.sys.service.UserService;
import cn.dy.sys.util.encode.PasswordEncoder;

@Service
public class UserDetailsService {
    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Value("${remain.login.times}")
    private int remainLoginTimes;

    public ApplicationUserDetails login(final AuthenticationDTO authDTO) throws UsernameNotFoundException {
        try {
            final ResultDTO<AuthenticationUserDTO> result = this.authenticate(authDTO);

            this.logger.info("***** Service Auth step 4.1 end!*****");
            //            if (result.getStatus() == Status.failure) {
            //                if (isNonceTokenLogin) {
            //                    throw new NonceExpiredException("无效的身份令牌.");
            //                } else {
            //                    throw new BadCredentialsException((result.getErrors()[0]).getErrmsg());
            //                }
            //            }
            final AuthenticationUserDTO userDTO = result.getData();

            final List<Role> roleList = this.roleRepository.findAllByUserId(userDTO.getUserID());
            this.logger.info("***** Service Auth step 4.2 end!*****");
            if ((roleList != null) && (roleList.size() != 0)) {
                final List<String> listRoles = new ArrayList<String>();
                for (final Role role : roleList) {
                    listRoles.add(role.getName());
                }
                userDTO.setRoles(listRoles);
            }
            this.logger.info("***** Service Auth step 4.3 end!*****");
            final ApplicationUserDetails details = new ApplicationUserDetails(userDTO);

            return details;
        } catch (final RestClientException e) {
            e.printStackTrace();
            throw new InternalAuthenticationServiceException(e.getMessage(), e);
        }
    }

    public ResultDTO<AuthenticationUserDTO> authenticate(final AuthenticationDTO dto) {
        AuthenticationUserDTO authUserDTO = null;
        if (UserDetailsService.isAccountLogin(dto)) {// 用户名登录
            authUserDTO = this.authenticateByUserAccount(dto);
        }
        if (authUserDTO == null) {
            throw new CustomRuntimeException("401", "用户不存在");
        }
        final ResultDTO<AuthenticationUserDTO> rs = ResultDTO.success(authUserDTO);
        return rs;
    }

    private static boolean isAccountLogin(final AuthenticationDTO dto) {
        return StringUtils.hasText(dto.getUsername());
    }

    /**
     * 用户账户认证（用户名／手机号／邮件地址＋密码认证）
     *
     * @param dto
     * @return
     */
    private AuthenticationUserDTO authenticateByUserAccount(final AuthenticationDTO dto) {
        String username = dto.getUsername();
        if (StringUtils.hasText(username)) {
            username = username.trim();
        }

        final User model = this.userService.findUserByIdentity(username);

        if (model == null) {
            throw new CustomRuntimeException("username.error", "用户名或密码错误，请重新登录！");
        } else {
            if (model.isAccountLocked()) {
                throw new CustomRuntimeException("password.error", "用户名或密码错误累积超过" + this.remainLoginTimes
                        + "次，请联系系统管理员！");
            } else {
                final Date now = new Date();
                if ((now.getTime() - model.getLastLoginTime().getTime()) >= (24 * 60 * 60 * 1000)) {
                    model.setRemainLoginTimes(this.remainLoginTimes);
                }

                if (!this.passwordEncoder.matches(dto.getPassword(), model.getPassword())) {//校验密码
                    model.setRemainLoginTimes(model.getRemainLoginTimes() - 1);
                    String msg = "";
                    if (model.getRemainLoginTimes() <= 0) {
                        model.setAccountLocked(true);
                        msg = "用户名或密码错误累积超过5次，请联系系统管理员！";
                    } else {
                        msg = "用户名或密码错误，请重新登录，您还有" + model.getRemainLoginTimes() + "次机会！";
                    }
                    model.setLastLoginTime(new Date());
                    this.userRepository.save(model);
                    throw new CustomRuntimeException("password.error", msg);
                }
            }
        }
        model.setRemainLoginTimes(this.remainLoginTimes);
        this.userRepository.save(model);
        return this.toDTO(model);
    }

    /**
     * 认证DTO转换仅在该Controller使用，不具有通用性，故而没有实现Convertor的统一方式
     *
     * @param model
     * @return
     */
    private AuthenticationUserDTO toDTO(final User model) {
        if (model == null) {
            return null;
        }
        final AuthenticationUserDTO dto = new AuthenticationUserDTO();

        dto.setUserID(String.valueOf(model.getId()));
        dto.setLoginId(model.getLoginId());
        dto.setPassword(model.getPassword());
        dto.setEnabled(model.isEnabled());
        dto.setAccountLocked(model.isAccountLocked());
        dto.setAccountExpired(model.isAccountExpired());
        dto.setCredentialsExpired(model.isCredentialsExpired());
        return dto;
    }

    //    public void logout(final Authentication authentication) {
    //        if (authentication == null) {
    //            return;
    //        }
    //        final Object details = authentication.getDetails();
    //        if ((details == null) || !(details instanceof ApplicationUserDetails)) {
    //            return;
    //        }
    //        final ApplicationUserDetails appUserDetails = (ApplicationUserDetails) details;
    //        final String nonceToken = appUserDetails.getNonceToken();
    //        if (!StringUtils.hasText(nonceToken)) {
    //            return;
    //        }
    //        try {
    //            this.authRestTemplate.delete(this.logoutUrl, nonceToken);
    //        } catch (final RestClientException e) {
    //            throw new InternalAuthenticationServiceException(e.getMessage(), e);
    //        }
    //    }

}
