package com.daghosoft.daghlink.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpSession;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daghosoft.daghlink.bean.Msg;
import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.dao.DaoUser;
import com.daghosoft.daghlink.util.Util;
import com.daghosoft.daghlink.util.UtilBundle;
import com.daghosoft.daghlink.util.UtilMail;


@Service
public class OFFServiceMail {

	private static final Logger logger = LoggerFactory.getLogger(OFFServiceMail.class);
	@Autowired DaoUser daoUser;
	@Autowired UtilMail utilMail;
	@Autowired Util util;
	@Autowired UtilBundle utilBundle;
	@Autowired HttpSession session;
	
	
	/*public void shareByMail(Msg msg){
			Locale locale = Locale.getDefault();
			
			Map<String, String> model = new HashMap<String, String>();
			model.putAll(utilBundle.getMessageBundle("com/daghosoft/daghlink/view/templateMessages",locale));		
			model.put("subject",model.get("subjectNotifyShare"));
			String body = msg.getBody().replace("\n", "<br>");
			model.put("body",body);
			model.put("feed",  util.getInstalledUrl()+ msg.getFeedUrl()+"?uuid="+msg.getUuid());
						
			String template = "modelShareLinkByMail.vm";
			
			User user = (User) session.getAttribute("userOs");
			InternetAddress to = new InternetAddress();
			to.setAddress(user.getMail());
			try {
				to.setPersonal(util.formatName(user));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			utilMail.notify(to, msg.getTo(), model, template);
	}*/

	
	
	/*public void passwordNotify(String mail){
		List<User> list = daoUser.find("mail", mail);
		if (list.size()>0){
			User user = list.get(0);
			Locale locale = Locale.getDefault();
			locale = Locale.ENGLISH;
			locale = Locale.ITALIAN;
			Locale.setDefault(locale);
			logger.debug("Locale : "+Locale.getDefault());
			String to = user.getMail();
			Map<String, String> model = new HashMap<String, String>();
			model.putAll(utilBundle.getMessageBundle("com/daghosoft/daghlink/view/templateMessages",locale));		
			model.put("subject",model.get("subjectNotifyRetrievePassword"));
			model.put("yourname",util.formatName(user));
			model.put("loginUrl",util.getInstalledUrl());
			model.put("loginUser",user.getUsername());
			model.put("loginPassword",user.getPassword());
						
			String template = "modelRetrievePassword.vm";
			utilMail.notify(null, to, model, template);
		}else{
			logger.info("No user find for mail : "+mail);
		}
	}*/
}
