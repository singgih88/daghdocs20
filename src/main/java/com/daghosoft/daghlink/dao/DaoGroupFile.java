package com.daghosoft.daghlink.dao;

 
import org.springframework.stereotype.Repository;

@Repository("daoGroupFile")
public class DaoGroupFile extends DaoGroupGeneric {

	public String getTableName() {
		return "file_group";
	}
	
}
