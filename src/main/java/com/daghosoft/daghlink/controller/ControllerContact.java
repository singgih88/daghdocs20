package com.daghosoft.daghlink.controller;


import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.daghosoft.daghlink.bean.Contact;
import com.daghosoft.daghlink.service.ServiceContact;
import com.daghosoft.daghlink.util.UtilPagination;

@Controller
@RequestMapping("/secure/contact")
public class ControllerContact {
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerContact.class);
	@Autowired HttpSession session;
	@Autowired ServiceContact serviceContact;
	@Autowired UtilPagination page;
	
	
	@RequestMapping
	public String list(Model model){
		return serviceContact.list(model);
	}
	@RequestMapping(params={"next"},method=RequestMethod.GET)
	public String next(Model model) {
		page.next();
		return serviceContact.list(model);
	}
	@RequestMapping(params={"page"},method=RequestMethod.GET)
	public String page(Model model,int pageid) {
		page.page(pageid);
		return serviceContact.list(model);
	}
	@RequestMapping(params={"prev"},method=RequestMethod.GET)
	public String prev(Model model) {
		page.prev();
		return serviceContact.list(model);
	}
	
	@RequestMapping(params = { "create" }, method = RequestMethod.GET)
	public String showForm(Model model) {
		return serviceContact.showForm(model);
	}
	
	@RequestMapping(params = { "create" }, method = RequestMethod.POST)
	public String create(Contact contact, Model model, BindingResult errors) {
		page.clear();
		return serviceContact.create(contact, model, errors);
	}
	
	@RequestMapping(params = { "update" }, method = RequestMethod.GET)
	public String showForm(Integer id, Model model) {
		return serviceContact.showForm(model,id);
	}
	
	@RequestMapping(params = { "update" }, method = RequestMethod.POST)
	public String update(Contact contact, Model model, BindingResult errors) {
		page.clear();
		return serviceContact.update(contact, model, errors);
	}
	
	@RequestMapping(params={"delete"},method=RequestMethod.GET)
	public String delete(Integer id,Model model) {
		page.clear();
		return serviceContact.delete(id,model);
	}
}
