package com.daghosoft.daghlink.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.daghosoft.daghlink.dao.DaoLink;
import com.daghosoft.daghlink.service.ServiceGroup;
import com.daghosoft.daghlink.service.ServiceLink;
import com.daghosoft.daghlink.util.ReadMail;
import com.daghosoft.daghlink.util.UtilMail;
import com.daghosoft.daghlink.util.UtilPagination;

@Controller
@RequestMapping("/secure/groupLink")
public class ControllerLinkGroup {

	private static final Logger logger = LoggerFactory.getLogger(ControllerLinkGroup.class);
	@Autowired @Qualifier("grplink") ServiceGroup serviceGroupLink;
	@Autowired ServiceLink serviceLink;
	@Autowired DaoLink daoLink;
	@Autowired UtilMail sendmail;
	@Autowired UtilPagination page;
	//@Autowired HttpSession session;
	@Autowired ReadMail read;
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@RequestMapping(params={"enterGroup"},method=RequestMethod.GET)
	public String enterGroup(Integer id,Model model){
		page.clear();
		serviceGroupLink.switchGroup(id, model);
		return "redirect:../secure/link";
	}
	@RequestMapping(params={"create"},method=RequestMethod.GET)
	public String showFormGroup(Model model,HttpSession session) {
		serviceGroupLink.showForm(model);
		return "groupjsp/formLink";
	}
	@RequestMapping(params={"create"},method=RequestMethod.POST)
	public String createLinkGroup(Group group,Model model,BindingResult errors) {
		serviceGroupLink.create(group, model, errors);
		return "redirect:../secure/link";
	}
	@RequestMapping(params={"update"},method=RequestMethod.GET)
	public String showForm(Integer id,Model model) {
		serviceGroupLink.showForm(id, model);
		return "groupjsp/formLink";
	}
	@RequestMapping(params={"update"},method=RequestMethod.POST)
	public String update(Group group,Model model,BindingResult errors) {
		page.clear();
		serviceGroupLink.update(group, model, errors);
		return "redirect:../secure/link";
	}
	@RequestMapping(params={"delete"},method=RequestMethod.GET)
	public String delete(Integer id,Model model) {
		page.clear();
		serviceGroupLink.delete(id);
		daoLink.resetFather(id);
		return "redirect:../secure/link";
	}
}
