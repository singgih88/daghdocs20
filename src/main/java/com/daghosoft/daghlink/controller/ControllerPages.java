package com.daghosoft.daghlink.controller;


import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.daghosoft.daghlink.bean.Group;
import com.daghosoft.daghlink.dao.DaoGroupInterface;
import com.daghosoft.daghlink.util.Util;
import com.daghosoft.daghlink.util.UtilPagination;



/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/")
public class ControllerPages {
	
	@Autowired Util util;
	@Autowired UtilPagination utilPagination;
	@Autowired @Qualifier("daoGroupFile") DaoGroupInterface daoGroup;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model,HttpSession session) {
		return "home";
	}
	
	@RequestMapping(value = "/secure/tools", method = RequestMethod.GET)
	public String tools(Locale locale, Model model,HttpSession session) {
		model.addAttribute("url",util.getInstalledUrl()+"/secure/bookmarklet");
		utilPagination.type("tools");
		return "pagesjsp/tools";
	}
	@RequestMapping(value = "/secure/info", method = RequestMethod.GET)
	public String info(Locale locale, Model model,HttpSession session) {
		utilPagination.type("info");
		return "pagesjsp/info";
	}
	@RequestMapping(value = "/secure/credits", method = RequestMethod.GET)
	public String credits() {
		utilPagination.type("credits");
		return "pagesjsp/credits";
	}
	
	@RequestMapping(value = "/gallery", method = RequestMethod.GET)
	public String tools(Model model,String uuid) {
		Group group =  daoGroup.get(uuid);
		model.addAttribute("uuid",uuid);
		model.addAttribute("title", group.getTitle());
		model.addAttribute("total", group.getChild());
		model.addAttribute("share", group.getShare());
		return "filejsp/gallery";
	}
}
