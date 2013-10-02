package com.daghosoft.daghlink.controller;


import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.daghosoft.daghlink.service.ServiceInstall;
import com.daghosoft.daghlink.service.ServiceProperty;
import com.daghosoft.daghlink.util.UtilFile;

@Secured("ROLE_ADMIN")
@Controller
public class ControllerInstall {
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerInstall.class);
	
	@Autowired UtilFile utilFile;
	@Autowired HttpSession session;
	@Autowired ServiceInstall serviceInstall;
	@Autowired ServiceProperty serviceProperty;
	
	@RequestMapping(value="/secure/copyfiles", method = RequestMethod.GET)
	public String copy() {
		utilFile.restoreFiles();
		session.setAttribute("alertType","success");
		return "redirect:../secure/property";
	}
	
	@RequestMapping(value="/secure/deltadb", method = RequestMethod.GET)
	@ResponseBody
	public String execute() {
		String lock = serviceProperty.get("general.sql.lock");
		Boolean out = false;
		if (lock!=null && lock.equals("unlocked")){
			out= serviceInstall.deltaDb();
		}
		logger.info("Delta db execution result : {}",out);
		return "Execute Sql : "+out;
	}
	
	/*@RequestMapping(value="/createdb", method = RequestMethod.GET)
	@ResponseBody
	public String createDb() {
		//String lock = serviceProperty.get("general.sql.lock");
		String lock= "unlocked";
		Boolean out = false;
		if (lock!=null && lock.equals("unlocked")){
			out= serviceInstall.createDb();
		}
		logger.info("Db creation result : {}",out);
		return "Execute Sql : "+out;
	}
	*/
}
