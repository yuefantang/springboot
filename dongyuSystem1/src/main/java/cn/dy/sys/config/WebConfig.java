package cn.dy.sys.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import cn.dy.sys.util.encode.BCryptPasswordEncoder;
import cn.dy.sys.util.encode.PasswordEncoder;
import cn.dy.sys.util.excel.AbstractExcelViewNew;

@Configuration
//@EnableAuthentication
public class WebConfig implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(WebConfig.class);

    @Value("${spring.profiles.active}")
    private String activeProfiles;
    @Autowired
    private ApplicationContext applicationContext;

    /*
     * Configure ContentNegotiatingViewResolver
     */
    @Bean
    public ViewResolver contentNegotiatingViewResolver(final ContentNegotiationManager manager) {
        final ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);

        final List<ViewResolver> resolvers = new ArrayList<ViewResolver>();

        resolvers.add(this.excelViewResolver());

        resolver.setViewResolvers(resolvers);
        return resolver;
    }

    @Bean
    public ViewResolver excelViewResolver() {
        return new ExcelViewResolver();
    }

    public class ExcelViewResolver implements ViewResolver {

        @Override
        public View resolveViewName(final String viewName, final Locale locale) throws Exception {
            if (!WebConfig.this.applicationContext.containsBean(viewName)) {
                return null;
            }
            final Object bean = WebConfig.this.applicationContext.getBean(viewName);
            //            if (!(bean instanceof AbstractExcelView)) {
            if (!((bean instanceof AbstractExcelView) || (bean instanceof AbstractExcelViewNew))) {
                return null;
            }
            return ((View) bean);
        }
    }

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
