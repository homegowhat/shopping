package com.shopping.web.db.dao;

import java.sql.SQLException;
import java.util.List;

import com.shopping.web.db.dto.Catalog;



public interface ICatalogDao {
	public void update(Catalog catalog) throws SQLException;
	public void insert(Catalog catalog) throws SQLException;
	public List<Catalog> getCatalogByParentId(int parentId,String type) throws SQLException;

	
	public void delete(int id) throws SQLException;
	public Long getCatalogCountByParentId(int parentId,String type) throws SQLException;
	public Catalog getCatalogById(int id) throws SQLException;
	
	public List<Catalog> getCatalogByRelease(String type) throws SQLException;

}