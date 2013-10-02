package com.daghosoft.daghlink.validator;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.dao.DaoUser;
import com.daghosoft.daghlink.service.ServiceProperty;

@Component
public class ValidatorUserDelete implements Validator {

	private static final Logger logger = LoggerFactory.getLogger(ValidatorUserDelete.class);
	
	@Autowired DaoUser daoUser;
	@Autowired ServiceProperty serviceProperty;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = daoUser.get((Integer) target);
		logger.debug("Validate Delete : " + user.getUsername());
		if (user.getUsername().equals("admin")) {
			errors.rejectValue("username", "admin.protect");
		}
	}
}
