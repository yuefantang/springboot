package cn.dy.sys.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import cn.dy.sys.util.encode.BCryptPasswordEncoder;
import cn.dy.sys.util.encode.PasswordEncoder;

@Configuration
//@EnableAuthentication
public class WebConfig implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(WebConfig.class);

    @Value("${spring.profiles.active}")
    private String activeProfiles;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        WebConfig.log.info("*******************************************************************");
        WebConfig.log.info("              spring.profiles.active is: " + this.activeProfiles);
        WebConfig.log.info("*******************************************************************");
    }
}
