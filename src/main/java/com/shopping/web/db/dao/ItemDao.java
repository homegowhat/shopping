package com.shopping.web.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.shopping.web.db.AbsBaseDao;
import com.shopping.web.db.dto.Item;

public class ItemDao extends AbsBaseDao implements IItemDao {
	private static final transient Logger logger = Logger.getLogger(ItemDao.class);


	public void update(Item item) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			StringBuffer updateColumnSQL = new StringBuffer();
			if(item.isModified("manufacturerId")) {
				updateColumnSQL.append("`manufacturer_id` = ?,");
			}
			if(item.isModified("unitPrice")) {
				updateColumnSQL.append("`unit_price` = ?,");
			}
			if(item.isModified("category")) {
				updateColumnSQL.append("`category` = ?,");
			}
			if(item.isModified("shippingCharge")) {
				updateColumnSQL.append("`shipping_charge` = ?,");
			}
			if(item.isModified("title")) {
				updateColumnSQL.append("`title` = ?,");
			}
			if(item.isModified("descrip")) {
				updateColumnSQL.append("`descrip` = ?,");
			}
			if(updateColumnSQL.length() > 0) {
				updateColumnSQL.deleteCharAt(updateColumnSQL.length() - 1);
			} else {
				return;
			}
		
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE `item` SET ");
			sql.append(updateColumnSQL.toString());
			sql.append(" WHERE ");
			sql.append(" `id` = ? AND");
			String sqlCode = sql.toString();
			if(sqlCode.endsWith("AND")) {
				sqlCode = sqlCode.substring(0, sqlCode.length() - 3);
			}
			ps = conn.prepareStatement(sqlCode);
		
		
			int index = 1;
			if(item.isModified("manufacturerId")) {
				ps.setInt(index++, item.getManufacturerId());
			}
			if(item.isModified("unitPrice")) {
				ps.setDouble(index++, item.getUnitPrice());
			}
			if(item.isModified("category")) {
				ps.setInt(index++, item.getCategory());
			}
			if(item.isModified("shippingCharge")) {
				ps.setDouble(index++, item.getShippingCharge());
			}
			if(item.isModified("title")) {
				ps.setString(index++, item.getTitle());
			}
			if(item.isModified("descrip")) {
				ps.setString(index++, item.getDescrip());
			}

			ps.setInt(index++, item.getId());
		
			ps.execute();
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw e;
		} finally {
			stmtClose(ps);
			connClose(conn);
		}

	}

	public Long countBycategory(int category) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT COUNT(*) FROM item WHERE category = ?");
					ps.setInt(1, category);
		
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

	public void insert(Item item) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("INSERT INTO `item`(`id`,`manufacturer_id`,`unit_price`,`category`,`shipping_charge`,`title`,`descrip`) VALUES(?,?,?,?,?,?,?)");
			ps.setInt(1, item.getId());
			ps.setInt(2, item.getManufacturerId());
			ps.setDouble(3, item.getUnitPrice());
			ps.setInt(4, item.getCategory());
			ps.setDouble(5, item.getShippingCharge());
			ps.setString(6, item.getTitle());
			ps.setString(7, item.getDescrip());
			ps.execute();
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw e;
		} finally {
			stmtClose(ps);
			connClose(conn);
		}
	}

	public Long count() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT COUNT(*) FROM item");
		
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

	public List<Item> list(int pageCode,int pageSize) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT * FROM item LIMIT ?,?");
					ps.setInt(1, pageCode);
					ps.setInt(2, pageSize);
		
			LinkedList<Item> returnVal = new LinkedList<Item>();
			for(ResultSet rs = ps.executeQuery(); rs.next(); ) {
				Item obj = new Item(
					rs.getInt("id"),
					rs.getInt("manufacturer_id"),
					rs.getInt("category"),
					rs.getInt("status"),
					rs.getString("title"),
					rs.getString("descrip"),
					rs.getDouble("unit_price"),
					rs.getDouble("shipping_charge")
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

	public Long countByManufacturer(int manufacturerId) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT COUNT(*) FROM item WHERE manufacturer_id = ?");
					ps.setInt(1, manufacturerId);
		
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


	
	public void delete(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("DELETE FROM item WHERE `id` = ?");
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

	public List<Item> listByCategory(int category,int pageCode,int pageSize) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT * FROM item WHERE category = ? LIMIT ?,?");
					ps.setInt(1, category);
					ps.setInt(2, pageCode);
					ps.setInt(3, pageSize);
		
			LinkedList<Item> returnVal = new LinkedList<Item>();
			for(ResultSet rs = ps.executeQuery(); rs.next(); ) {
				Item obj = new Item(
					rs.getInt("id"),
					rs.getInt("manufacturer_id"),
					rs.getInt("category"),
					rs.getInt("status"),
					rs.getString("title"),
					rs.getString("descrip"),
					rs.getDouble("unit_price"),
					rs.getDouble("shipping_charge")
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

	public Item get(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT * FROM item WHERE id = ?");
					ps.setInt(1, id);
		
			for(ResultSet rs = ps.executeQuery(); rs.next(); ) {
				Item obj = new Item(
					rs.getInt("id"),
					rs.getInt("manufacturer_id"),
					rs.getInt("category"),
					rs.getInt("status"),
					rs.getString("title"),
					rs.getString("descrip"),
					rs.getDouble("unit_price"),
					rs.getDouble("shipping_charge")
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

	public List<Item> listByManufacturer(int manufacturerId,int pageCode,int pageSize) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT * FROM item WHERE manufacturer_id = ? LIMIT ?,?");
					ps.setInt(1, manufacturerId);
					ps.setInt(2, pageCode);
					ps.setInt(3, pageSize);
		
			LinkedList<Item> returnVal = new LinkedList<Item>();
			for(ResultSet rs = ps.executeQuery(); rs.next(); ) {
				Item obj = new Item(
					rs.getInt("id"),
					rs.getInt("manufacturer_id"),
					rs.getInt("category"),
					rs.getInt("status"),
					rs.getString("title"),
					rs.getString("descrip"),
					rs.getDouble("unit_price"),
					rs.getDouble("shipping_charge")
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





}