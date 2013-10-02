package com.daghosoft.daghlink.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import com.daghosoft.daghlink.bean.Download;
import com.daghosoft.daghlink.bean.FileBean;
import com.daghosoft.daghlink.bean.Group;
import com.daghosoft.daghlink.bean.Msg;
import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.dao.DaoDownload;
import com.daghosoft.daghlink.dao.DaoFile;
import com.daghosoft.daghlink.dao.DaoGroupInterface;
import com.daghosoft.daghlink.util.Util;
import com.daghosoft.daghlink.util.UtilFile;
import com.daghosoft.daghlink.util.UtilPagination;
import com.daghosoft.daghlink.util.UtilThumb;
import com.daghosoft.daghlink.validator.ValidatorFile;
import com.daghosoft.daghlink.validator.ValidatorFileGroup;
import com.daghosoft.daghlink.validator.ValidatorMsg;

@Service
public class ServiceFile {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceFile.class);
	@Autowired HttpSession session;
	@Autowired DaoFile daoFile;
	@Autowired DaoDownload daoDownload;
	@Autowired UtilPagination utilPagination;
	@Autowired UtilFile utilFile;
	@Autowired Util util;
	@Autowired ValidatorFile validatorFile;
	@Autowired ValidatorFileGroup validatorFileGroup;
	@Autowired ValidatorMsg validatorMsg;
	@Autowired ServiceMail2 serviceMail2;
	@Autowired ServiceProperty serviceProperty;
	//@Autowired ServiceUser serviceUser;
	@Autowired @Qualifier("daoGroupFile") DaoGroupInterface daoGroup;
	@Autowired HttpServletRequest request;
	
	public String list(Model model) {
		User user = (User) session.getAttribute("userOs");
		utilPagination.type("file");
		int groupId = Util.groupId(session);
		if (groupId!=0){
			model.addAttribute("group", daoGroup.get(groupId));
		}
		utilPagination.pagedList(daoFile.findFileByGroup(user.getId(), groupId));
		navigator(model, user);
		return "filejsp/list";
	}
	
	public List<FileBean> listFlat(String uuid){
		Group group =  daoGroup.get(uuid);
		return daoFile.findFileByGroup(group.getId());
	} 
	
	public void navigator(Model model, User user){
		model.addAttribute("fileGroupList", daoGroup.find(user));
	}
	
	public void showCreateForm(Model model,  String type) {
		FileBean fileBean = new FileBean();
		fileBean.setPath("/00/fake/fakeFile.jpg");
		fileBean.setDate(new Date());
		fileBean.setFather(Util.groupId(session));
		showForm(fileBean, model);
	}
	
	public void showUpdateForm(int id,Model model) {
		FileBean fileBean = daoFile.get(id);
		model.addAttribute("listDownload", daoDownload.get(id));
		model.addAttribute("count",daoDownload.count(id));
		showForm(fileBean, model);
	}
	
	public void showForm(FileBean fileBean, Model model) {
		model.addAttribute("fileGroupList", selectGroupList(session));
		model.addAttribute(fileBean);
	}
	
	private Map<Integer, String> selectGroupList(HttpSession session) {
		User user = (User) session.getAttribute("userOs");
		List<Group> fileGroupList = daoGroup.find(user);
		return selectGroup(fileGroupList);
	}
	
	public String update(FileBean fileBean, Model model, BindingResult errors) {
		User user = (User) session.getAttribute("userOs");
		String out = null;
		//validatorFileM.validate(fileBean, errors);
		if (!errors.hasErrors()) {
			fileBean = utilFile.description(fileBean);
			daoFile.update(fileBean,user.getId());
			//uploadFile(fileBean, user,errors);
			session.setAttribute("alertType","success");
			out = "redirect:../secure/file";
		} else {
			out = "filejsp/form";
			session.setAttribute("alertType","error");
			showForm(fileBean, model);
		}
		return out;
	}
	
	public void uploadFiles(BindingResult errors) {
		User user = (User) session.getAttribute("userOs");
		List<FileItem> list = uploadParser();
		for(FileItem item : list){
			validatorFile.validate(item, errors);
			if (!errors.hasErrors()) {
				String filename = FilenameUtils.getName(item.getName());
				
				FileBean fileBean = new FileBean();
				fileBean.setFilename(filename);
				fileBean.setSize(item.getSize());
				fileBean.setUuid(UUID.randomUUID().toString());
				fileBean.setFather(Util.groupId(session));
				fileBean = utilFile.genPath(fileBean);
				fileBean = utilFile.description(fileBean);
				
				String s = File.separator;
			    String basePath = serviceProperty.get("general.basepath");
	
			    UtilFile.createFolder(basePath+s+user.getId()+s+"file");
			   
			    File dest = new File(basePath+fileBean.getPath());
				
				daoFile.createFile(fileBean, user.getId());
				try {
					item.write(dest);
					logger.debug("generate thumb");
					UtilThumb.thumb(dest, 0, 500);
					UtilThumb.thumb(dest, 70, 0);
			    	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				session.setAttribute("alertType","errorEmpty");
			}
		}
	}
	
	public void deleteMultiple(HttpServletRequest request){
		if (request.getParameterValues("chk")!=null){
			String [] archk = request.getParameterValues("chk");
			for(int x=0;x<archk.length;x++){
				delete(NumberUtils.toInt(archk[x]));
			}
			session.setAttribute("alertType","success");
		}else{
			session.setAttribute("alertType","errorNoSelection");
		}
	}
	
	public void moveMultiple(HttpServletRequest request){
		if (request.getParameterValues("chk")!=null){
			User user = (User) session.getAttribute("userOs");
			int father =NumberUtils.toInt(request.getParameter("father"));
			String [] archk = request.getParameterValues("chk");
			for(int x=0;x<archk.length;x++){
				daoFile.updateFather(father,NumberUtils.toInt(archk[x]), user.getId());
			}
			session.setAttribute("alertType","success");
		}else{
			session.setAttribute("alertType","errorNoSelection");
		}
	}
	
	public void delete(int id) {
		User user = (User) session.getAttribute("userOs");
		FileBean fileBean = daoFile.get(id);
		String basePath = serviceProperty.get("general.basepath");
		File f = new File(basePath+fileBean.getPath());
		f.delete();
		UtilThumb.deleteThumb(f);
		daoFile.delete(id,user.getId());
		session.setAttribute("alertType","success");
	}
	
	public static Map<Integer,String> selectGroup(List<Group> list){
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, "Home");
		for(Group group : list){
			map.put(group.getId(), group.getTitle());
		}
		return map;
	}
	
	public void switchGroup(Integer id, Model model, HttpSession session) {
		User user = (User) session.getAttribute("userOs");
		if (id != 0 && id != null) {
			Group group = daoGroup.get(id);
			if(group.getUser_id()==user.getId()){
				session.setAttribute("groupId", id);
				session.setAttribute("groupUuid", group.getUuid());
			}
		} else {
			session.setAttribute("groupId", 0);
			session.setAttribute("groupTitle", "");
		}
	}
	
	
	public String share(Model model) {
		User user = (User) session.getAttribute("userOs");
		int id = (Integer) session.getAttribute("groupId");
		Group linkGroup = daoGroup.get(id);
		String body = "\n\n"+linkGroup.getTitle()+"\n";
		
		if(linkGroup.getShare()==null || linkGroup.getShare().equals("")){
			List<FileBean> list = daoFile.findFileByGroup(user.getId(), id);
			for(FileBean file : list){
				body=body + util.getInstalledUrl() + "/file" +file.getPath() +"\n"; 
			}
		}else{
			    body=body + util.getInstalledUrl() + "/gallery?uuid=" +linkGroup.getUuid() +"\n";
		}
		
		body=body  + "\n\n\n"+serviceProperty.get("share.sign");
		Msg msg = new Msg();
		msg.setBody(body);
		msg.setSubj(serviceProperty.get("share.subj"));
		msg.setUuid(linkGroup.getUuid());
		msg.setFeedUrl("/feedFile");
		model.addAttribute("message", msg);
		return "filejsp/share";
	}
	
	public String send(Msg msg,Model model,Errors errors,HttpSession session){
		validatorMsg.validate(msg, errors);
		String out="";
		if (!errors.hasErrors()) {
			serviceMail2.shareByMail(msg);
			out = "redirect:../secure/file";
			session.setAttribute("alertType","success");
		} else {
			out = share(model);
			session.setAttribute("alertType","error");
		}
		return out;
	}
	
	
	/*Service request parser for multi upload*/
	
	@SuppressWarnings("unchecked")
	public List<FileItem> uploadParser() {
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// Set overall request size constraint
		Long limit = NumberUtils.toLong(serviceProperty.get("general.upload.size.limit"));
		upload.setSizeMax(limit);
		List<FileItem> items = new ArrayList<FileItem>();
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.setAttribute("alertType","errorSize");
		}
		
		return items;
	}
}
