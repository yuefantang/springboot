package cn.dy.sys.config;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import cn.dy.sys.dto.common.ResultDTO;
import cn.dy.sys.util.json.JsonUtils;

public class AccountAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String PASSWORD_KEY = "password";
    public static final String LOGINID_KEY = "userName";
    private final boolean postOnly = true;

    protected AccountAuthenticationFilter() {
        super("/login");
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
            final Authentication authResult) throws IOException, ServletException {

        this.writeResponse(authResult, request, response);
        super.successfulAuthentication(request, response, authResult);
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        if ((this.postOnly) && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        final AccountAuthenticationToken authRequest = this.obtainAuthenticationToken(request);
        final Authentication auth = this.getAuthenticationManager().authenticate(authRequest);

        return auth;
    }

    protected AccountAuthenticationToken obtainAuthenticationToken(final HttpServletRequest request) {
        AccountAuthenticationToken authToken = new AccountAuthenticationToken();
        if (request.getMethod().equals(HttpMethod.GET.name())) {

            authToken.setUserName(request.getParameter(AccountAuthenticationFilter.LOGINID_KEY));
            authToken.setPassword(request.getParameter(AccountAuthenticationFilter.PASSWORD_KEY));

        } else if (request.getMethod().equals(HttpMethod.POST.name())) {
            try {
                final InputStreamReader reader = new InputStreamReader(request.getInputStream());

                authToken = JsonUtils.jsonToPojo(reader, AccountAuthenticationToken.class);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        return authToken;
    }

    private void writeResponse(final Authentication authResult, final HttpServletRequest request,
            final HttpServletResponse response) throws IOException {
        // 先要将认证结果写入线程上下文，否则会丢失认证结果
        response.addHeader("Content-Type", "application/json;charset=UTF-8");
        SecurityContextHolder.getContext().setAuthentication(authResult);
        final PrintWriter writer = response.getWriter();
        final ResultDTO<?> rs = ResultDTO.success(authResult.getDetails());
        writer.write(JsonUtils.pojoToJson(rs));
        writer.flush();
        writer.close();
    }
}
