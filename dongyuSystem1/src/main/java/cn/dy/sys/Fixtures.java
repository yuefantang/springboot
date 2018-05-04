package cn.dy.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.dy.sys.model.Role;
import cn.dy.sys.model.User;
import cn.dy.sys.repository.RoleRepository;
import cn.dy.sys.repository.UserRepository;
import cn.dy.sys.util.encode.PasswordEncoder;

/**
 * Production fixtures
 */
@Component
@Transactional
@Profile("dev")
public class Fixtures implements Ordered, ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            //  this.configure();
        }
    }

    private void configure() {
        final User admin = new User();
        admin.setLoginId("admin");
        admin.setPassword(this.passwordEncoder.encode("rd1234"));
        final User save = this.userRepository.save(admin);

        final Role role = new Role();
        role.setName("admin");
        role.setDescript("manger");
        role.setUserId(String.valueOf(save.getId()));

        this.roleRepository.save(role);

    }
}
