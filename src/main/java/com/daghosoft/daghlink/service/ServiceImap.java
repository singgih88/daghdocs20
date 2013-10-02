package com.daghosoft.daghlink.service;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daghosoft.daghlink.bean.ImapAgentSlot;
import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.dao.DaoProperty;
import com.daghosoft.daghlink.dao.DaoQueue;
import com.daghosoft.daghlink.validator.ValidatorImap;

@Service
public class ServiceImap {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceImap.class);
	@Autowired ServiceProperty serviceProperty;
	@Autowired ServiceQueue serviceQueue;
	@Autowired ValidatorImap validatorImap;
	@Autowired DaoQueue daoQueue;
	@Autowired HttpSession session;
	@Autowired DaoProperty daoProperty;
	
	
	public void updateImap(HttpServletRequest request){
		User user = (User) session.getAttribute("userOs");
		daoProperty.personalSetting("imap.host", request.getParameter("imap-host"), user);
		daoProperty.personalSetting("imap.user", request.getParameter("imap-user"), user);
		daoProperty.personalSetting("imap.password", request.getParameter("imap-password"), user);
		daoProperty.personalSetting("imap.folder", request.getParameter("imap-folder"), user);
		if (request.getParameter("imap-delete")!=null){
			daoProperty.personalSetting("imap.delete", request.getParameter("imap-delete"), user);
		}else{
			daoProperty.personalSetting("imap.delete", "", user);
		}
		serviceProperty.clearSession();

		if(validatorImap.validate()){
			serviceQueue.subscriber(request);
			session.setAttribute("alertType","success");
		}else{
			daoQueue.delete(user.getId());
			session.setAttribute("alertType","connection.fail");
		}
	}

	 public Store getConnection(ImapAgentSlot slot) throws MessagingException{
		 	Properties props = System.getProperties();
			props.setProperty("mail.store.protocol", slot.getProtocol());
			Session session = Session.getDefaultInstance(props, null);
			Store store = session.getStore("imaps");
			store.connect(slot.getHost(), slot.getUser(), slot.getPassword());
			return store;
	 }
	 
	 private String getFolderList( Store store) throws MessagingException{
		 Folder[] f = store.getDefaultFolder().list();
		 String folders = "";
			for(Folder fd:f){
				//logger.trace(">> "+fd.getName());
				folders = folders + " ; " +fd.getName();
			}
		return folders;
	 }
}
