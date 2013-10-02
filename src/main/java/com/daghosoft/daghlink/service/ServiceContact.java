package com.daghosoft.daghlink.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import com.daghosoft.daghlink.bean.Contact;
import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.dao.DaoContact;
import com.daghosoft.daghlink.util.UtilPagination;
import com.daghosoft.daghlink.validator.ValidatorContact;

@Service
public class ServiceContact {

	private static final Logger logger = LoggerFactory.getLogger(ServiceContact.class);
	@Autowired DaoContact daoContact;
	@Autowired HttpSession session;
	@Autowired ValidatorContact validatorContact;
	@Autowired UtilPagination utilPagination;
	
	public String list(Model model){
		User user = (User) session.getAttribute("userOs");
		List<Contact> list = daoContact.find(user.getId());
		utilPagination.type("contact");
		utilPagination.pagedList(list);
		return "contactjsp/list";
	};
	
	public String showForm(Model model){
		Contact contact = new Contact();
		model.addAttribute("contact", contact);
		return "contactjsp/form";
	};
	
	public String showForm(Model model,Integer id){
		Contact contact = daoContact.get(id);
		model.addAttribute("contact", contact);
		return "contactjsp/form";
	};
	
	public String create(Contact contact,Model model,Errors errors){
		validatorContact.validate(contact, errors);
		String out="";
		if(!errors.hasErrors()){
			User user = (User) session.getAttribute("userOs");
			contact.setUser_id(user.getId());
			daoContact.create(contact);
			session.setAttribute("alertType","success");
			out="redirect:../secure/contact";
		}else{
			model.addAttribute(contact);
			session.setAttribute("alertType","error");
			out="contactjsp/form";
		}
		return out;
	};
	
	
	public String update(Contact contact,Model model,Errors errors){
		validatorContact.validate(contact, errors);
		String out="";
		if(!errors.hasErrors()){
			User user = (User) session.getAttribute("userOs");
			daoContact.update(contact,user.getId());
			session.setAttribute("alertType","success");
			out="redirect:../secure/contact";
		}else{
			model.addAttribute(contact);
			session.setAttribute("alertType","error");
			out="contactjsp/form";
		}
		return out;
	};
	
	public String delete(int id,Model model) {
		User user = (User) session.getAttribute("userOs");
		daoContact.delete(id,user.getId());
		session.setAttribute("alertType","success");
		return "redirect:../secure/contact";
	};
	
	public void addToMyContact(String[] to){
		User user = (User) session.getAttribute("userOs");
		for(int i=0; i<to.length; i++){
			List<Contact> list = daoContact.findByMail(to[i], user.getId());
			if (list.size()<1){
				Contact contact = new Contact();
				contact.setMail(to[i]);
				contact.setUser_id(user.getId());
				daoContact.create(contact);
			}
		}
	};
	
}
