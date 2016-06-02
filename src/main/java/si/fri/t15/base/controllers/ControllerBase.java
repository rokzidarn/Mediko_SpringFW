package si.fri.t15.base.controllers;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.support.RequestContextUtils;

import si.fri.t15.base.helpers.Utils;

public class ControllerBase {
	
	@Autowired
	protected EntityManager em;
	
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	@Autowired
	protected MessageSource translations;
	
	@ModelAttribute("_utils")
	public Class<Utils> getUtils() {
		return Utils.class;
	}
	
	protected String getTranslation(String translationKey, HttpServletRequest request) {
		return translations.getMessage(translationKey, null, RequestContextUtils.getLocale(request));
	}	
}
