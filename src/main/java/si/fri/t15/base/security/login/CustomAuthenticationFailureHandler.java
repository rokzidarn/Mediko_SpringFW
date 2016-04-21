package si.fri.t15.base.security.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component("authenticationFailureHandler")
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) throws IOException, ServletException {
        setDefaultFailureUrl("/login?error");

        super.onAuthenticationFailure(request, response, exception);

        String errorMessage = "auth.message.bad_credentials";

        if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
            errorMessage = "auth.message.disabled";
        } else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
            errorMessage = "auth.message.expired";
        } else if (exception.getMessage().equalsIgnoreCase("User exceeded login attempts")) {
            errorMessage = "auth.message.ip_blocked";
        }

        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
    }
}