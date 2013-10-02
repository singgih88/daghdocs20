package com.daghosoft.daghlink.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.dao.DaoUser;

@Component
public class ValidatorFileGroup implements Validator {

	@Autowired
	DaoUser daoUser;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
	}
}
