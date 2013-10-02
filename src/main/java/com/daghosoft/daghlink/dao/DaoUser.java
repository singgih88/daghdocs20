package com.daghosoft.daghlink.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.service.ServiceUserAutority;
import com.daghosoft.daghlink.util.Util;

@Repository
public class DaoUser {
	
	private static final Logger logger = LoggerFactory.getLogger(DaoUser.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired	DaoUserAuthority daoUserAuthority;
	@Autowired	ServiceUserAutority serviceUserAutority;
	@Autowired	Util utility;
	
	@Autowired
	public void setDataSource (DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	public List<User> find(){
		String sql = "Select * from user order by id desc";
		List<User> userList =  jdbcTemplate.query(sql, new UserMapper());
		return userList;
	}
	public List<User> find(String field,String value){
		String sql = "Select * from user where "+field+"=?";
		String[] attr = {value};
		logger.trace(sql);
		return  jdbcTemplate.query(sql,attr, new UserMapper());
	}
	/*public int create(User user){
		String sql = "insert into user (name,surname,mail,username,password,enabled) values (?,?,?,?,?,?)";
		jdbcTemplate.update(sql, user.getName(),user.getSurname(),user.getMail(),user.getUsername(),user.getPassword(),user.getEnabled());
		return 0;
	}*/
	
	public User create(final User user) {

		final String sql = "insert into user (name,surname,mail,username,password,enabled,quota) values (?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, user.getName());
				ps.setString(2, user.getSurname());
				ps.setString(3, user.getMail());
				ps.setString(4, user.getUsername());
				ps.setString(5, user.getPassword());
				ps.setBoolean(6, user.getEnabled());
				ps.setLong(7, user.getQuota());
				return ps;
			}
		}, keyHolder);
		int id = keyHolder.getKey().intValue();
		user.setId(id);
		return user;
	}
	
	
	public void update(User user){
		String sql = "update user set name=?,surname=?,mail=?,username=?,password=?,enabled=?,quota=? where id=?";
		jdbcTemplate.update(sql, user.getName(),user.getSurname(),user.getMail(),user.getUsername(),user.getPassword(),user.getEnabled(),user.getQuota(),user.getId());
	}
	public void updateSettings(User user){
		String sql = "update user set name=?,surname=?,password=? where id=?";
		jdbcTemplate.update(sql, user.getName(),user.getSurname(),user.getPassword(),user.getId());
	}
	public void updateAvatarPath(User user){
		String sql = "update user set avatarPath=? where id=?";
		jdbcTemplate.update(sql, user.getAvatarPath(),user.getId());
	}
	public void delete(int id){
		String sql = "delete from user where id=?";
		jdbcTemplate.update(sql, id);
	}
	public User get(int id){
		String sql="select * from user where id=?";
		Integer attr[] = {id};
		return jdbcTemplate.queryForObject(sql,attr,new UserMapper());
	}
	public User get(String field,String value){
		String sql="select * from user where "+field+"=?";
		String attr[] = {value};
		return jdbcTemplate.queryForObject(sql,attr,new UserMapper());
	}
	
	
	class UserMapper implements RowMapper<User>{
		
		@Override
		public User mapRow (ResultSet rs, int id) throws SQLException{
			User user = new User();
			
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setSurname(rs.getString("surname"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setEnabled(rs.getBoolean("enabled"));
			user.setMail(rs.getString("mail"));
			user.setAuth(serviceUserAutority.convertArrToList(daoUserAuthority.find(user.getUsername())));
			
			String avatarPath=rs.getString("avatarPath");
			if(avatarPath==null ||avatarPath.equals("")){
				user.setAvatarPath("/fake/resource/fakeAvatar.jpg");
			}else{
				user.setAvatarPath(avatarPath);
			}
			user.setQuota(rs.getLong("quota"));
			
			return user;
		}
	} 
}
