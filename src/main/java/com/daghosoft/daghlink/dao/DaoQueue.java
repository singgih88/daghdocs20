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

import com.daghosoft.daghlink.bean.Link;
import com.daghosoft.daghlink.bean.Queue;

@Repository
public class DaoQueue {

	private static final Logger logger = LoggerFactory.getLogger(DaoQueue.class);
		private JdbcTemplate jdbcTemplate;
		
		@Autowired
		public void setDataSource (DataSource dataSource){
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		
		public List<Queue> allQueue(){
			String sql = "Select * from queue ";
			logger.trace(sql);
			List<Queue> queue =  jdbcTemplate.query(sql,new QueueMapper());
			return queue;
		}
		
		public Boolean findQueue(int user_id){
			String sql = "Select * from queue Where user_id = ? ";
			logger.trace(sql+" "+user_id);
			String [] attr = {String.valueOf(user_id)};
			List<Queue> queue =  jdbcTemplate.query(sql,attr,new QueueMapper());
			Boolean out = false;
			if(queue.size()>0){
				out = true ;
			};
			return out;
		}
		
		public int create(int user_id){
			String sql = "INSERT INTO `queue` (`user_id`) VALUES (?)";
			logger.trace(sql+" "+user_id);
			return jdbcTemplate.update(sql,user_id); 
		}
		
		public void delete(int user_id){
			String sql = "delete from queue where user_id=?";
			logger.trace(sql+" "+user_id);
			jdbcTemplate.update(sql, user_id);
		}
		
		public void update(Queue queue){
			String sql = "update queue set fail=? where user_id=? ";
			logger.trace(sql+" "+queue.getFail() + " " +queue.getUser_id());
			jdbcTemplate.update(sql, queue.getFail(),queue.getUser_id());
		}
		
				
		class QueueMapper implements RowMapper<Queue>{
			@Override
			public Queue mapRow (ResultSet rs, int id) throws SQLException{
				Queue queue = new Queue();
				
				queue.setId(rs.getInt("id"));
				queue.setUser_id(rs.getInt("user_id"));
				queue.setFail(rs.getInt("fail"));
				
				return queue;
			}
		} 
		
		
		
}
