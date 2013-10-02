package com.daghosoft.daghlink.service;

import java.security.Principal;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.daghosoft.daghlink.dao.DaoUser;


public class Interceptor extends HandlerInterceptorAdapter{
	private static final Logger logger = LoggerFactory.getLogger(Interceptor.class);
	
	@Autowired DaoUser daoUser;
	@Autowired ServiceUser serviceUser;
	//@Autowired ServletContext cnt;
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		Principal principal = request.getUserPrincipal();
		/*if(session.getAttribute("userOs")==null){
			Principal principal = request.getUserPrincipal();
			session.setAttribute("userOs", daoUser.get("username", principal.getName()));
		}*/
		//cnt.setAttribute("sample", "sample");
		if(session.getAttribute("userOs")==null){
			serviceUser.getUserOs(session, principal.getName());
		}
		return true;
	}


}
