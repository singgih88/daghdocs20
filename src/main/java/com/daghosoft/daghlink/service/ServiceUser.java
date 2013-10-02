package com.daghosoft.daghlink.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import com.daghosoft.daghlink.bean.Group;
import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.dao.DaoFile;
import com.daghosoft.daghlink.dao.DaoProperty;
import com.daghosoft.daghlink.dao.DaoQueue;
import com.daghosoft.daghlink.dao.DaoUser;
import com.daghosoft.daghlink.dao.DaoUserAuthority;
import com.daghosoft.daghlink.util.UtilFile;
import com.daghosoft.daghlink.util.UtilPagination;
import com.daghosoft.daghlink.util.UtilThumb;
import com.daghosoft.daghlink.validator.ValidatorImap;
import com.daghosoft.daghlink.validator.ValidatorSettings;
import com.daghosoft.daghlink.validator.ValidatorUser;
import com.daghosoft.daghlink.validator.ValidatorUserDelete;

@Component
public class ServiceUser {

	private static final Logger logger = LoggerFactory.getLogger(ServiceUser.class);
	@Autowired DaoUser daoUser;
	@Autowired DaoUserAuthority daoUserAuthority;
	@Autowired DaoProperty daoProperty;
	@Autowired DaoFile daoFile;
	@Autowired DaoQueue daoQueue;
	@Autowired ServiceUserAutority serviceUserAutority;
	@Autowired ServiceProperty serviceProperty;
	@Autowired ServiceFile serviceFile;
	@Autowired ServiceQueue serviceQueue;
	@Autowired @Qualifier("grpfile") ServiceGroup serviceGroupFile;
	@Autowired UtilPagination utilPagination;
	@Autowired ValidatorUser validatorUser;
	@Autowired ValidatorUserDelete validatorUserDelete;
	@Autowired ValidatorSettings validatorSettings;
	@Autowired ValidatorImap validatorImap;
	@Autowired HttpSession session;
	@Autowired ServiceMail2 serviceMail;
	
	
	public String list(Model model){
		List<User> userList = daoUser.find();
		
		for(int x=0;x<userList.size();x++){
			User user = userList.get(x);
			user.setUsed(daoFile.GetTotalSizeByUser(user.getId()));
			userList.set(x, user);
		}
		
		model.addAttribute(userList);
		utilPagination.type("user");
		return "userjsp/list";
	}
	
	public String showFormSettings(Model model,HttpSession session){
		User user = (User) session.getAttribute("userOs");
		user.setUsed(daoFile.GetTotalSizeByUser(user.getId()));
		//User details
		model.addAttribute(user);
		//Share details
		model.addAttribute("sharesubj",serviceProperty.get("share.subj"));
		model.addAttribute("sharesign",serviceProperty.get("share.sign"));
		//Imap details
		model.addAttribute("imaphost",serviceProperty.get("imap.host"));
		model.addAttribute("imapuser",serviceProperty.get("imap.user"));
		model.addAttribute("imappass",serviceProperty.get("imap.password"));
		model.addAttribute("imapfolder",serviceProperty.get("imap.folder"));
		model.addAttribute("imapcheck",serviceQueue.checkForm());
		if(serviceProperty.get("imap.delete").equals("true")){
			model.addAttribute("imapdelete","checked");
		}else{
			model.addAttribute("imapdelete","");
		}
		return "userjsp/formsettings";
	}
	
	public String showCreateForm(Model model){
		User user = new User();
		String quota = serviceProperty.getNoContextNeeded("general.max.quota");
		user.setQuota(NumberUtils.toLong(quota));
		String [] auth = {"ROLE_USER"};
		user.setAuth(auth);
		return showForm(user, model);
	}
	
	public String showUpdateForm(Model model,int id){
		User user = daoUser.get(id);
		/*String quota = serviceProperty.getForUserNoContextNeeded("general.max.quota", user.getId());
		user.setQuota(NumberUtils.toLong(quota));*/
		return showForm(user, model);
	}
	
	private String showForm(User user,Model model){
		model.addAttribute("authList", authList());
		model.addAttribute(user);
		return "userjsp/form";
	}
	
	public void itemInPage(int num){
		User user = (User) session.getAttribute("userOs");
		daoProperty.personalSetting("general.page.size", ""+num, user);
		logger.debug("general.page.size : {}",num, user);
		serviceProperty.clearSession();
	}
	
	@Secured("ROLE_ADMIN")
	public String create(User user,Model model,Errors errors){
		validatorUser.validate(user, errors);
		String out = null;
		if (!errors.hasErrors()) {
			user = daoUser.create(user);
			setAuthority(user);
			/*///////////////////////////
			Group group = new Group();
			group.setTitle("Help");
			group.setUser_id(user.getId());
			serviceGroupFile.create(group, model, (BindingResult) errors);
			//////////////////////////
*/			serviceProperty.clearSession();
			serviceMail.passwordNotify(user.getMail());
			session.setAttribute("alertType","success");
			out = "redirect:../secure/user";
		} else {
			model.addAttribute("authList", authList());
			model.addAttribute(user);
			session.setAttribute("alertType","error");
			out = "userjsp/form";
		}
		return out;
	}
	
	@Secured("ROLE_ADMIN")
	public String notify(int id){
			User user = daoUser.get(id);
			serviceMail.passwordNotify(user.getMail());
			session.setAttribute("alertType","success");
			return "redirect:../secure/user";
	}
	
	@Secured("ROLE_ADMIN")
	public String update(User user, Errors errors,Model model){
		validatorUser.validate(user, errors);
		String out = null;
		if (!errors.hasErrors()) {
			daoUser.update(user);
			setAuthority(user);
			daoProperty.personalSetting("general.max.quota",String.valueOf(user.getQuota()), user);
			out = "redirect:../secure/user";
			session.setAttribute("alertType","success");
		} else {
			model.addAttribute("authList", authList());
			model.addAttribute(user);
			out = "userjsp/form";
			session.setAttribute("alertType","error");
		}
		return out;
	}
	
	public void updateSettings(User user, Errors errors,HttpSession session ){
		validatorSettings.validate(user, errors);				
		if(!errors.hasErrors()){
			daoUser.updateSettings(user);
			user = daoUser.get(user.getId());
			session.setAttribute("userOs", user);
			session.setAttribute("alertType","success");
		}else{
			session.setAttribute("alertType","error");
		}
		//return "userjsp/formsettings";
		serviceProperty.clearSession();
		session.setAttribute("alertType","success");
	}
	
	public String updateAvatar(){
			User user = (User) session.getAttribute("userOs");
			List<FileItem> list = serviceFile.uploadParser();
			for(FileItem item : list){
				String avatar = item.getName();
				if (avatar!=null && !avatar.equals("")){
					String basePath = serviceProperty.get("general.basepath");
					String separator = "/";
					String  folder = separator+user.getId()+separator+"resource";
					UtilFile.createFolder(basePath+folder);
					
					avatar = folder+separator+avatar;
					user.setAvatarPath(avatar);
					
			        File dest = new File(basePath+user.getAvatarPath());

					    try {
					    	//user.getAvatar().transferTo(dest);
					    	item.write(dest);
					    	daoUser.updateAvatarPath(user);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					session.setAttribute("alertType","success");
				}
			}	
		return "userjsp/formsettings";
	}
	
	public void deleteAvatar(HttpSession session){
		User userOs =(User) session.getAttribute("userOs");
		String basePath = serviceProperty.get("general.basepath");
		String separator = File.separator;
		File avatar = new File(basePath+separator+userOs.getAvatarPath());
		if (avatar.exists()){
			avatar.delete();
			UtilThumb.deleteThumb(avatar);
		}
		userOs.setAvatarPath("");
		daoUser.updateAvatarPath(userOs);
		userOs.setAvatarPath("/fake/resource/fakeAvatar.jpg");
		session.setAttribute("alertType","success");
		session.setAttribute("userOs", userOs);
	}
	
	public void updateShare(HttpServletRequest request){
		User user = (User) session.getAttribute("userOs");
		daoProperty.personalSetting("share.sign", request.getParameter("share-sign"), user);
		daoProperty.personalSetting("share.subj", request.getParameter("share-subj"), user);
		serviceProperty.clearSession();
		session.setAttribute("alertType","success");
	}
	
	@Secured("ROLE_ADMIN")
	public String delete(int id,Model model){
		User user = daoUser.get(id);
		if (!user.getUsername().equals("admin")) {
			daoUser.delete(id);
			session.setAttribute("alertType","success");
		}else {
			session.setAttribute("alertType","error");
			logger.info("Cannot delete user admin");
		}
		return "redirect:../secure/user";
	}
	
	public void getUserOs(HttpSession session,String userPrincipal){
			User userOs = daoUser.get("username", userPrincipal);
			userOs.setUsed(daoFile.GetTotalSizeByUser(userOs.getId()));
			session.setAttribute("userOs", userOs);
			//Long quota = NumberUtils.toLong(serviceProperty.get("general.max.quota")); 
			//userOs.setQuota(quota);
			userOs = lockUpload(userOs);
			session.setAttribute("userOs", userOs);
	}
	
	public void reloadUser(HttpSession session){
		User userOs = (User) session.getAttribute("userOs");
    	userOs.setUsed(daoFile.GetTotalSizeByUser(userOs.getId()));
    	userOs = lockUpload(userOs);
    	session.setAttribute("userOs", userOs);
	}
	
	private User lockUpload(User user){
		if((user.getUsed()/1024)>=user.getQuota()){
			user.setLocked(true);
			daoQueue.delete(user.getId());
		}else{
			user.setLocked(false);
		}
		return user;
	}
	
	public boolean lockUpload(int user_id){
		User user = daoUser.get(user_id);
		user.setUsed(daoFile.GetTotalSizeByUser(user_id));
		lockUpload(user);
		return user.getLocked();
	}
	
	//#############################################
	public void setAuthority(User user){
		daoUserAuthority.delete(user.getUsername());
		daoUserAuthority.create(serviceUserAutority.convertListToArr(user.getAuth(), user.getUsername()));
	}
	
	private Map<String, String> authList() {
		Map<String, String> authList = new HashMap<String, String>();
		authList.put("ROLE_ADMIN", "admin");
		authList.put("ROLE_USER", "user");
		authList.put("ROLE_POWERUSER", "poweruser");
		return authList;
	}
}
