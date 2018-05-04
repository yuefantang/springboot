package cn.dy.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dy.sys.model.User;
import cn.dy.sys.repository.UserRepository;
import cn.dy.sys.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserByIdentity(final String identity) {
        final User user = this.userRepository.findOneByLoginId(identity);
        if (user != null) {
            return user;
        }
        return null;
    }

}
