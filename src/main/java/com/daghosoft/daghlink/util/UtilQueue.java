package com.daghosoft.daghlink.util;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.daghosoft.daghlink.bean.ImapAgentSlot;
import com.daghosoft.daghlink.bean.Queue;
import com.daghosoft.daghlink.dao.DaoQueue;
import com.daghosoft.daghlink.service.ServiceProperty;
import com.daghosoft.daghlink.service.ServiceUser;

@Component
public class UtilQueue {

	private static final Logger logger = LoggerFactory.getLogger(UtilQueue.class);
	
	@Autowired ServiceProperty serviceProperty;
	@Autowired DaoQueue queue;
	@Autowired UtilImapAgent mailAgent;
	@Autowired ServiceUser serviceUser;
	
	@Scheduled(fixedDelay = 150000)
	//30000 30 sec
	//60000	1 min
	//300000 5 min
	//600000 10 min
	public void queueLookup(){
		logger.info("Imap Queue Start");
		
		String basePath=serviceProperty.getNoContextNeeded("general.basepath");
		List<Queue> listQueue =  queue.allQueue();
		
		for (Queue job : listQueue) {
			if (!serviceUser.lockUpload(job.getUser_id())){
				Date now =	new Date();
				logger.debug(now + "job : id = "+job.getId()+" user_id : "+job.getUser_id());
				ImapAgentSlot slot = new ImapAgentSlot();
				slot.setHost(serviceProperty.getForUserNoContextNeeded("imap.host", job.getUser_id()));
				slot.setUser(serviceProperty.getForUserNoContextNeeded("imap.user", job.getUser_id()));
				slot.setPassword(serviceProperty.getForUserNoContextNeeded("imap.password", job.getUser_id()));
				//slot.setUser_id(job.getUser_id());
				slot.setQueue(job);
				slot.setProtocol("imap");
				slot.setRemoteFolder(serviceProperty.getForUserNoContextNeeded("imap.folder", job.getUser_id()));
				slot.setDelete(serviceProperty.getForUserNoContextNeeded("imap.delete", job.getUser_id()));
				slot.setBasePath(basePath);
			    mailAgent.read(slot);
			}
		}
		
		logger.info("Imap Queue Stop");
	}
	
}
