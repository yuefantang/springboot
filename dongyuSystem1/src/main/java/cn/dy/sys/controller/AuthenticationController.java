package cn.dy.sys.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.dy.sys.dto.AuthenticationDTO;
import cn.dy.sys.dto.AuthenticationUserDTO;
import cn.dy.sys.dto.common.ResultDTO;
import cn.dy.sys.exception.CustomRuntimeException;
import cn.dy.sys.model.User;
import cn.dy.sys.repository.UserRepository;
import cn.dy.sys.service.UserService;
import cn.dy.sys.util.encode.PasswordEncoder;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {
    //    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Value("${remain.login.times}")
    private int remainLoginTimes;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultDTO<AuthenticationUserDTO> authenticate(@RequestBody final AuthenticationDTO dto) {
        AuthenticationUserDTO authUserDTO = null;
        if (this.isAccountLogin(dto)) {// 用户名登录
            authUserDTO = this.authenticateByUserAccount(dto);
        }
        if (authUserDTO == null) {
            throw new CustomRuntimeException("401", "用户不存在");
        }
        final ResultDTO<AuthenticationUserDTO> rs = ResultDTO.success(authUserDTO);
        return rs;
    }

    @RequestMapping(value = "/logout/{nonceToken}", method = RequestMethod.DELETE)
    public ResultDTO<?> logout(@PathVariable final String nonceToken) {
        return ResultDTO.success();
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

    private boolean isAccountLogin(final AuthenticationDTO dto) {
        return StringUtils.hasText(dto.getUsername());
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

        dto.setLoginId(model.getLoginId());
        dto.setPassword(model.getPassword());
        dto.setEnabled(model.isEnabled());
        dto.setAccountLocked(model.isAccountLocked());
        dto.setAccountExpired(model.isAccountExpired());
        dto.setCredentialsExpired(model.isCredentialsExpired());
        return dto;
    }
}
