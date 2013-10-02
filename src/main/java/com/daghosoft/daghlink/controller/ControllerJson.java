package com.daghosoft.daghlink.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.daghosoft.daghlink.bean.Contact;
import com.daghosoft.daghlink.bean.FileBean;
import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.dao.DaoContact;
import com.daghosoft.daghlink.dao.DaoFile;
import com.daghosoft.daghlink.dao.DaoUser;
import com.daghosoft.daghlink.service.ServiceFile;
import com.daghosoft.daghlink.service.ServiceImap;

@Controller
public class ControllerJson {
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerJson.class);
	
	@Autowired DaoContact daoContact;
	@Autowired DaoUser daoUser;
	@Autowired DaoFile daoFile;
	@Autowired ServiceFile serviceFile;
	@Autowired ServiceImap serviceImap;
	
	
	@RequestMapping(value = "/secure/jcontact",method=RequestMethod.GET)
	public @ResponseBody List<Contact> showJson(HttpSession session){
		User user = (User) session.getAttribute("userOs");
		return daoContact.find(user.getId());
	}
	
	@RequestMapping(value = "/jgallery",method=RequestMethod.GET)
	public @ResponseBody List<FileBean> showFile(String uuid){
		return serviceFile.listFlat(uuid);
	}
	
	//!!! metodo commmentato da aplicare alla service setting in post di salvataggio parametri
	/*@RequestMapping(value = "/secure/jimap",method=RequestMethod.GET)
	public @ResponseBody HashedMap validateImap(){
		HashedMap map = new HashedMap();
		map.put("test", "devel");
		//return map;
		return serviceImap.validate();
	}*/
	
	/*@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/secure/jquota",method=RequestMethod.GET)
	public @ResponseBody Long showQuota(int id){
		logger.debug("in");
		return daoFile.GetTotalSizeByUser(id);
	}*/
}
