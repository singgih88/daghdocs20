package com.daghosoft.daghlink.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.UUID;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.daghosoft.daghlink.bean.FileBean;
import com.daghosoft.daghlink.bean.ImapAgentSlot;
import com.daghosoft.daghlink.dao.DaoFile;
import com.daghosoft.daghlink.service.ServiceImap;
import com.daghosoft.daghlink.service.ServiceQueue;

@Component
public class UtilImapAgent {
	
	private static final Logger logger = LoggerFactory.getLogger(UtilImapAgent.class);
	//@Autowired ServiceProperty property;
	@Autowired UtilFile utilFile;
	@Autowired DaoFile daoFile;
	@Autowired ServiceQueue serviceQueue;
	@Autowired ServiceImap serviceImap;
	
	@Async
	public void read(ImapAgentSlot slot){
			
			try {
				Store store = serviceImap.getConnection(slot);
				logger.trace("Connections established : {} ",store.isConnected());
				
				Folder folder = null;
				folder = store.getDefaultFolder();
				folder = folder.getFolder(slot.getRemoteFolder());
				folder.open(Folder.READ_WRITE);
				logger.trace("Folder Exists : {} ", folder.exists());
				logger.trace("Total Messages : {} ",folder.getMessageCount());
				logger.trace("Unread Messages : {} ", folder.getUnreadMessageCount());
				
				//getFolderList(store);
				
				if(folder.getUnreadMessageCount()>0){
					for(int x=0;x<folder.getMessageCount();x++){
						getMessage(folder, x+1, store,slot);
					}
				}
				serviceQueue.deleteFail(slot);
			} catch (NoSuchProviderException e) {
				// TODO Auto-generated catch block
				serviceQueue.checkFail(slot);
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				serviceQueue.checkFail(slot);
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				serviceQueue.checkFail(slot);
				e.printStackTrace();
			} catch (MailAuthenticationException e) {
				// TODO: handle exception
				serviceQueue.checkFail(slot);
				e.printStackTrace();
			} 
	}
	
	private void getMessage(Folder folder,int id,Store store,ImapAgentSlot slot) throws MessagingException, IOException{
		 String s = File.separator;
		 String localFolder =slot.getBasePath()+s+slot.getQueue().getUser_id()+s+"file"+s;
		 UtilFile.createFolder(localFolder);
		
		Message message = folder.getMessage(id);
		
		logger.trace("Flags : "+ message.isSet(Flags.Flag.SEEN));
		logger.trace("Subject : {}", message.getSubject());
		logger.trace("Date : {} ", message.getSentDate());
		logger.trace("Sender : {} ", ((InternetAddress) message.getFrom()[0]).getAddress());
		
		Object content = message.getContent();
		
	if (content instanceof Multipart && !message.isSet(Flags.Flag.SEEN) ) {
		    Multipart mp = (Multipart) content;
		    String body = "";
		    for (int i = 0; i < mp.getCount(); i++) {
		        BodyPart bp = mp.getBodyPart(i);
		        String type = bp.getContentType().toLowerCase();
		        
		        	// Retrieve the file name
					String fileName = bp.getFileName();
					String realName = fileName;
					
					String ext = ".txt";
					if(type.toLowerCase().contains("html")){
						ext=".html";
					}
					
					if (fileName==null){
						fileName=(id+""+UUID.randomUUID().toString()+ext);
						realName=fileName;
						body="flag";
					}
					
					FileBean fileBean = new FileBean();
					fileBean.setFilename(realName);
					fileBean.setUuid(UUID.randomUUID().toString());
					fileBean = utilFile.genPath(fileBean,slot.getQueue().getUser_id());
					
					save(bp.getInputStream(),slot.getBasePath()+s+fileBean.getPath());
					
					File f = new File(slot.getBasePath()+s+fileBean.getPath());
					if (body.equals("flag")){
						body = FileUtils.readFileToString(f);
						f.delete();
					}else{
						Long size = f.length();
						fileBean.setSize(size);
						fileBean.setDescription(body);
						logger.trace("fileName :"+ fileName + " size : " + size);
						daoFile.createFile(fileBean, slot.getQueue().getUser_id());
					}
					
		    }
		    if(slot.getDelete().equals("true")){
		    	message.setFlag(Flags.Flag.DELETED, true);
		    }else{
		    	message.setFlag(Flags.Flag.SEEN, true);
		    }
		}
	 }
	 
	 private void save(InputStream in,String filename){
			try {
			File f = new File(filename);
			InputStream fstream = in;
			DataInputStream din = new DataInputStream(fstream);
			OutputStream out = new FileOutputStream(f);
			
					byte[] buffer = new byte[256];
					int read = 0;
					while ((read = din.read(buffer)) > 0) {
						out.write(buffer, 0, read);	
				   }
				   out.close();
				   in.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
