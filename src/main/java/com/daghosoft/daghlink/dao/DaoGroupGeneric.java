package com.daghosoft.daghlink.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.daghosoft.daghlink.bean.Group;
import com.daghosoft.daghlink.bean.User;

public abstract class DaoGroupGeneric implements DaoGroupInterface {

	private static final Logger logger = LoggerFactory.getLogger(DaoGroupLink.class);
	private JdbcTemplate jdbcTemplate;

	public DaoGroupGeneric() {
		super();
	}

	/* (non-Javadoc)
	 * @see com.daghosoft.daghlink.dao.DaoGroupInterface#setDataSource(javax.sql.DataSource)
	 */
	@Override
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/* (non-Javadoc)
	 * @see com.daghosoft.daghlink.dao.DaoGroupInterface#create(com.daghosoft.daghlink.bean.Group)
	 */
	@Override
	public int create(Group group) {
		String sql = "insert into "+getTableName()+" (user_id,title,description,type,date,uuid,share) values (?,?,?,?,?,?,?)";
		java.sql.Date date = new java.sql.Date(group.getDate().getTime());
		logger.trace(sql);
		return jdbcTemplate.update(sql, group.getUser_id() ,group.getTitle(),group.getDescription(),group.getType(),date,group.getUuid(),group.getShare());
	}

	/* (non-Javadoc)
	 * @see com.daghosoft.daghlink.dao.DaoGroupInterface#update(com.daghosoft.daghlink.bean.Group, int)
	 */
	@Override
	public void update(Group group, int user_id) {
		String sql = "update "+getTableName()+" set title=?,description=?,type=?,share=? where id=? and user_id=? ";
		jdbcTemplate.update(sql, group.getTitle(),group.getDescription(),group.getType(),group.getShare(),group.getId(),user_id);
	}

	/* (non-Javadoc)
	 * @see com.daghosoft.daghlink.dao.DaoGroupInterface#delete(int, int)
	 */
	@Override
	public void delete(int id, int user_id) {
		String sql = "delete from "+getTableName()+" where id=? and user_id=?";
		jdbcTemplate.update(sql, id,user_id);
	}

	/* (non-Javadoc)
	 * @see com.daghosoft.daghlink.dao.DaoGroupInterface#get(int)
	 */
	@Override
	public Group get(int id) {
		String sql="select * from "+getTableName()+" where id=?";
		Integer attr[] = {id};
		return jdbcTemplate.queryForObject(sql,attr,new GroupMapper());
	}

	/* (non-Javadoc)
	 * @see com.daghosoft.daghlink.dao.DaoGroupInterface#get(java.lang.String)
	 */
	@Override
	public Group get(String uuid) {
		String sql="select * from "+getTableName()+" where uuid=?";
		logger.trace(sql + uuid);
		String attr[] = {uuid};
		return jdbcTemplate.queryForObject(sql,attr,new GroupMapper());
	}
	
	/* (non-Javadoc)
	 * @see com.daghosoft.daghlink.dao.DaoGroupInterface#find(com.daghosoft.daghlink.bean.User)
	 */
	@Override
	public List<Group> find(User user) {
		String sql = "Select * from "+getTableName()+" where user_id="+user.getId()+" order by title desc";
		logger.trace(sql);
		List<Group> groupList =  jdbcTemplate.query(sql,new GroupMapper());
		return groupList;
	}

	/* (non-Javadoc)
	 * @see com.daghosoft.daghlink.dao.DaoGroupInterface#countByFather(int, java.lang.String)
	 */
	@Override
	public int countByFather(int id, String type) {
		String sql="SELECT COUNT(id) FROM "+type+" where father=?";
		return jdbcTemplate.queryForInt(sql, id);
	}
	class GroupMapper implements RowMapper<Group>{
		
		@Override
		public Group mapRow (ResultSet rs, int id) throws SQLException{
			Group group = new Group();
			
			group.setId(rs.getInt("id"));
			group.setUser_id(rs.getInt("user_id"));
			group.setTitle(rs.getString("title"));
			group.setDescription(rs.getString("description"));
			group.setType(rs.getString("type"));
			group.setDate(rs.getDate("date"));
			group.setUuid(rs.getString("uuid"));
			group.setChild(countByFather(group.getId(),group.getType()));
			group.setShare(rs.getString("share"));
			 
			return group;
		}
	} 

}