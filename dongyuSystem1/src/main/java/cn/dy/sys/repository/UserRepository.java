package cn.dy.sys.repository;

import org.springframework.data.repository.Repository;

import cn.dy.sys.model.User;

public interface UserRepository extends Repository<User, Long> {

    User findOneByLoginId(String loginId);

    User save(User user);
}
