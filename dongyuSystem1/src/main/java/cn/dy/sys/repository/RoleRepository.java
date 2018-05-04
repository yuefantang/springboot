package cn.dy.sys.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import cn.dy.sys.model.Role;

public interface RoleRepository extends Repository<Role, Long> {

    List<Role> findAllByUserId(String userId);

    Role save(Role user);
}
