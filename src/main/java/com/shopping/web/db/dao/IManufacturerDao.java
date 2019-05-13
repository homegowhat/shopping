package com.shopping.web.db.dao;

import com.shopping.web.db.dto.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;



public interface IManufacturerDao {
	public List<Manufacturer> list(int pageCode,int pageSize) throws SQLException;
	public Long countByKey(String name) throws SQLException;
	public void insert(Manufacturer manufacturer) throws SQLException;
	public void update(Manufacturer manufacturer) throws SQLException;

	
	public void delete(int id) throws SQLException;
	public List<Manufacturer> listByKey(String name,int pageCode,int pageSize) throws SQLException;
	public Long count() throws SQLException;
	public Manufacturer get(int id) throws SQLException;

}