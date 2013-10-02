package com.daghosoft.daghlink.service;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.daghosoft.daghlink.bean.Property;
import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.dao.DaoProperty;
import com.daghosoft.daghlink.util.UtilPagination;

@Service
public class ServiceProperty {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceProperty.class);
	@Autowired ServletContext application;
	@Autowired HttpSession session;
	@Autowired UtilPagination utilPagination;
	@Autowired DaoProperty daoProperty;
	
	@Secured("ROLE_ADMIN")
	public String list(Model model){
	 model.addAttribute("list",daoProperty.find(""));
	 utilPagination.type("property");
	 return "propertyjsp/list";
	}
	
	@Secured("ROLE_ADMIN")
	public void create(HttpServletRequest request){
		Property property = new Property();
		property.setKey(request.getParameter("key"));
		property.setValue(request.getParameter("value"));
		daoProperty.create(property);
		session.setAttribute("alertType","success");
		clearApp();
	}
	
	@Secured("ROLE_ADMIN")
	public void delete(int id){
		daoProperty.delete(id);
		session.setAttribute("alertType","success");
		clearApp();
	}
	
	@Secured("ROLE_ADMIN")
	public void saveList(HttpServletRequest request){
		List<Property> list = daoProperty.find("");
		for (Property pro : list){
			daoProperty.update(request.getParameter("value"+pro.getId()),request.getParameter("id"+pro.getId()));
		}
		session.setAttribute("alertType","success");
		clearApp();
	}
	
	@SuppressWarnings("unchecked")
	public String get(String key) {
		HashMap<String,String> amap = new HashMap<String, String>();
		if (application.getAttribute("appProperty")==null){
			List<Property> list =  daoProperty.find("");
			for(Property property : list){
				amap.put(property.getKey(), property.getValue());
			}
			application.setAttribute("appProperty", amap);
		}else{
			amap = (HashMap<String, String>) application.getAttribute("appProperty");
		}
		
		User user = (User) session.getAttribute("userOs");
		
		HashMap<String,String> smap = new HashMap<String, String>();
		if (user!=null){
			if (session.getAttribute("sessionProperty")==null){
				List<Property> list =  daoProperty.find(""+user.getId());
				for(Property property : list){
					smap.put(property.getKey(), property.getValue());
					//logger.debug("------"+property.getKey()+"---"+property.getValue());
					//logger.debug("key = "+ key);
				}
				session.setAttribute("sessionProperty", smap);
			}else{
				smap = (HashMap<String, String>) session.getAttribute("sessionProperty");
			}
		}
		
		String value = smap.get(key);
		if (value==null || value.equals("")){
			value=amap.get(key);
		}
		if (value==null || value.equals("")){
			value="";
			logger.debug("###################### !!! No property found -> key : "+key +" - user : "+user.getUsername()+" !!!");
		}
		
		return value;
	}
	
	public String getNoContextNeeded(String key) {
		HashMap<String,String> amap = new HashMap<String, String>();
			List<Property> list =  daoProperty.find("");
			for(Property property : list){
				amap.put(property.getKey(), property.getValue());
			}
			application.setAttribute("appProperty", amap);
		String value=amap.get(key);
		if (value==null || value.equals("")){
			value="";
			logger.info("###################### !!! No property found -> key : "+key +" !!!");
		}
		return value;
	}
	
	public String getForUserNoContextNeeded(String key,int user_id) {
		List<Property> list = daoProperty.find(key,user_id);
		String value="";
		if (list.size()>0){
			value=list.get(0).getValue();
		}else{
			logger.info("###################### !!! No property found for -> user_id : "+user_id+" key : "+key +" !!!");
		}
		return value;
	}
	
	public void clearApp(){
		application.setAttribute("appProperty", null);
		//logger.info("Clean appProperty Done "+application.getAttributeNames());
	}
	
	public void clearSession(){
		session.setAttribute("sessionProperty", null);
		session.setAttribute("contentList", null);
		//logger.info("Clean sessionProperty Done");
	}
}
