package si.fri.t15.patient.controllers;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import si.fri.t15.base.controllers.ControllerBase;
import si.fri.t15.dao.UserRepository;
import si.fri.t15.models.user.User;
import si.fri.t15.validators.ForgottenPasswordForm;
import si.fri.t15.validators.ForgottenPasswordValidator;

@Controller
public class ForgottenPasswordController extends ControllerBase {

	@Autowired
	ForgottenPasswordValidator forgottenPasswordValidator;

	@InitBinder("command")
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.setValidator(forgottenPasswordValidator);
	}

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	UserRepository userRepo;

	@Value("${proxy.realPath:#{null}}")
	private String domain;

	@RequestMapping(value = "/forgotten", method = RequestMethod.GET)
	public String forgottenPasswordGET(Model model, HttpServletRequest request) {

		model.addAttribute("title", "Pozabljeno geslo");

		return "forgotten";
	}

	@Transactional
	@RequestMapping(value = "/forgotten", method = RequestMethod.POST)
	public String forgottenPasswordPOST(Model model, HttpServletRequest request) {

		model.addAttribute("title", "Pozabljeno geslo");

		User user = userRepo.findByUsername(request.getParameter("username"));
		if (user != null) {
			String token = UUID.randomUUID().toString().substring(0, 15);
			user.setPasswordResetToken(token);
			em.merge(user);
			request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
					"Preverite poštni predal za ponastavitveno povezavo.");
			String tString = null;
			String cpath = request.getContextPath().toString();
			if (domain != null) {
				tString = domain + cpath;
			} else {
				domain = request.getRequestURL().toString();
				tString = domain.substring(0, domain.indexOf(cpath) + cpath.length());
			}
			signUpConfirmationMail(user, tString, token);
		} else {
			request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, "Neveljaven email.");
		}

		return "forgotten";
	}

	@RequestMapping(value = "/confirmForgotten", method = RequestMethod.GET)
	public String confirmForgottenGET(Model model, HttpServletRequest request, @RequestParam String token) {
		User user = userRepo.findByPasswordResetToken(token);
		if (user != null) {
			model.addAttribute("title", "Sprememba gesla");
			return "forgottenInput";
		} else {
			request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, "Neveljaven žeton.");
			return "forgotten";
		}
	}

	@Transactional
	@RequestMapping(value = "/confirmForgotten", method = RequestMethod.POST)
	public String confirmForgottenPOST(Model model, HttpServletRequest request,
			@ModelAttribute("command") @Valid ForgottenPasswordForm command, BindingResult result,
			@RequestParam String token) {
		User user = userRepo.findByPasswordResetToken(token);
		if (user != null) {
			user.setPassword(passwordEncoder.encode(command.getPassword()));
			request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, "Uspešno ponastavljanje gesla.");
			return "redirect:/login";
		} else {
			request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, "Neveljaven žeton.");
			return "forgotten";
		}
	}

	private void signUpConfirmationMail(User user, String URL, String token) {
		MimeMessage mail = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
			helper.setTo(user.getUsername());
			helper.setFrom("mediko@sladic.si");
			helper.setSubject("Potrditvena povezava za ponastavitev gesla");
			helper.setText("Prosimo, kliknite na povezavo, da ponastavite geslo v sistemu Mediko.\n" + URL
					+ "/confirmForgotten?token=" + token);
		} catch (MessagingException e) {
			e.printStackTrace();
		} finally {
		}
		mailSender.send(mail);
	}
}
