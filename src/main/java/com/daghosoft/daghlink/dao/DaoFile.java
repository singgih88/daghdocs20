package com.daghosoft.daghlink.dao;

 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.daghosoft.daghlink.bean.FileBean;


@Repository
public class DaoFile {
	
	private static final Logger logger = LoggerFactory.getLogger(DaoFile.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource (DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	public List<FileBean> findFileByGroup(Integer user_id,Integer father){
		List<FileBean> FileList =  find("user_id="+user_id+" and father="+father+" and (type='' or type is null )");
		return FileList;
	}
	public List<FileBean> findFileByGroup(Integer father){
		List<FileBean> FileList =  find(" father="+father+" and (type='' or type is null )");
		return FileList;
	}
	private List<FileBean> find(String filter){
		String sql = "Select * from file where "+filter+" order by id desc";
		logger.trace(sql);
		List<FileBean> FileList =  jdbcTemplate.query(sql,new ItemMapper());
		return FileList;
	}
	public Long GetTotalSizeByUser(int user_id) {
		String sql = "SELECT SUM(size) size FROM file WHERE user_id=?";
		logger.trace(sql);
		return jdbcTemplate.queryForLong(sql,user_id);
	}
////////////////////////////////////TO REVIEW///////////////////////////////////////////////////////
	/*public int create(FileBean file,int user_id){
		String sql = "insert into file (user_id,title,path,description,type,father,date,uuid,size,filename) values (?,?,?,?,?,?,?,?,0,'')";
		java.sql.Date date = new java.sql.Date(file.getDate().getTime());
		logger.trace(sql);
		return jdbcTemplate.update(sql, user_id,file.getTitle(),file.getPath(),file.getDescription(),file.getType(),file.getFather(),date,file.getUuid());
	}
	public void updateFileUpload(FileBean file,int user_id){
		String sql = "update file set filename=?,size=?,path=? where uuid=? and user_id=? ";
		jdbcTemplate.update(sql, file.getFilename(),file.getSize(),file.getPath(),file.getUuid(),user_id);
	}*/
///////////////////////////////////////////////////////////////////////////////////////////	
	public void update(FileBean file,int user_id){
		String sql = "update file set title=?,description=?,type=?,father=? where id=? and user_id=? ";
		jdbcTemplate.update(sql, file.getTitle(),file.getDescription(),file.getType(),file.getFather(),file.getId(),user_id);
	}
	
	public int createFile(FileBean file,int user_id){
		String sql = "insert into file (user_id,title,path,description,type,father,date,uuid,size,filename) values (?,?,?,?,?,?,?,?,?,?)";
		file.setDate(new Date());
		java.sql.Date date = new java.sql.Date(file.getDate().getTime());
		logger.trace(sql);
		return jdbcTemplate.update(sql, user_id,file.getTitle(),file.getPath(),file.getDescription(),file.getType(),file.getFather(),date,file.getUuid(),file.getSize(),file.getFilename());
	}
	
	public void resetFather(int father){
		String sql = "update file set father=0 where father=?";
		jdbcTemplate.update(sql, father);
	}
	public void delete(int id,int user_id){
		String sql = "delete from file where id=? and user_id=?";
		jdbcTemplate.update(sql, id,user_id);
	}
	public void updateFather(int father,int id, int user_id) {
		String sql = "update file set father=? where id=? and user_id=? ";
		logger.debug(sql+"-"+father+"-"+id+"-"+user_id );
		jdbcTemplate.update(sql, father,id,user_id);
	} 
	public FileBean get(int id){
		String sql="select * from file where id=?";
		Integer attr[] = {id};
		return jdbcTemplate.queryForObject(sql,attr,new ItemMapper());
	}
	
	public FileBean get(String uuid){
		String sql="select * from file where uuid=?";
		String attr[] = {uuid};
		return jdbcTemplate.queryForObject(sql,attr,new ItemMapper());
	}
	
	public int countByFather(int id){
		String sql="SELECT COUNT(id) FROM file where father=?";
		return jdbcTemplate.queryForInt(sql, id);
	}
	
	class ItemMapper implements RowMapper<FileBean>{
		
		@Override
		public FileBean mapRow (ResultSet rs, int id) throws SQLException{
			FileBean item = new FileBean();
			
			item.setId(rs.getInt("id"));
			item.setUser_id(rs.getInt("user_id"));
			item.setTitle(rs.getString("title"));
			item.setPath(rs.getString("path"));
			item.setFilename(rs.getString("filename"));
			item.setDescription(rs.getString("description"));
			item.setType(rs.getString("type"));
			item.setFather(rs.getInt("father"));
			item.setDate(rs.getDate("date"));
			item.setUuid(rs.getString("uuid"));
			item.setSize(rs.getLong("size"));
			if (rs.getString("type")!=null){
				item.setChild(countByFather(item.getId()));
			}
			
			return item;
		}
	}


}
