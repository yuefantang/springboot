package cn.dy.sys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig
        extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public AccountAuthenticationProvider accountAuthenticationProvider() {
        return new AccountAuthenticationProvider();
    }

    @Bean
    public AccountAuthenticationFilter accountAuthenticationFilter() {
        final AccountAuthenticationFilter filter = new AccountAuthenticationFilter();
        filter.setAuthenticationManager(this.authenticationManager);
        if (this.authenticationManager instanceof ProviderManager) {
            ((ProviderManager) this.authenticationManager).getProviders().add(this.accountAuthenticationProvider());
        }
        filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(new DefaultAuthenticationFailureHandler());
        return filter;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/js/**", "/lib/**", "/css/**", "/images/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic().disable()
                .addFilterBefore(this.accountAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/index")//登陆页面
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("remember-me", "JSESSIONID")
                .and()
                .rememberMe().disable()
                .csrf().disable() // cross site request forgery checks are not possible with static pages
                .headers().frameOptions().disable(); // H2 Console uses frames
    }
}
