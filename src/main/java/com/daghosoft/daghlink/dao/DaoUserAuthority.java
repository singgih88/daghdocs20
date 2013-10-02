package com.daghosoft.daghlink.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.daghosoft.daghlink.bean.UserAuthority;

@Repository
public class DaoUserAuthority {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public int create(UserAuthority userAuthority) {
		String sql = "insert into user_authority (username,authority) values (?,?)";
		jdbcTemplate.update(sql, userAuthority.getUsername(),userAuthority.getAuthority());
		return 0;
	}

	public void create(List<UserAuthority> list) {
		for (UserAuthority userAuthority : list) {
			create(userAuthority);
		}
	}

	public void delete(String username) {
		String sql = "delete from user_authority where username=?";
		jdbcTemplate.update(sql, username);
	}

	public List<UserAuthority> find(String username) {
		String sql = "Select * from user_authority where username = ?";
		String[] args = { username };
		List<UserAuthority> userList = jdbcTemplate.query(sql, args,new UserAuthorityMapper());
		return userList;
	}

	class UserAuthorityMapper implements RowMapper<UserAuthority> {

		@Override
		public UserAuthority mapRow(ResultSet rs, int id) throws SQLException {
			UserAuthority userAuthority = new UserAuthority();
			userAuthority.setUsername(rs.getString("username"));
			userAuthority.setAuthority(rs.getString("authority"));
			return userAuthority;
		}
	}
}
