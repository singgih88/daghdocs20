package com.daghosoft.daghlink.util;

import java.util.Map;


import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.dao.DaoUser;
import com.daghosoft.daghlink.service.ServiceContact;
import com.daghosoft.daghlink.service.ServiceProperty;


@Component
public class UtilMail {
	
	private static final Logger logger = LoggerFactory.getLogger(UtilMail.class);
	
	@Autowired JavaMailSenderImpl mailSender;
	@Autowired ServiceProperty serviceProperty;
	@Autowired ServiceContact serviceContact;
	@Autowired VelocityEngine velocityEngine;
	@Autowired DaoUser daoUser;
		
	public String[] multiAddress(String to) throws MessagingException {
		to = to.replace(";;", ";");
		to = to.replace(",", ";");
		to = to.replace(" ", "");
		to = to.replace("null", "");
		logger.debug("to : "+to);
		String[] MultipleMail = {to};
			if (to.indexOf(";") > 0) {
				MultipleMail = to.split(";");
			} 
		return MultipleMail;
	}
}
