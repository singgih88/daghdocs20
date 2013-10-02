package com.daghosoft.daghlink.validator;


import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.daghosoft.daghlink.bean.Link;
import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.dao.DaoUser;

@Component
public class ValidatorLink implements Validator {

	@Autowired
	DaoUser daoUser;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "link", "link.required");
		Link link = (Link) target;
		if (link.getTitle().length() > 150) {
			errors.rejectValue("title", "title.lenght");
		}
		if (link.getType() == null || link.getType().equals("")) {
			UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);
			if (!urlValidator.isValid(link.getLink())) {
				errors.rejectValue("link", "link.format");
			}
		}

	}
}
