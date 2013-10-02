package com.daghosoft.daghlink.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.daghosoft.daghlink.bean.Link;
import com.daghosoft.daghlink.bean.Msg;
import com.daghosoft.daghlink.service.ServiceLink;
import com.daghosoft.daghlink.util.ReadMail;
import com.daghosoft.daghlink.util.UtilMail;
import com.daghosoft.daghlink.util.UtilPagination;

@Controller
@RequestMapping("/secure/link")
public class ControllerLink {

	private static final Logger logger = LoggerFactory.getLogger(ControllerLink.class);
	@Autowired ServiceLink serviceLink;
	@Autowired UtilMail sendmail;
	@Autowired UtilPagination page;
	@Autowired HttpSession session;
	@Autowired HttpServletRequest request;
	@Autowired ReadMail read;
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	@RequestMapping
	public String list(Model model,HttpSession session){
		return serviceLink.list(model,session);
	}
	@RequestMapping(params={"next"},method=RequestMethod.GET)
	public String next(Model model) {
		page.next();
		return serviceLink.list(model,session);
	}
	@RequestMapping(params={"page"},method=RequestMethod.GET)
	public String page(Model model,int pageid) {
		page.page(pageid);
		return serviceLink.list(model,session);
	}
	@RequestMapping(params={"prev"},method=RequestMethod.GET)
	public String prev(Model model,HttpSession session) {
		page.prev();
		return serviceLink.list(model,session);
	}
	
	//########################################################################
	
	@RequestMapping(params={"create"},method=RequestMethod.GET)
	public String showForm(Model model,HttpSession session) {
		serviceLink.showCreateForm(model, session);
		return "linkjsp/form";
	}
	
	@RequestMapping(params={"create"},method=RequestMethod.POST)
	public String createLink(Link link,Model model,BindingResult errors,HttpSession session) {
		page.clear();
		return serviceLink.create(link, model, errors, session);
	}
	
	@RequestMapping(params={"update"},method=RequestMethod.GET)
	public String showForm(Integer id,Model model,HttpSession session) {
		serviceLink.showUpdateForm(id, model, session);
		return "linkjsp/form";
	}
	@RequestMapping(params={"update"},method=RequestMethod.POST)
	public String update(Link link,Model model,BindingResult errors,HttpSession session) {
		page.clear();
		return serviceLink.update(link, model, errors, session);
	}
	@RequestMapping(params={"delete"},method=RequestMethod.GET)
	public String delete(Integer id,Model model) {
		page.clear();
		serviceLink.delete(id);
		return list(model,session);
	}
	@RequestMapping(params={"multiple"},method=RequestMethod.POST)
	public String Multiple(Model model,HttpServletRequest request) {
		page.clear();
		if(request.getParameter("step").equals("delete")){
			serviceLink.deleteMultiple(request);
		}else{
			serviceLink.moveMultiple(request);
		}
		
		return serviceLink.list(model,session);
	}

	
	
	//#################
	
	@RequestMapping(params={"share"},method=RequestMethod.GET)
	public String share(Model model,HttpSession session) {
		return serviceLink.share(model,session);
	}
	@RequestMapping(params={"share"},method=RequestMethod.POST)
	public String share(Msg msg,Model model,BindingResult errors) {
		msg.setTo(msg.getTo()+";"+request.getParameter("copyTo"));
		return serviceLink.send(msg,model,errors,session);
	}
}
