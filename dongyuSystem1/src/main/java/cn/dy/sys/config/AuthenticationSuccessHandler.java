package cn.dy.sys.config;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import cn.dy.sys.util.json.JsonUtils;

public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
            final Authentication auth)
            throws IOException, ServletException {

        response.setStatus(200);
        response.addHeader("Content-Type", "application/json;charset=UTF-8");
        final Writer writer = response.getWriter();
        final String ajaxHeader = request.getHeader("X-Requested-With");
        final boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);
        if (isAjax) {
            writer.write(JsonUtils.pojoToJson(auth.getDetails()));
            writer.flush();
            writer.close();
        } else {
            super.onAuthenticationSuccess(request, response, auth);
        }

        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ((principal != null) && (principal instanceof UserDetails)) {
            final UserDetails user = (UserDetails) principal;
            System.out.println("loginUser:" + user.getUsername());
            //维护在session中
            request.getSession().setAttribute("userDetail", user);
            response.sendRedirect("/");
        }
    }
}
