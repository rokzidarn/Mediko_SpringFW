package si.fri.t15.base.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.support.RequestContextUtils;

public class ControllerBase {
	
	@Autowired
	private MessageSource translations;
	
	protected String getTranslation(String translationKey, HttpServletRequest request) {
		return translations.getMessage(translationKey, null, RequestContextUtils.getLocale(request));
	}
	
}
