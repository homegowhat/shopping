package com.shopping.web.db.dao;

import java.sql.SQLException;

import com.shopping.web.db.dto.Ids;



public interface IIdsDao {
	public void insert(Ids ids) throws SQLException;
	public Ids get(String websiteId,String name) throws SQLException;
	public void update(Ids ids) throws SQLException;

}