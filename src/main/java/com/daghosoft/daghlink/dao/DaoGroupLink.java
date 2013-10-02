package com.daghosoft.daghlink.dao;

 
import org.springframework.stereotype.Repository;

@Repository("daoGroupLink")
public class DaoGroupLink extends DaoGroupGeneric {

	public String getTableName() {
		return "link_group";
	}
	
}
