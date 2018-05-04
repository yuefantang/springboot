package cn.dy.sys.config;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import cn.dy.sys.dto.common.ResultDTO;
import cn.dy.sys.exception.Error;
import cn.dy.sys.util.json.JsonUtils;

public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final String ERROR_CODE_USERNAME_PASSWORD = "username&password";

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response,
            final AuthenticationException exception) throws IOException, ServletException {
        final Error error = new Error("401", exception.getMessage(),
                DefaultAuthenticationFailureHandler.ERROR_CODE_USERNAME_PASSWORD);
        final ResultDTO<?> rs = ResultDTO.failure(error);
        response.setStatus(200);
        response.addHeader("Content-Type", "application/json;charset=UTF-8");
        final Writer writer = response.getWriter();
        writer.write(JsonUtils.pojoToJson(rs));
        writer.flush();
        writer.close();
    }
}
