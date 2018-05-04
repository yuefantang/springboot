package cn.dy.sys.service;

import org.hibernate.validator.constraints.NotBlank;

import cn.dy.sys.model.User;

public interface UserService {

    User findUserByIdentity(@NotBlank final String identity);

}
