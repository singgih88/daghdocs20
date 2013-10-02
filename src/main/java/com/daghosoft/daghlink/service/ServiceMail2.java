package com.daghosoft.daghlink.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.daghosoft.daghlink.bean.Msg;
import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.dao.DaoContact;
import com.daghosoft.daghlink.dao.DaoUser;
import com.daghosoft.daghlink.util.Util;
import com.daghosoft.daghlink.util.UtilBundle;
import com.daghosoft.daghlink.util.UtilMail;


@Service
public class ServiceMail2 {

	private static final Logger logger = LoggerFactory.getLogger(ServiceMail2.class);
	@Autowired DaoUser daoUser;
	@Autowired JavaMailSenderImpl mailSender;
	@Autowired VelocityEngine velocityEngine;
	@Autowired ServiceProperty serviceProperty;
	@Autowired ServiceContact serviceContact;
	@Autowired UtilBundle utilBundle;
	@Autowired Util util;
	@Autowired UtilMail utilMail;
	@Autowired HttpSession session;
	

	public void shareByMail(Msg msg){
		Locale locale = Locale.getDefault();
		Map<String, String> model = new HashMap<String, String>();
		model.putAll(utilBundle.getMessageBundle("com/daghosoft/daghlink/view/templateMessages",locale));		
		//-------- Verificare il subject se vuoto 
		model.put("subject",model.get("subjectNotifyShare"));
		model.put("body",msg.getBody().replace("\n", "<br>"));
		model.put("feed",util.getInstalledUrl()+ msg.getFeedUrl()+"?uuid="+msg.getUuid());
					
		String template = "modelShareLinkByMail.vm";
		
		User user = (User) session.getAttribute("userOs");
		InternetAddress replyTo = new InternetAddress();
		replyTo.setAddress(user.getMail());
			try {
				replyTo.setPersonal(util.formatName(user));
				sendMail(msg.getTo(), replyTo, model, template);
				serviceContact.addToMyContact(utilMail.multiAddress(msg.getTo()));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	
	
	public void passwordNotify(String mail){
		List<User> list = daoUser.find("mail", mail);
		if (list.size()>0){
			User user = list.get(0);
			String to = user.getMail();
			Map<String, String> model = prepareBody(user);
			String template = "modelRetrievePassword.vm";
			InternetAddress replyTo = new InternetAddress();
			replyTo.setAddress(serviceProperty.get("general.mail.from"));
			try {
				sendMail(to, replyTo, model, template);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			logger.info("No user find for mail : "+mail);
		}
	}
	
	private Map<String, String> prepareBody(User user){
		Locale locale = Locale.getDefault();
		Map<String, String> model = new HashMap<String, String>();
		model.putAll(utilBundle.getMessageBundle("com/daghosoft/daghlink/view/templateMessages",locale));		
		model.put("subject",model.get("subjectNotifyRetrievePassword"));
		model.put("yourname",util.formatName(user));
		model.put("loginUrl",util.getInstalledUrl());
		model.put("loginUser",user.getUsername());
		model.put("loginPassword",user.getPassword());
		return model;
	}
	
	
///////////////////////////	
	private void sendMail(String to,InternetAddress replyTo,Map<String, String> model,String templateName) throws MessagingException {
			//General send information
			mailSender.setHost(serviceProperty.get("general.mail.host"));
			mailSender.setPort(NumberUtils.toInt(serviceProperty.get("general.mail.port")));
			mailSender.setUsername(serviceProperty.get("general.mail.user"));
			mailSender.setPassword(serviceProperty.get("general.mail.password"));
			//End General send information
		
			MimeMessage mail = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail,true);
			
			//helper.setBcc(to);
			helper.setBcc(utilMail.multiAddress(to));
			helper.setReplyTo(replyTo);
			helper.setFrom(serviceProperty.get("general.mail.from"));
			
			helper.setSubject(model.get("subject"));
			helper = adminMail(helper);
			
			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "com/daghosoft/daghlink/view/"+templateName, model);
			mail.setContent(text, "text/html");
			mailSender.send(mail);
	}
	
	private MimeMessageHelper adminMail(MimeMessageHelper helper){
		if(serviceProperty.get("general.mail.ccn.admin").equals("true")){
			//send copy to admin user
			User admin = daoUser.get("username", "admin");
			try {
				helper.addBcc(admin.getMail());
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("Get admin user mail : "+admin.getMail());
		}
		return helper;
	}
}
