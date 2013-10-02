package com.daghosoft.daghlink.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.math.NumberUtils;
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

import com.daghosoft.daghlink.bean.FileBean;
import com.daghosoft.daghlink.bean.Msg;
import com.daghosoft.daghlink.service.ServiceFile;
import com.daghosoft.daghlink.service.ServiceProperty;
import com.daghosoft.daghlink.service.ServiceUser;
import com.daghosoft.daghlink.util.UtilPagination;

@Controller
@RequestMapping("/secure/file")
public class ControllerFile {

	private static final Logger logger = LoggerFactory.getLogger(ControllerFile.class);
	@Autowired ServiceFile serviceFile;
	@Autowired ServiceUser serviceUser;
	@Autowired HttpSession session;
	@Autowired HttpServletRequest request;
	@Autowired ServiceProperty serviceProperty;
	@Autowired UtilPagination page;
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		
	}
	@RequestMapping
	public String list(Model model){
		return serviceFile.list(model);
	}
	
	@RequestMapping(params={"next"},method=RequestMethod.GET)
	public String next(Model model) {
		page.next();
		return serviceFile.list(model);
	}
	@RequestMapping(params={"page"},method=RequestMethod.GET)
	public String page(Model model,int pageid) {
		page.page(pageid);
		return serviceFile.list(model);
	}
	@RequestMapping(params={"prev"},method=RequestMethod.GET)
	public String prev(Model model) {
		page.prev();
		return serviceFile.list(model);
	}
	
	@RequestMapping(params={"create"},method=RequestMethod.POST)
	public String create(FileBean file,Model model,BindingResult errors) {
		page.clear();
		//return serviceFile.create(file, model, errors);
		serviceFile.uploadFiles(errors);
		serviceUser.reloadUser(session);
		return "redirect:../secure/file?";
	}
	
	@RequestMapping(params={"update"},method=RequestMethod.GET)
	public String showForm(Integer id,Model model,HttpSession session) {
		serviceFile.showUpdateForm(id, model);
		return "filejsp/form";
	}
	@RequestMapping(params={"update"},method=RequestMethod.POST)
	public String update(FileBean fileBean,Model model,BindingResult errors) {
		page.clear();
		return serviceFile.update(fileBean, model, errors);
	}
	@RequestMapping(params={"delete"},method=RequestMethod.GET)
	public String delete(Integer id,Model model) {
		page.clear();
		serviceFile.delete(id);
		serviceUser.reloadUser(session);
		return serviceFile.list(model);
	}
	
	@RequestMapping(params={"multiple"},method=RequestMethod.POST)
	public String Multiple(Model model,HttpServletRequest request) {
		page.clear();
		if(request.getParameter("step").equals("delete")){
			serviceFile.deleteMultiple(request);
			serviceUser.reloadUser(session);
		}else{
			serviceFile.moveMultiple(request);
		}
		
		return serviceFile.list(model);
	}

	@RequestMapping(params={"enterGroup"},method=RequestMethod.GET)
	public String enterGroup(Integer id,Model model){
		page.clear();
		serviceFile.switchGroup(id, model, session);
		return list(model);
	}	
	
	
	//#################
	
		@RequestMapping(params={"share"},method=RequestMethod.GET)
		public String share(Model model) {
			return serviceFile.share(model);
		}
		@RequestMapping(params={"share"},method=RequestMethod.POST)
		public String share(Msg msg,Model model,BindingResult errors) {
			msg.setTo(msg.getTo()+";"+request.getParameter("copyTo"));
			return serviceFile.send(msg,model,errors,session);
		}
	
}
