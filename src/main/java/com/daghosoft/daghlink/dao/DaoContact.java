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
import org.springframework.stereotype.Repository;

import com.daghosoft.daghlink.bean.Contact;

@Repository
public class DaoContact {

	private static final Logger logger = LoggerFactory.getLogger(DaoContact.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource (DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public Contact get(int id){
		String sql="select * from contact where id=?";
		Integer attr[] = {id};
		return jdbcTemplate.queryForObject(sql,attr,new ContactMapper());
	}
	
	public List<Contact> find(int user_id){
		String sql = "Select * from contact where user_id= "+user_id+"  order by mail";
		logger.trace(sql);
		List<Contact> contactList =  jdbcTemplate.query(sql,new ContactMapper());
		return contactList;
	}
	
	public List<Contact> findByMail(String mail,int user_id){
		String sql = "Select * from contact where mail= '"+mail+"' and user_id="+user_id+" order by mail";
		logger.trace(sql);
		List<Contact> contactList =  jdbcTemplate.query(sql,new ContactMapper());
		return contactList;
	}
	
	public void update(Contact contact,int user_id){
		String sql = "update contact set name=?,surname=?,mail=? where id=? and user_id=?";
		logger.trace(sql);
		jdbcTemplate.update(sql,contact.getName(),contact.getSurname(),contact.getMail(),contact.getId(),user_id);
	}
	
	public int create(Contact contact){
		String sql = "insert into contact (name,surname,mail,user_id) values (?,?,?,?)";
		logger.trace(sql);
		jdbcTemplate.update(sql,contact.getName(),contact.getSurname(),contact.getMail(),contact.getUser_id());
		return 0;
	}
	
	public void delete(int id,int user_id){
		String sql = "delete from contact where id=? and user_id=?";
		jdbcTemplate.update(sql, id,user_id);
	}
	
	
class ContactMapper implements RowMapper<Contact>{
		
		@Override
		public Contact mapRow (ResultSet rs, int id) throws SQLException{
			Contact contact = new Contact();
			
			contact.setId(rs.getInt("id"));
			contact.setName(rs.getString("name"));
			contact.setSurname(rs.getString("surname"));
			contact.setUser_id(rs.getInt("user_id"));
			contact.setCompany(rs.getString("company"));
			contact.setMail(rs.getString("mail"));
			contact.setMail2(rs.getString("mail2"));
			contact.setMail3(rs.getString("mail3"));
			
			return contact;
		}
	} 
}
