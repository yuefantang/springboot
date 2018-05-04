package cn.dy.sys.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Secured {

    /**
     * Returns the list of security configuration attributes (e.g. ROLE_USER,
     * ROLE_ADMIN).
     *
     * @return String[] The secure method attributes
     */
    public String[] value();
}
