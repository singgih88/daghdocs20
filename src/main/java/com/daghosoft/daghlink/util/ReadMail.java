package com.daghosoft.daghlink.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.FlagTerm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.daghosoft.daghlink.service.ServiceProperty;
import com.sun.mail.pop3.POP3SSLStore;

@Component
public class ReadMail {

	   private static final Logger logger = LoggerFactory.getLogger(ReadMail.class);
	   @Autowired ServiceProperty property;
	   
	   
	/*@Scheduled(fixedDelay = 60000 )*/
	//@Async
	public void get(){

		
		logger.info("enter mail");
		
		try {
			   Session session = null;
			   Store store = null;
			   Folder folder = null;
			   //Message message = null;
			
			   Properties props = System.getProperties();
			   props.setProperty("mail.store.protocol", "imaps");
			   session = Session.getDefaultInstance(props, null);
			   store = session.getStore("imaps");
			   store.connect("pop3.daghosoft.com", "devel@daghosoft.com", "celluisa");

			   logger.info(" conn : " +  store.isConnected());
	       
	        
			folder = store.getDefaultFolder();
			folder = folder.getFolder("inbox");
			folder.open(Folder.READ_WRITE);
			logger.info("folder exists : "+ folder.exists());
			logger.info("msgTot : {} ",folder.getMessageCount());
			logger.info("unread :"+ folder.getUnreadMessageCount());
			
			/*FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
			Message messages[] = folder.search(ft);*/			
			
			getFolderList(store);
			
			if(folder.getMessageCount()>0){
				getMessage(folder, 1, store);
			}
			
			
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		//return out;
	}
	
	 
	 private void getMessage(Folder folder,int id,Store store) throws MessagingException, IOException{
		Message message = folder.getMessage(id);
		logger.info("sub :"+ message.getSubject());
		logger.info("date :"+ message.getSentDate());
		logger.info("from :"+ ((InternetAddress) message.getFrom()[0]).getAddress());
		
		Object content = message.getContent();
		
		if (content instanceof Multipart) {
		    Multipart mp = (Multipart) content;
		    for (int i = 0; i < mp.getCount(); i++) {
		        BodyPart bp = mp.getBodyPart(i);
		        String type = bp.getContentType().toLowerCase();
		        logger.info("i"+i+ " ct : "+type);
		        
		        	// Retrieve the file name
					String fileName = bp.getFileName();
					if (fileName==null){
						fileName=(id+""+UUID.randomUUID().toString());
					}
					save(bp.getInputStream(),fileName);
					logger.info("fileName :"+ fileName);
		        
		    }
		}
		
		logger.info("DELETE");
		message.setFlag(Flags.Flag.DELETED, true);
		
	 }
	 
	 private void getFolderList( Store store) throws MessagingException{
		 Folder[] f = store.getDefaultFolder().list();
			for(Folder fd:f){
				logger.info(">> "+fd.getName());
			}
	 }
	
	private void save(InputStream in,String filename){
		try {
		String separator = File.separator;
		File f = new File(property.getNoContextNeeded("general.basepath")+separator+filename);
		logger.info("fileName : "+f.getAbsolutePath());
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
