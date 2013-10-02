package com.daghosoft.daghlink.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.daghosoft.daghlink.bean.Group;
import com.daghosoft.daghlink.bean.User;

public interface DaoGroupInterface {

	@Autowired
	public abstract void setDataSource(DataSource dataSource);

	public abstract List<Group> find(User user);

	public abstract int create(Group group);

	public abstract void update(Group group, int user_id);

	public abstract void delete(int id, int user_id);

	public abstract Group get(int id);

	public abstract Group get(String uuid);

	public abstract int countByFather(int id, String type);
	
	public abstract String getTableName();

}