package com.daghosoft.daghlink.validator;


import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.dao.DaoUser;
import com.daghosoft.daghlink.service.ServiceProperty;

@Component
public class ValidatorFile implements Validator {

	@Autowired DaoUser daoUser;
	@Autowired ServiceProperty serviceProperty;
	@Autowired HttpSession session;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//ValidationUtils.rejectIfEmpty(errors, "filename", "filename.required");
		FileItem file = (FileItem) target;
		String filename = file.getName();
		if (filename.equals("") ||filename==null ){
			errors.rejectValue("filename", "filename.required");
		}
		User user =(User) session.getAttribute("userOs");
		if(user.getLocked()){
			errors.rejectValue("filename", "response.quota.exceed");
		}
	}
}
