package com.shopping.web.db.dao;

import java.sql.SQLException;
import java.util.List;

import com.shopping.web.db.dto.User;



public interface IUserDao {
	public Long getUserCount(String status) throws SQLException;
	public void insert(User user) throws SQLException;
	public User getUserByEmail(String email) throws SQLException;
	public User getUserByName(String name) throws SQLException;
	public void update(User user) throws SQLException;
	public User getUserById(int id) throws SQLException;
	public List<User> getUserList(String status,int pageCode,int pageSize) throws SQLException;

}