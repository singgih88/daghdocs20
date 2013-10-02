package com.daghosoft.daghlink.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.daghosoft.daghlink.bean.Group;
import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.dao.DaoGroupInterface;
import com.daghosoft.daghlink.dao.DaoLink;
import com.daghosoft.daghlink.util.Util;
import com.daghosoft.daghlink.util.UtilPagination;
import com.daghosoft.daghlink.validator.ValidatorGroup;
import com.daghosoft.daghlink.validator.ValidatorMsg;



/**
 * @author dagheisha
 * 
 */

public class ServiceGroup {

	private static final Logger logger = LoggerFactory.getLogger(ServiceGroup.class);

	DaoGroupInterface daoGroup;
	@Autowired ValidatorGroup validatorGroup;
	@Autowired HttpSession session;
	
	public void navigator(Model model, User user){
		model.addAttribute("linkGroupList", daoGroup.find(user));
	}

	public void showForm(Integer id, Model model) {
		Group group = daoGroup.get(id);
		model.addAttribute("shareList", shareList());
		model.addAttribute(group);
	}

	public void showForm(Model model) {
		String stype = (String)	session.getAttribute("contentListType");
		Group group = new Group();
		group.setDate(new Date());
		group.setType(stype);
		group.setShare("");
		model.addAttribute("shareList", shareList());
		model.addAttribute(group);
	}

	public String update(Group group, Model model, BindingResult errors) {
		User user = (User) session.getAttribute("userOs");
		validatorGroup.validate(group, errors);
		String out = null;
		if (!errors.hasErrors()) {
			daoGroup.update(group,user.getId());
			session.setAttribute("alertType","success");
			//out = "redirect:../secure/link";
		} else {
			//out = "linkjsp/form";
			session.setAttribute("alertType","error");
			model.addAttribute(group);
		}
		return out;
	}

	public String create(Group group, Model model, BindingResult errors) {
		validatorGroup.validate(group, errors);
		User user = (User) session.getAttribute("userOs");
		group.setUser_id(user.getId());
		group.setUuid(UUID.randomUUID().toString());
		String out = null;
		if (!errors.hasErrors()) {
			daoGroup.create(group);
			//out = "redirect:../secure/link";
			session.setAttribute("alertType","success");
		} else {
			//out = "linkjsp/form";
			session.setAttribute("alertType","error");
			model.addAttribute(group);
		}
		return out;
	}

	public void delete(int id) {
		User user = (User) session.getAttribute("userOs");
		session.setAttribute("alertType","success");
		daoGroup.delete(id,user.getId());
		session.setAttribute("groupId", 0);
	}

	public void switchGroup(Integer id, Model model) {
		User user = (User) session.getAttribute("userOs");
		if (id != 0 && id != null) {
			Group group = daoGroup.get(id);
			if(group.getUser_id()==user.getId()){
				session.setAttribute("groupId", id);
				session.setAttribute("groupUuid", group.getUuid());
			}
		} else {
			session.setAttribute("groupId", 0);
			session.setAttribute("groupTitle", "");
		}
	}

	public void setDaoGroup(DaoGroupInterface daoGroup) {
		this.daoGroup = daoGroup;
	}
	
	private Map<String, String> shareList() {
		Map<String, String> shareList = new HashMap<String, String>();
		shareList.put("", "filelist");
		shareList.put("gallery", "gallery");
		return shareList;
	}

}
