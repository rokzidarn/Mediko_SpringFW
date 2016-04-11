package si.fri.t15;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import si.fri.t15.dao.UserDetailsService;
import si.fri.t15.validators.SignUpValidator;

@EnableGlobalMethodSecurity(securedEnabled = true)
//@Configuration
//@ComponentScan(basePackages={"si.fri.t15.dao", "si.fri.t15.base.security"})
@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/*
	 * This is the configuration for the messageSource bean, which when
	 * Autowired in a controller, gives us access to translation key values for
	 * the user's locale. NOTE: inside template files you can use a macro to get
	 * the value directly, example: #springMessage("login.title") will return
	 * the appropriate value according to the user's current locale.
	 */
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:translations/login/login");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}

	/*
	 * This is the configuration for the localeResolver bean, which uses a
	 * SessionLocaleResolver; that resolver allows us to remember the users'
	 * locale for the duration of their session.
	 */
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(new Locale("sl_SI"));
		return slr;
	}

	/*
	 * This is the configuration for the localeChangeInterceptor, which
	 * intercepts all requests from users before they arrive at the intended
	 * controller. If the request contains a request parameter with the name,
	 * defined in the configuration, then it uses the localeResolver bean to
	 * change the user's locale to the value specified in the request. Example:
	 * localhost:8080/login?lang=sl
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	/*
	 * Here we register the above localeChangeInterceptor with the
	 * InterceptorRegistry, so that requests go through it.
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService();
	}
	
	@Bean
	public SignUpValidator signUpValidator() {
		return new SignUpValidator();
	}
}