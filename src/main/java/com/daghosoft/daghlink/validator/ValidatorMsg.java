package com.daghosoft.daghlink.validator;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.daghosoft.daghlink.bean.Msg;
import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.service.ServiceProperty;

@Component
public class ValidatorMsg implements Validator {

	private static final Logger logger = LoggerFactory.getLogger(ValidatorUser.class);
	
	@Autowired ServiceProperty serviceProperty;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "to", "to.required");
		Msg msg = (Msg) target;
		String to = msg.getTo().replace(";", "");
		to = to + msg.getTo().replace(" ", "");
		if(to.equals("")){
			errors.rejectValue("mail", "mail.format");
		}
		
	}
}
