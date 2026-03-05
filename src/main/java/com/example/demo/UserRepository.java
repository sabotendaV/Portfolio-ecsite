package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	//会員登録用
	public void save(User user) {
	    String sql = "INSERT INTO users (last_name, first_name, email, password, role) VALUES (?, ?, ?, ?, ?)";
	    jdbcTemplate.update(sql,
	        user.getLastName(),
	        user.getFirstName(),
	        user.getEmail(),
	        user.getPassword(),
	        user.getRole()
	    );
	}
	
	//ログイン認証用
	public User findByEmail(String email) {
	    String sql = "SELECT * FROM users WHERE email = ?";
	    return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
	        User user = new User();
	        user.setId(rs.getLong("id"));
	        user.setLastName(rs.getString("last_name"));
	        user.setFirstName(rs.getString("first_name"));
	        user.setEmail(rs.getString("email"));
	        user.setPassword(rs.getString("password"));
	        user.setRole(rs.getString("role"));
	        return user;
	    }, email);
	}
}
