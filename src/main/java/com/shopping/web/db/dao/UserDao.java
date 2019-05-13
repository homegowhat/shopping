package com.shopping.web.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import os.rabbit.dao.IDaoObject;

import com.shopping.web.db.AbsBaseDao;
import com.shopping.web.db.dto.User;

public class UserDao  extends AbsBaseDao implements  IUserDao {


	public Long getUserCount( String status) throws SQLException {
		Connection connection = getConnection();

		PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM `user` WHERE   status = ?");
		ResultSet rs = null;
		try {
			ps.setString(1, status);
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getLong(1);
			}
		} finally {
			ps.close();
			if (rs != null)
				rs.close();
		}
		return 0L;
	}

	public void insert(User user) throws SQLException {
		Connection connection = getConnection();

		PreparedStatement ps = connection.prepareStatement("INSERT INTO `user`(`id`,`email`,`password`,`name`,`status`,`website_id`) VALUES(?,?,?,?,?,?)");
		try {
			ps.setInt(1, user.getId());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getName());
			ps.setString(5, user.getStatus());
			ps.setString(6, user.getWebsiteId());
			ps.execute();
		} finally {
			ps.close();
		}

	}

	public User getUserByEmail(String email) throws SQLException {
		Connection connection = getConnection();
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM `user` WHERE `email` = ? ");
		ResultSet rs = null;
		try {
			ps.setString(1, email);
			rs = ps.executeQuery();
			while (rs.next()) {
				User obj = new User(rs.getInt("id"), rs.getString("email"),rs.getString("login_name"), rs.getString("password"), rs.getString("name"), rs.getString("status"), rs.getString("website_id"));
				return obj;
			}
		} finally {
			ps.close();
			if (rs != null)
				rs.close();
		}
		return null;
	}
	
	@Override
	public User getUserByName(String name) throws SQLException {
		Connection connection = getConnection();
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM `user` WHERE `name` = ? ");
		ResultSet rs = null;
		try {
			ps.setString(1, name);
			rs = ps.executeQuery();
			while (rs.next()) {
				User obj = new User(rs.getInt("id"), rs.getString("email"),rs.getString("login_name"), rs.getString("password"), rs.getString("name"), rs.getString("status"), rs.getString("website_id"));
				return obj;
			}
		} finally {
			ps.close();
			if (rs != null)
				rs.close();
		}
		return null;
	}

	public void update(User user) throws SQLException {
		Connection connection = getConnection();
		StringBuffer updateColumnSQL = new StringBuffer();
		if (user.isModified("password")) {
			updateColumnSQL.append("`password` = ?,");
		}
		if (user.isModified("name")) {
			updateColumnSQL.append("`name` = ?,");
		}
		if (user.isModified("status")) {
			updateColumnSQL.append("`status` = ?,");
		}
		if (user.isModified("loginName")) {
			updateColumnSQL.append("`login_name` = ?,");
		}
		if (updateColumnSQL.length() > 0) {
			updateColumnSQL.deleteCharAt(updateColumnSQL.length() - 1);
		} else {
			return;
		}

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE `user` SET ");
		sql.append(updateColumnSQL.toString());
		sql.append(" WHERE ");
		sql.append(" `id` = ? AND");
		String sqlCode = sql.toString();
		if (sqlCode.endsWith("AND")) {
			sqlCode = sqlCode.substring(0, sqlCode.length() - 3);
		}
		PreparedStatement ps = connection.prepareStatement(sqlCode);
		try {

			int index = 1;
			if (user.isModified("password")) {
				ps.setString(index++, user.getPassword());
			}
			if (user.isModified("name")) {
				ps.setString(index++, user.getName());
			}
			if (user.isModified("status")) {
				ps.setString(index++, user.getStatus());
			}

			ps.setInt(index++, user.getId());

			ps.execute();
		} finally {
			ps.close();
		}

	}

	public User getUserById(int id) throws SQLException {
		Connection connection = getConnection();

		PreparedStatement ps = connection.prepareStatement("SELECT * FROM `user` WHERE `id` = ?");
		ResultSet rs = null;
		try {
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				User obj = new User(rs.getInt("id"), rs.getString("email"), rs.getString("login_name"), rs.getString("password"), rs.getString("name"), rs.getString("status"), rs.getString("website_id"));
				return obj;
			}
		} finally {
			ps.close();
			if (rs != null)
				rs.close();
		}
		return null;
	}

	public List<User> getUserList( String status, int pageCode, int pageSize) throws SQLException {
		LinkedList<User> returnVal = new LinkedList<User>();
		Connection connection = getConnection();
		ResultSet rs = null;
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM `user` WHERE  status = ? LIMIT ?,?");
		try {
			ps.setString(1, status);
			ps.setInt(2, pageCode);
			ps.setInt(3, pageSize);

			rs = ps.executeQuery();
			while (rs.next()) {
				User obj = new User(rs.getInt("id"), rs.getString("email"), rs.getString("login_name"), rs.getString("password"), rs.getString("name"), rs.getString("status"), rs.getString("website_id"));
				returnVal.add(obj);

			}
		} finally {
			ps.close();
			if (rs != null)
				rs.close();
		}
		return returnVal;
	}



}