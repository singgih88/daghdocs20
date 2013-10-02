package com.daghosoft.daghlink.service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.daghosoft.daghlink.bean.ImapAgentSlot;
import com.daghosoft.daghlink.bean.Queue;
import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.dao.DaoQueue;

@Component
public class ServiceQueue {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceQueue.class);
	@Autowired DaoQueue daoQueue;
	@Autowired HttpSession session;
	@Autowired ServiceProperty serviceProperty;
	
	public void subscriber(HttpServletRequest request){
		User user = (User) session.getAttribute("userOs");
		daoQueue.delete(user.getId());
		Boolean check = BooleanUtils.toBoolean(request.getParameter("imap-checkbox"));
		if (check && !user.getLocked()){
			daoQueue.create(user.getId());
		}
	}
	
	public String checkForm(){
		User user = (User) session.getAttribute("userOs");
		String out = "";
		if (daoQueue.findQueue(user.getId())){
			out="checked";
		}
		return out;
	}
	
	public void deleteFail(ImapAgentSlot slot){
		slot.getQueue().setFail(0);
		daoQueue.update(slot.getQueue());
	}
	
	public void checkFail(ImapAgentSlot slot){
		int failLimit = NumberUtils.toInt(serviceProperty.getNoContextNeeded("general.mail.fail.limit"));
		Queue job = slot.getQueue();
		logger.trace("Faillimit :"+ failLimit + " user Fail "+job.getFail() +" user_id " + job.getUser_id());
		if(job.getFail()>=failLimit){
			daoQueue.delete(job.getUser_id());
		}else{
			job.setFail(job.getFail()+1);
			daoQueue.update(job);
		}
	}
}
