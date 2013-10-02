package com.daghosoft.daghlink.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.daghosoft.daghlink.bean.Download;

@Repository
public class DaoDownload {

	private static final Logger logger = LoggerFactory.getLogger(DaoDownload.class);
		private JdbcTemplate jdbcTemplate;
		
		@Autowired
		public void setDataSource (DataSource dataSource){
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		
		public List<Download> get(int file_id){
			String sql = "Select * from file_download where file_id=? ";
			logger.trace(sql + " " + file_id);
			Integer attr[] = {file_id};
			return jdbcTemplate.query(sql,attr,new DownloadMapper());
		}
 		
		public int create(int file_id,String ip){
			String sql = "INSERT INTO file_download (file_id,ip,date) VALUES (?,?,?)";
			logger.trace(sql+" "+file_id);
			return jdbcTemplate.update(sql,file_id,ip,DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss")); 
		}
		
		public int count(int id){
			String sql="SELECT COUNT(id) FROM file_download where file_id=?";
			return jdbcTemplate.queryForInt(sql, id);
		}
				
		class DownloadMapper implements RowMapper<Download>{
			@Override
			public Download mapRow (ResultSet rs, int id) throws SQLException{
				Download dwn = new Download();
				
				dwn.setId(rs.getInt("id"));
				dwn.setFile_id(rs.getInt("file_id"));
				dwn.setIp(rs.getString("ip"));
				dwn.setDate(rs.getDate("date"));
				
				return dwn;
			}
		} 
		
		
		
}
