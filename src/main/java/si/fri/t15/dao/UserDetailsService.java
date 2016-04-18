package si.fri.t15.dao;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import si.fri.t15.base.security.login.LoginAttemptService;
import si.fri.t15.models.user.User;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private LoginAttemptService loginAttemptService;

	@Autowired
	private HttpServletRequest request;

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		if (loginAttemptService.isBlocked(getClientIP())) {
			throw new RuntimeException("User exceeded login attempts");
		}

		User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Username: " + username);
		}
		Hibernate.initialize(user.getUserRoles());
		return user;
	}

	private String getClientIP() {
		String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null) {
			return request.getRemoteAddr();
		}
		return xfHeader.split(",")[0];
	}
}