package com.shopping.web.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.shopping.web.db.AbsBaseDao;
import com.shopping.web.db.dto.Manufacturer;

public class ManufacturerDao extends AbsBaseDao implements IManufacturerDao {
	private static final transient Logger logger = Logger.getLogger(ManufacturerDao.class);


	public List<Manufacturer> list(int pageCode,int pageSize) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT * FROM manufacturer LIMIT ?,?");
					ps.setInt(1, pageCode);
					ps.setInt(2, pageSize);
		
			LinkedList<Manufacturer> returnVal = new LinkedList<Manufacturer>();
			for(ResultSet rs = ps.executeQuery(); rs.next(); ) {
				Manufacturer obj = new Manufacturer(
					rs.getInt("id"),
					rs.getString("name"),
					rs.getString("link")
				);
				returnVal.add(obj);
			
			}
			return returnVal;
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			stmtClose(ps);
			connClose(conn);
		}
		return java.util.Collections.emptyList();
	}

	public Long countByKey(String name) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT COUNT(*) FROM manufacturer WHERE name LIKE ?");
					ps.setString(1, name);
		
			for(ResultSet rs = ps.executeQuery(); rs.next(); ) {
				return rs.getLong(1);
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw e;
		} finally {
			stmtClose(ps);
			connClose(conn);
		}
		return 0L;
	}

	public void insert(Manufacturer manufacturer) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("INSERT INTO `manufacturer`(`id`,`name`,`link`) VALUES(?,?,?)");
			ps.setInt(1, manufacturer.getId());
			ps.setString(2, manufacturer.getName());
			ps.setString(3, manufacturer.getLink());
			ps.execute();
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw e;
		} finally {
			stmtClose(ps);
			connClose(conn);
		}
	}

	public void update(Manufacturer manufacturer) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			StringBuffer updateColumnSQL = new StringBuffer();
			if(manufacturer.isModified("name")) {
				updateColumnSQL.append("`name` = ?,");
			}
			if(manufacturer.isModified("link")) {
				updateColumnSQL.append("`link` = ?,");
			}
			if(updateColumnSQL.length() > 0) {
				updateColumnSQL.deleteCharAt(updateColumnSQL.length() - 1);
			} else {
				return;
			}
		
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE `manufacturer` SET ");
			sql.append(updateColumnSQL.toString());
			sql.append(" WHERE ");
			sql.append(" `id` = ? AND");
			String sqlCode = sql.toString();
			if(sqlCode.endsWith("AND")) {
				sqlCode = sqlCode.substring(0, sqlCode.length() - 3);
			}
			ps = conn.prepareStatement(sqlCode);
		
		
			int index = 1;
			if(manufacturer.isModified("name")) {
				ps.setString(index++, manufacturer.getName());
			}
			if(manufacturer.isModified("link")) {
				ps.setString(index++, manufacturer.getLink());
			}

			ps.setInt(index++, manufacturer.getId());
		
			ps.execute();
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw e;
		} finally {
			stmtClose(ps);
			connClose(conn);
		}

	}


	
	public void delete(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("DELETE FROM manufacturer WHERE `id` = ?");
			ps.setInt(1, id);

			ps.execute();
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw e;
		} finally {
			stmtClose(ps);
			connClose(conn);
		}
	}

	public List<Manufacturer> listByKey(String name,int pageCode,int pageSize) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT * FROM manufacturer WHERE name LIKE ? LIMIT ?,?");
					ps.setString(1, name);
					ps.setInt(2, pageCode);
					ps.setInt(3, pageSize);
		
			LinkedList<Manufacturer> returnVal = new LinkedList<Manufacturer>();
			for(ResultSet rs = ps.executeQuery(); rs.next(); ) {
				Manufacturer obj = new Manufacturer(
					rs.getInt("id"),
					rs.getString("name"),
					rs.getString("link")
				);
				returnVal.add(obj);
			
			}
			return returnVal;
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			stmtClose(ps);
			connClose(conn);
		}
		return java.util.Collections.emptyList();
	}

	public Long count() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT COUNT(*) FROM manufacturer");
		
			for(ResultSet rs = ps.executeQuery(); rs.next(); ) {
				return rs.getLong(1);
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw e;
		} finally {
			stmtClose(ps);
			connClose(conn);
		}
		return 0L;
	}

	public Manufacturer get(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT * FROM manufacturer WHERE id = ?");
					ps.setInt(1, id);
		
			for(ResultSet rs = ps.executeQuery(); rs.next(); ) {
				Manufacturer obj = new Manufacturer(
					rs.getInt("id"),
					rs.getString("name"),
					rs.getString("link")
				);
				return obj;
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			stmtClose(ps);
			connClose(conn);
		}
		return null;
	}



}