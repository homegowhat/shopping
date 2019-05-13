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



public interface IItemDao {
	public void update(Item item) throws SQLException;
	public Long countBycategory(int category) throws SQLException;
	public void insert(Item item) throws SQLException;
	public Long count() throws SQLException;
	public List<Item> list(int pageCode,int pageSize) throws SQLException;
	public Long countByManufacturer(int manufacturerId) throws SQLException;

	
	public void delete(int id) throws SQLException;
	public List<Item> listByCategory(int category,int pageCode,int pageSize) throws SQLException;
	public Item get(int id) throws SQLException;
	public List<Item> listByManufacturer(int manufacturerId,int pageCode,int pageSize) throws SQLException;

}