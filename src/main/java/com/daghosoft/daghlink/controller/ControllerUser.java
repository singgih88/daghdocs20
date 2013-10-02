package com.daghosoft.daghlink.controller;


import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.dao.DaoQueue;
import com.daghosoft.daghlink.service.ServiceGroup;
import com.daghosoft.daghlink.service.ServiceProperty;
import com.daghosoft.daghlink.service.ServiceUser;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/secure/user")

public class ControllerUser {

	private static final Logger logger = LoggerFactory.getLogger(ControllerUser.class);

	@Autowired ServiceUser serviceUser;
	@Autowired HttpSession session;
	@Autowired HttpServletRequest request;
	@Autowired ServiceProperty serviceProperty;
	

	@RequestMapping
	public String list(Model model) {
		return serviceUser.list(model);
	}

	@RequestMapping(params = { "create" }, method = RequestMethod.GET)
	public String showForm(Model model, HttpServletRequest request) {
		return serviceUser.showCreateForm(model);
	}

	@RequestMapping(params = { "create" }, method = RequestMethod.POST)
	public String create(User user, Model model, BindingResult errors) {
		return serviceUser.create(user, model, errors);
	}
	@RequestMapping(params = { "notify" }, method = RequestMethod.GET)
	public String notify(int id) {
		return serviceUser.notify(id);
	}

	@RequestMapping(params = { "update" }, method = RequestMethod.GET)
	public String showForm(Integer id, Model model, HttpServletRequest request) {
		return serviceUser.showUpdateForm(model,id);
	}

	@RequestMapping(params = { "update" }, method = RequestMethod.POST)
	public String update(User user, Model model, BindingResult errors,HttpServletRequest request) {
		return serviceUser.update(user, errors, model);
	}

	@RequestMapping(params = { "delete" }, method = RequestMethod.GET)
	public String delete(int id,Model model) {
		return serviceUser.delete(id,model);
	}
	
	@RequestMapping(params = { "actas" }, method = RequestMethod.GET)
	public String actas(String userPrincipal) {
		serviceProperty.clearSession();
		serviceUser.getUserOs(session, userPrincipal);
		serviceUser.reloadUser(session);
		setPrincipal();
		return "redirect:../secure/file";
	}
	
	private void setPrincipal(){
		Principal principal = request.getUserPrincipal();
		session.setAttribute("actas", principal.getName());
		User userOs = (User) session.getAttribute("userOs");
		if(userOs.getUsername().equals(principal.getName())){
			session.setAttribute("actas", null);
		}
	}
}
