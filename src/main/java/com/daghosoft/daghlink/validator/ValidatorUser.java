package com.daghosoft.daghlink.validator;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.dao.DaoUser;
import com.daghosoft.daghlink.service.ServiceProperty;

@Component
public class ValidatorUser implements Validator {

	private static final Logger logger = LoggerFactory.getLogger(ValidatorUser.class);
	
	@Autowired DaoUser daoUser;
	@Autowired ServiceProperty serviceProperty;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mail", "mail.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required");
		
		User user = (User) target;
		
		
		if (user.getUsername().length() > 50) {
			errors.rejectValue("username", "username.lenght");
		}
		
		if (errors.getFieldErrorCount("username") < 1) {
			List<User> userList = daoUser.find("username",user.getUsername());
			if (userList.size() > 0) {
				for (User userIn : userList) {
					if (userIn.getId() != user.getId()) {
						errors.rejectValue("username", "username.duplicated");
					}
				}
			}
		}

		if(user.getQuota()==null || user.getQuota()==0){
			errors.rejectValue("quota", "quota.min");
		}
		if(!EmailValidator.getInstance().isValid(user.getMail())){
			errors.rejectValue("mail", "mail.format");
		}
		if (errors.getFieldErrorCount("mail") < 1) {
			List<User> userList = daoUser.find("mail",user.getMail());
			if (userList.size() > 0) {
				for (User userIn : userList) {
					if (userIn.getId() != user.getId()) {
						errors.rejectValue("mail", "mail.duplicated");
					}
				}
			}
		}
	}
}
