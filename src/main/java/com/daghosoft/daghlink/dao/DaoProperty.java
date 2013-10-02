package com.daghosoft.daghlink.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.daghosoft.daghlink.bean.Property;
import com.daghosoft.daghlink.bean.User;

@Repository
public class DaoProperty {

	private static final Logger logger = LoggerFactory.getLogger(DaoProperty.class);
		private JdbcTemplate jdbcTemplate;
		
		@Autowired
		public void setDataSource (DataSource dataSource){
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		
		public List<Property>  find(String user_id){
			String sql = "Select * from property where user_id is null or user_id=0 Order By `key`";
			if (user_id!=null && !user_id.equals("")){
				sql = "Select * from property where user_id="+user_id;
			}
			logger.trace(sql);
			List<Property> list =  jdbcTemplate.query(sql,new PropertyMapper());
			return list;
		}
		public List<Property>  find(String key,int user_id){
			String sql = "Select * from property where user_id=? and `key`=?";
			String [] attr={String.valueOf(user_id),key};
			logger.trace(sql);
			List<Property> list =  jdbcTemplate.query(sql,attr,new PropertyMapper());
			return list;
		}
				
		public int create(Property property){
			String sql = "INSERT INTO `property` (`key`, `value`,user_id) VALUES (?, ?, ?)";
			logger.trace(sql+" "+property.getValue()+" - "+property.getKey());
			return jdbcTemplate.update(sql,property.getKey().trim(),property.getValue().trim(),property.getUser_id()); 
		}
		
		public void update(String value,String id){
			Property property = new Property();
			property.setId(NumberUtils.toInt(id));
			property.setValue(value);
			update(property);
		}
		
		public void update(Property property){
			String sql = "update property set value=? where id=? and (user_id is null or user_id=0)";
			logger.trace(sql+" "+property.getValue()+" - "+property.getId());
			jdbcTemplate.update(sql, property.getValue().trim(),property.getId());
		}
		
		public void delete(int id){
			String sql = "delete from property where id=?";
			jdbcTemplate.update(sql, id);
		}
		
		
		public void personalSetting(String key,String value,User user){
			String sql = "delete from property where `key`=? and user_id=?";
			jdbcTemplate.update(sql, key,user.getId());
			logger.trace(sql+" "+key+" "+user.getId());
			
			sql = "INSERT INTO `property` (`key`, `value`,`user_id`) VALUES (?,?,?)";
			jdbcTemplate.update(sql,key.trim(),value.trim(),user.getId());
			logger.trace(sql+" "+key.trim()+" "+value.trim()+" "+user.getId());
		}
		
		public void executeSql(String sql){
			jdbcTemplate.execute(sql);
		}
		
		public void clear(){
			String sql="delete from property";
			jdbcTemplate.execute(sql);
		}
				
		class PropertyMapper implements RowMapper<Property>{
			@Override
			public Property mapRow (ResultSet rs, int id) throws SQLException{
				Property property = new Property();
				
				property.setId(rs.getInt("id"));
				property.setUser_id(rs.getInt("user_id"));
				property.setKey(rs.getString("key"));
				property.setValue(rs.getString("value"));
				
				return property;
			}
		} 
		
		
		
}
