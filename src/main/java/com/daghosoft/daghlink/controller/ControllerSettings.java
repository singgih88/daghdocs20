package com.daghosoft.daghlink.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.service.ServiceImap;
import com.daghosoft.daghlink.service.ServiceProperty;
import com.daghosoft.daghlink.service.ServiceUser;
import com.daghosoft.daghlink.util.UtilPagination;

@Controller
@RequestMapping("/secure/settings")
public class ControllerSettings {//implements HandlerExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger(ControllerSettings.class);
	
	@Autowired ServiceUser serviceUser;
	@Autowired ServiceImap serviceImap;
	@Autowired ServiceProperty serviceProperty;
	@Autowired HttpSession session;
	@Autowired UtilPagination utilPagination; 
	@Autowired HttpServletRequest request;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	
	}

	@RequestMapping
	public String showForm(Model model, HttpSession session) {
		serviceUser.showFormSettings(model, session);
		utilPagination.type("property");
		return "userjsp/formsettings";
	}

	@RequestMapping(params = { "updateSettings" }, method = RequestMethod.POST)
	public String updateSettings(User user, Model model, Errors errors,HttpSession session) {
		serviceUser.updateSettings(user, errors, session);
		return "redirect:../secure/settings?settings=active";
	}
	@RequestMapping(params = { "updateAvatar" }, method = RequestMethod.POST)
	public String updateAvatar() {
		serviceUser.updateAvatar();
		return "redirect:../secure/settings?avatar=active";
	}

	@RequestMapping(params = { "deleteAvatar" }, method = RequestMethod.GET)
	public String deleteAvatar(HttpSession session) {
		serviceUser.deleteAvatar(session);
		return "redirect:../secure/settings?avatar=active";
	}
	
	@RequestMapping(params = { "updateShare" }, method = RequestMethod.POST)
	public String updateShare(HttpServletRequest request) {
		serviceUser.updateShare(request);
		return "redirect:../secure/settings?share=active";
	}
	
	@RequestMapping(params = { "updateImap" }, method = RequestMethod.POST)
	public String updateImap(HttpServletRequest request) {
		serviceImap.updateImap(request);
		return "redirect:../secure/settings?imap=active";
	}
	
	@RequestMapping(params = { "itemInPage" }, method = RequestMethod.GET)
	@ResponseBody
	public String itemInPage(int num) {
		serviceUser.itemInPage(num);
		return "Item in page :"+num;
	}
}
