package com.daghosoft.daghlink.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.daghosoft.daghlink.bean.Group;
import com.daghosoft.daghlink.bean.Link;
import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.dao.DaoGroupInterface;
import com.daghosoft.daghlink.dao.DaoLink;
import com.daghosoft.daghlink.service.ServiceLink;
import com.daghosoft.daghlink.util.Util;
import com.daghosoft.daghlink.validator.ValidatorLink;

@Controller
@RequestMapping("secure/bookmarklet")

public class ControllerLinkTool {

	private static final Logger logger = LoggerFactory.getLogger(ControllerLinkTool.class);	
	@Autowired DaoLink daoLink;
	@Autowired ValidatorLink validatorLink;
	@Autowired ServiceLink serviceLink;
	@Autowired @Qualifier("daoGroupLink") DaoGroupInterface daoGroup;
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@RequestMapping()
	public String showform(Model model,String title,String url,HttpSession session){
		Link link = new Link();
		link.setLink(url);
		link.setTitle(title);
		link.setFather(0);
		link.setDate(new Date());
		serviceLink.showForm(link, model, session);
		return "linkjsp/formB";
	}
	
	@RequestMapping(params={"create"},method=RequestMethod.POST)
	public String createB(Link link,Model model,BindingResult errors,HttpSession session) {
		validatorLink.validate(link, errors);
		link.setType("");
		User user = (User) session.getAttribute("userOs");
		link.setUser_id(user.getId());
		link.setUuid(UUID.randomUUID().toString());
		if(!errors.hasErrors()){
			daoLink.create(link);
			model.addAttribute("close", true);
		}else{
			model.addAttribute(link);
			List<Group> linkGroupList = daoGroup.find(user);
			model.addAttribute("linkGroupList",Util.selectGroup(linkGroupList));
		}
		return "linkjsp/formB";
	}
	
}
