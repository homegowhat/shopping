package com.shopping.web.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import os.rabbit.dao.IDaoObject;

public abstract class AbsBaseDao  implements IDaoObject,ApplicationContextAware {
	protected ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

	protected DataSource dataSource;
	protected Connection connection;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Connection getConnection() throws SQLException {
		if(connection != null) return connection;
		
		return dataSource.getConnection();
	}
	
	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
		
	}

	protected void rsClose(ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
			}
	}

	protected void connClose(Connection conn) {
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
			}
	}

	protected void stmtClose(PreparedStatement stmt) {
		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
			}
	}


}
