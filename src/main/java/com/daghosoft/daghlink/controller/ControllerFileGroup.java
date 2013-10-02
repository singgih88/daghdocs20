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
import com.daghosoft.daghlink.dao.DaoFile;
import com.daghosoft.daghlink.service.ServiceGroup;
import com.daghosoft.daghlink.util.ReadMail;
import com.daghosoft.daghlink.util.UtilMail;
import com.daghosoft.daghlink.util.UtilPagination;

@Controller
@RequestMapping("/secure/groupFile")
public class ControllerFileGroup {

	private static final Logger logger = LoggerFactory.getLogger(ControllerFileGroup.class);
	@Autowired @Qualifier("grpfile") ServiceGroup serviceGroupFile;
	@Autowired DaoFile daoFile;
	@Autowired UtilMail sendmail;
	@Autowired UtilPagination page;
	@Autowired HttpSession session;
	@Autowired ReadMail read;
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	/*@RequestMapping(params={"enterGroup"},method=RequestMethod.GET)
	public void enterGroup(Integer id,Model model){
		serviceLink.switchGroup(id, model, session);
	}*/
	@RequestMapping(params={"create"},method=RequestMethod.GET)
	public String showFormGroup(Model model,HttpSession session) {
		serviceGroupFile.showForm(model);
		return "groupjsp/form";
	}
	@RequestMapping(params={"create"},method=RequestMethod.POST)
	public String createLinkGroup(Group group,Model model,BindingResult errors) {
		serviceGroupFile.create(group, model, errors);
		return "redirect:../secure/file";
	}
	@RequestMapping(params={"update"},method=RequestMethod.GET)
	public String showForm(Integer id,Model model) {
		serviceGroupFile.showForm(id, model);
		return "groupjsp/form";
	}
	@RequestMapping(params={"update"},method=RequestMethod.POST)
	public String update(Group group,Model model,BindingResult errors) {
		page.clear();
		//return serviceGroupLink.update(group, model, errors);
		serviceGroupFile.update(group, model, errors);
		return "redirect:../secure/file";
	}
	@RequestMapping(params={"delete"},method=RequestMethod.GET)
	public String delete(Integer id,Model model) {
		page.clear();
		serviceGroupFile.delete(id);
		daoFile.resetFather(id);
		return "redirect:../secure/file";
	}
}
