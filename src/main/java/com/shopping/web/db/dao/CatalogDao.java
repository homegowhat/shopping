package com.shopping.web.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.shopping.web.db.AbsBaseDao;
import com.shopping.web.db.dto.Catalog;



public class CatalogDao extends AbsBaseDao implements ICatalogDao {
	
	public void update(Catalog catalog) throws SQLException {
		Connection connection = getConnection();
		StringBuffer updateColumnSQL = new StringBuffer();
		if(catalog.isModified("parentId")) {
			updateColumnSQL.append("`parent_id` = ?,");
		}
		if(catalog.isModified("name")) {
			updateColumnSQL.append("`name` = ?,");
		}
		if(catalog.isModified("type")) {
			updateColumnSQL.append("`type` = ?,");
		}
		if(catalog.isModified("sort")) {
			updateColumnSQL.append("`sort` = ?,");
		}
		if(catalog.isModified("websiteId")) {
			updateColumnSQL.append("`website_id` = ?,");
		}
		if(catalog.isModified("pic")) {
			updateColumnSQL.append("`pic` = ?,");
		}
		if(updateColumnSQL.length() > 0) {
			updateColumnSQL.deleteCharAt(updateColumnSQL.length() - 1);
		} else {
			return;
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE `catalog` SET ");
		sql.append(updateColumnSQL.toString());
		sql.append(" WHERE ");
		sql.append(" `id` = ? AND");
		String sqlCode = sql.toString();
		if(sqlCode.endsWith("AND")) {
			sqlCode = sqlCode.substring(0, sqlCode.length() - 3);
		}
		PreparedStatement ps = connection.prepareStatement(sqlCode);
		try {
		
		int index = 1;
		if(catalog.isModified("parentId")) {
			ps.setInt(index++, catalog.getParentId());
		}
		if(catalog.isModified("name")) {
			ps.setString(index++, catalog.getName());
		}
		if(catalog.isModified("type")) {
			ps.setString(index++, catalog.getType());
		}
		if(catalog.isModified("sort")) {
			ps.setInt(index++, catalog.getSort());
		}
		if(catalog.isModified("websiteId")) {
			ps.setString(index++, catalog.getWebsiteId());
		}
		if(catalog.isModified("pic")) {
			ps.setString(index++, catalog.getPic());
		}
		
		ps.setInt(index++, catalog.getId());
		
		ps.execute();
		} finally {
			ps.close();
		}

	}

	public void insert(Catalog catalog) throws SQLException {
		Connection connection = getConnection();
		
		PreparedStatement ps = connection.prepareStatement("INSERT INTO `catalog`(`id`,`parent_id`,`name`,`type`,`sort`,`website_id`,`pic`) VALUES(?,?,?,?,?,?,?)");
		try {
		ps.setInt(1, catalog.getId());
		ps.setInt(2, catalog.getParentId());
		ps.setString(3, catalog.getName());
		ps.setString(4, catalog.getType());
		ps.setInt(5, catalog.getSort());
		ps.setString(6, catalog.getWebsiteId());
		ps.setString(7, catalog.getPic());
		ps.execute();
		} finally {
			ps.close();
		}

	}

	public List<Catalog> getCatalogByParentId(int parentId,String type) throws SQLException {
		LinkedList<Catalog> returnVal = new LinkedList<Catalog>();
		Connection connection = getConnection();
		ResultSet rs = null;
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM `catalog` WHERE parent_id = ? AND type = ?");
		try {
							ps.setInt(1, parentId);
							ps.setString(2, type);
				
		
			rs = ps.executeQuery();
			while(rs.next()) {
				Catalog obj = new Catalog(
						rs.getInt("id"),	
						rs.getInt("parent_id"),	
						rs.getString("name"),	
						rs.getString("type"),	
						rs.getInt("sort"),	
						rs.getString("website_id")	,	
						rs.getString("pic")	
					);
				returnVal.add(obj);
				
			}
		} finally {
			ps.close();
			if(rs != null) rs.close();
		}
		return returnVal;
	}


	
	public void delete(int id) throws SQLException {
		Connection connection = getConnection();
		
		PreparedStatement ps = connection.prepareStatement("DELETE FROM `catalog` WHERE `id` = ?");
		try {
		ps.setInt(1, id);

		ps.execute();
		} finally {
			ps.close();
		}
	}

	public Long getCatalogCountByParentId(int parentId,String type) throws SQLException {
		Connection connection = getConnection();
		
		PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM `catalog` WHERE parent_id = ? AND type = ?");
		ResultSet rs = null;
		try {
					ps.setInt(1, parentId);
					ps.setString(2, type);
				rs = ps.executeQuery();
		while(rs.next()) {
			return rs.getLong(1);
		}
		} finally {
			ps.close();
			if(rs != null)
				rs.close();
		}
		return 0L;
	}

	public Catalog getCatalogById(int id) throws SQLException {
		Connection connection = getConnection();
		
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM `catalog` WHERE id = ?");
		ResultSet rs = null;
		try {
							ps.setInt(1, id);
						rs = ps.executeQuery();
			while(rs.next()) {
				Catalog obj = new Catalog(
				rs.getInt("id"),
				rs.getInt("parent_id"),
				rs.getString("name"),
				rs.getString("type"),
				rs.getInt("sort"),
				rs.getString("website_id"),	
				rs.getString("pic")	
				);
				return obj;
			}
		} finally {
			ps.close();
			if(rs != null) rs.close();
		}
		return null;
	}

	public List<Catalog> getCatalogByRelease(String type) throws SQLException {
		LinkedList<Catalog> returnVal = new LinkedList<Catalog>();
		Connection connection = getConnection();
		ResultSet rs = null;
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM `catalog` a, `release` b WHERE b.type=?  AND b.target_id = a.id");
		try {
							ps.setString(1, type);
				
		
			rs = ps.executeQuery();
			while(rs.next()) {
				Catalog obj = new Catalog(
						rs.getInt("id"),	
						rs.getInt("parent_id"),	
						rs.getString("name"),	
						rs.getString("type"),	
						rs.getInt("sort"),	
						rs.getString("website_id")	,	
						rs.getString("pic")	
					);
				returnVal.add(obj);
				
			}
		} finally {
			ps.close();
			if(rs != null) rs.close();
		}
		return returnVal;
	}

}