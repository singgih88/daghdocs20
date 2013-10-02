package com.daghosoft.daghlink.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import com.daghosoft.daghlink.bean.Group;
import com.daghosoft.daghlink.bean.Link;
import com.daghosoft.daghlink.bean.Msg;
import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.dao.DaoGroupInterface;
import com.daghosoft.daghlink.dao.DaoLink;
import com.daghosoft.daghlink.util.Util;
import com.daghosoft.daghlink.util.UtilPagination;
import com.daghosoft.daghlink.validator.ValidatorLink;
import com.daghosoft.daghlink.validator.ValidatorMsg;

/**
 * @author dagheisha
 * 
 */

@Service
public class ServiceLink {

	private static final Logger logger = LoggerFactory.getLogger(ServiceLink.class);

	@Autowired ServiceMail2 serviceMail;
	@Autowired DaoLink daoLink;
	@Autowired @Qualifier("daoGroupLink") DaoGroupInterface daoGroup;
	@Autowired ValidatorLink validatorLink;
	@Autowired ValidatorMsg validatorMsg;
	@Autowired UtilPagination utilPagination;
	@Autowired Util util;
	@Autowired ServiceProperty serviceProperty;
	@Autowired HttpSession session;
	
	public String list(Model model,HttpSession session) {
		User user = (User) session.getAttribute("userOs");
		int groupId = Util.groupId(session);
		utilPagination.type("link");
		utilPagination.pagedList(daoLink.findLinkByGroup(user.getId(), groupId));
		navigator(model, user);
		return "linkjsp/list";
	}
	
	public void navigator(Model model, User user){
		model.addAttribute("linkGroupList", daoGroup.find(user));
	}

	public void showUpdateForm(Integer id, Model model, HttpSession session) {
		Link link = daoLink.get(id);
		showForm(link, model, session);
		logger.debug("Id : "+id+"");
	}

	public void showCreateForm(Model model, HttpSession session) {
		Link link = new Link();
		link.setDate(new Date());
		link.setLink("http://");
		link.setFather(Util.groupId(session));
		showForm(link, model, session);
	}

	public void showForm(Link link, Model model, HttpSession session) {
		model.addAttribute("linkGroupList", selectGroupList(session));
		model.addAttribute(link);
	}

	public String update(Link link, Model model, BindingResult errors,HttpSession session) {
		User user = (User) session.getAttribute("userOs");
		link.setLink(util.formatUrl(link.getLink()));
		validatorLink.validate(link, errors);
			
		String out = null;
		if (!errors.hasErrors()) {
			daoLink.update(link,user.getId());
			session.setAttribute("alertType","success");
			out = "redirect:../secure/link";
		} else {
			out = "linkjsp/form";
			session.setAttribute("alertType","error");
			showForm(link, model, session);
		}
		return out;
	}

	public String create(Link link, Model model, BindingResult errors,HttpSession session) {
		link.setLink(util.formatUrl(link.getLink()));
		validatorLink.validate(link, errors);
		User user = (User) session.getAttribute("userOs");
		link.setUser_id(user.getId());
		link.setUuid(UUID.randomUUID().toString());
		String out = null;
		if (!errors.hasErrors()) {
			daoLink.create(link);
			out = "redirect:../secure/link";
			session.setAttribute("alertType","success");
		} else {
			out = "linkjsp/form";
			session.setAttribute("alertType","error");
			showForm(link, model, session);
		}
		return out;
	}

	public void delete(int id) {
		User user = (User) session.getAttribute("userOs");
		Link link = daoLink.get(id);
		if (link.getType() != null) {
			daoLink.resetFather(id);
		}
		session.setAttribute("alertType","success");
		daoLink.delete(id,user.getId());
	}
	
	public String share(Model model,HttpSession session) {
		User user = (User) session.getAttribute("userOs");
		int id = (Integer) session.getAttribute("groupId");
		Group linkGroup = daoGroup.get(id);
		List<Link> list = daoLink.findLinkByGroup(user.getId(), id);
		String body = "\n\n"+linkGroup.getTitle()+"\n";
		for(Link link : list){
			body=body + link.getTitle() + " : " +link.getLink() +"\n"; 
		}
		body=body  + "\n\n\n"+serviceProperty.get("share.sign");
		Msg msg = new Msg();
		msg.setBody(body);
		msg.setSubj(serviceProperty.get("share.subj"));
		msg.setUuid(linkGroup.getUuid());
		msg.setFeedUrl("/feedLink");
		model.addAttribute("message", msg);
		return "linkjsp/share";
	}
	
	public String send(Msg msg,Model model,Errors errors,HttpSession session){
		validatorMsg.validate(msg, errors);
		String out="";
		if (!errors.hasErrors()) {
			serviceMail.shareByMail(msg);
			out = "redirect:../secure/link";
			session.setAttribute("alertType","success");
		} else {
			out = share(model,session);
			session.setAttribute("alertType","error");
		}
		return out;
	}
	
	
	public void deleteMultiple(HttpServletRequest request){
		if (request.getParameterValues("chk")!=null){
			String [] archk = request.getParameterValues("chk");
			for(int x=0;x<archk.length;x++){
				delete(NumberUtils.toInt(archk[x]));
			}
			session.setAttribute("alertType","success");
		}else{
			session.setAttribute("alertType","errorNoSelection");
		}
	}
	
	public void moveMultiple(HttpServletRequest request){
		if (request.getParameterValues("chk")!=null){
			User user = (User) session.getAttribute("userOs");
			int father =NumberUtils.toInt(request.getParameter("father"));
			String [] archk = request.getParameterValues("chk");
			for(int x=0;x<archk.length;x++){
				daoLink.updateFather(father,NumberUtils.toInt(archk[x]), user.getId());
			}
			session.setAttribute("alertType","success");
		}else{
			session.setAttribute("alertType","errorNoSelection");
		}
	}
	
	
	//####################################################################################
	
	
	private Map<Integer,String> selectGroupList(HttpSession session) {
		User user = (User) session.getAttribute("userOs");
		List<Group> linkGroupList = daoGroup.find(user);
		return Util.selectGroup(linkGroupList);
	}
	
	
}
