package com.daghosoft.daghlink.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.daghosoft.daghlink.dao.DaoLink;
import com.daghosoft.daghlink.dao.DaoUser;
import com.daghosoft.daghlink.service.ServiceMail2;

@Controller
public class ControllerLogin {
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerLogin.class);
 
	@Autowired 	DaoUser daoUser;
	@Autowired 	DaoLink daoLink;   
	@Autowired 	ServiceMail2 serviceMail2;	
	
	@RequestMapping(value="/secure/welcome", method = RequestMethod.GET)
	public String printWelcome(Model model, Principal principal,HttpSession session ) {
		model.addAttribute("username", principal.getName());
		return "home";
	}
 
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(ModelMap model) {
		return "login";
	}
 
	@RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
		model.addAttribute("error", "true");
		return "login";
	}
 
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(ModelMap model, Principal principal) {
		return "logout";
	}
	
	@RequestMapping(value="/retrieve", method = RequestMethod.GET)
	public String retrieve(String mail) {
		serviceMail2.passwordNotify(mail);
		return "login";
	}
}
