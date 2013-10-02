package com.daghosoft.daghlink.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.daghosoft.daghlink.bean.Link;
import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.dao.DaoUser;

@Component
public class ValidatorLinkGroup implements Validator {

	@Autowired
	DaoUser daoUser;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "title", "title.required");
		Link link = (Link) target;
		if (link.getTitle().length() > 150) {
			errors.rejectValue("title", "title.lenght");
		}
	}
}
