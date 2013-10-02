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

@Repository
public class DaoLink {
	
	private static final Logger logger = LoggerFactory.getLogger(DaoLink.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource (DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	public List<Link> findLink(){
		String sql = "Select * from link order by id desc";
		List<Link> linkList =  jdbcTemplate.query(sql,new LinkMapper());
		return linkList;
	}
	public List<Link> findLink(Integer user_id){
		List<Link> linkList =  find("user_id="+user_id+" and type=''");
		return linkList;
	}
	public List<Link> findLinkByGroup(Integer user_id,Integer father){
		List<Link> linkList =  find("user_id="+user_id+" and father="+father+" and type=''");
		return linkList;
	}
	public List<Link> findLinkByGroup(Integer father){
		List<Link> linkList =  find(" father="+father+" and type=''");
		return linkList;
	}
	private List<Link> find(String filter){
		String sql = "Select * from link where "+filter+" order by id desc";
		logger.trace(sql);
		List<Link> linkList =  jdbcTemplate.query(sql,new LinkMapper());
		return linkList;
	}
	public int create(Link link){
		String sql = "insert into link (user_id,title,link,description,type,father,date,uuid) values (?,?,?,?,?,?,?,?)";
		java.sql.Date date = new java.sql.Date(link.getDate().getTime());
		logger.trace(sql);
		return jdbcTemplate.update(sql, link.getUser_id() ,link.getTitle(),link.getLink(),link.getDescription(),link.getType(),link.getFather(),date,link.getUuid());
	}
	public void update(Link link,int user_id){
		String sql = "update link set title=?,link=?,description=?,type=?,father=? where id=? and user_id=? ";
		jdbcTemplate.update(sql, link.getTitle(),link.getLink(),link.getDescription(),link.getType(),link.getFather(),link.getId(),user_id);
	}
	public void updateFather(int father,int id, int user_id) {
		String sql = "update link set father=? where id=? and user_id=? ";
		logger.trace(sql+"-"+father+"-"+id+"-"+user_id );
		jdbcTemplate.update(sql, father,id,user_id);
	} 
	public void resetFather(int father){
		String sql = "update link set father=0 where father=?";
		logger.trace(sql+" "+father);
		jdbcTemplate.update(sql, father);
	}
	public void delete(int id,int user_id){
		String sql = "delete from link where id=? and user_id=?";
		jdbcTemplate.update(sql, id,user_id);
	}
	public Link get(int id){
		String sql="select * from link where id=?";
		Integer attr[] = {id};
		return jdbcTemplate.queryForObject(sql,attr,new LinkMapper());
	}
	
	public Link get(String uuid){
		String sql="select * from link where uuid=?";
		String attr[] = {uuid};
		return jdbcTemplate.queryForObject(sql,attr,new LinkMapper());
	}
	
	public int countByFather(int id){
		String sql="SELECT COUNT(id) FROM link where father=?";
		return jdbcTemplate.queryForInt(sql, id);
	}
	
	class LinkMapper implements RowMapper<Link>{
		
		@Override
		public Link mapRow (ResultSet rs, int id) throws SQLException{
			Link link = new Link();
			
			link.setId(rs.getInt("id"));
			link.setUser_id(rs.getInt("user_id"));
			link.setTitle(rs.getString("title"));
			link.setLink(rs.getString("link"));
			link.setDescription(rs.getString("description"));
			link.setType(rs.getString("type"));
			link.setFather(rs.getInt("father"));
			link.setDate(rs.getDate("date"));
			link.setUuid(rs.getString("uuid"));
			if (rs.getString("type")!=null){
			 link.setChild(countByFather(link.getId()));
			}
			
			return link;
		}
	} 
}
