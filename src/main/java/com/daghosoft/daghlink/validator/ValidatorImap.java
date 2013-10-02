package com.daghosoft.daghlink.validator;

import javax.mail.MessagingException;
import javax.mail.Store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.daghosoft.daghlink.bean.ImapAgentSlot;
import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.service.ServiceImap;
import com.daghosoft.daghlink.service.ServiceProperty;

@Component
public class ValidatorImap  {
	
	@Autowired ServiceImap serviceImap;
	@Autowired ServiceProperty serviceProperty;
	
	public Boolean validate() {
		ImapAgentSlot slot = new ImapAgentSlot();
		slot.setHost(serviceProperty.get("imap.host"));
		slot.setUser(serviceProperty.get("imap.user"));
		slot.setPassword(serviceProperty.get("imap.password"));
		slot.setProtocol("imap");
		Boolean out=false;
		try {
			serviceImap.getConnection(slot);
			Store store = serviceImap.getConnection(slot);
			out=store.isConnected();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}
}
