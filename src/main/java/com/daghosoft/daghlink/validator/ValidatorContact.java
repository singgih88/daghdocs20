package com.daghosoft.daghlink.validator;


import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.daghosoft.daghlink.bean.Contact;
import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.dao.DaoUser;
import com.daghosoft.daghlink.service.ServiceProperty;

@Component
public class ValidatorContact implements Validator {

	private static final Logger logger = LoggerFactory.getLogger(ValidatorContact.class);
	
	@Autowired DaoUser daoUser;
	@Autowired ServiceProperty serviceProperty;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
			
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mail", "mail.required");
		
		Contact contact = (Contact) target;
		
		if(!EmailValidator.getInstance().isValid(contact.getMail())){
			errors.rejectValue("mail", "mail.format");
		}
		
		
	}
}
