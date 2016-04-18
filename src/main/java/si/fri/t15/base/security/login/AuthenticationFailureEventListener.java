package si.fri.t15.base.security.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureEventListener
		implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	@Autowired
	private LoginAttemptService loginAttemptService;
	
	@Autowired
	private HttpServletRequest request;

	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
		loginAttemptService.loginFailed(getClientIP());
	}
	
	private String getClientIP() {
		String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null) {
			return request.getRemoteAddr();
		}
		return xfHeader.split(",")[0];
	}
}