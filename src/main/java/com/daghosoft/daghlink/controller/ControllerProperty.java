package com.daghosoft.daghlink.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.daghosoft.daghlink.service.ServiceInstall;
import com.daghosoft.daghlink.service.ServiceProperty;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/secure/property")
public class ControllerProperty {
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerProperty.class);
	@Autowired ServiceProperty serviceProperty;
	@Autowired ServiceInstall serviceInstall;
	
	@RequestMapping
	public String list(Model model,HttpSession session){
		return serviceProperty.list(model);
	}
	
	@RequestMapping(params={"create"},method=RequestMethod.POST)
	public String create(HttpServletRequest request){
		serviceProperty.create(request);
		return ("redirect:../secure/property");
	}
	
	@RequestMapping(params={"delete"},method=RequestMethod.GET)
	public String delete(int id){
		serviceProperty.delete(id);
		return ("redirect:../secure/property");
	}
	
	@RequestMapping(params={"saveList"},method=RequestMethod.POST)
	public String saveList(HttpServletRequest request){
		serviceProperty.saveList(request);
		return ("redirect:../secure/property");
	}
	
	@RequestMapping(params={"restore"},method=RequestMethod.GET)
	public String restore(){
		serviceInstall.defaultDb();
		return ("redirect:../secure/property");
	}

}
