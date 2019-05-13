package com.shopping.web.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import com.shopping.web.db.AbsBaseDao;
import com.shopping.web.db.dto.Image;

public class ImageDao extends AbsBaseDao implements IImageDao {


	public void insert(Image image) throws SQLException {
		Connection connection = getConnection();

		PreparedStatement ps = connection.prepareStatement("INSERT INTO `image`(`id`,`relational_id`,`type`) VALUES(?,?,?)");
		try {
			ps.setString(1, image.getId());
			ps.setInt(2, image.getRelationalId());
			ps.setInt(3, image.getType());
			ps.execute();
		} finally {
			ps.close();
		}

	}

	public void delete(String id) throws SQLException {
		Connection connection = getConnection();

		PreparedStatement ps = connection.prepareStatement("DELETE FROM `image` WHERE `id` = ?");
		try {
			ps.setString(1, id);

			ps.execute();
		} finally {
			ps.close();
		}
	}

	public void update(Image image) throws SQLException {
		Connection connection = getConnection();
		StringBuffer updateColumnSQL = new StringBuffer();
		if (image.isModified("relationalId")) {
			updateColumnSQL.append("`relational_id` = ?,");
		}
		if (image.isModified("type")) {
			updateColumnSQL.append("`type` = ?,");
		}
		if (updateColumnSQL.length() > 0) {
			updateColumnSQL.deleteCharAt(updateColumnSQL.length() - 1);
		} else {
			return;
		}

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE `image` SET ");
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
			if (image.isModified("relationalId")) {
				ps.setInt(index++, image.getRelationalId());
			}
			if (image.isModified("type")) {
				ps.setInt(index++, image.getType());
			}
			ps.setString(index++, image.getId());

			ps.execute();
		} finally {
			ps.close();
		}

	}

	@Override
	public List<Image> getImageListByID(int messageID, int type) throws SQLException {

		LinkedList<Image> returnVal = new LinkedList<Image>();
		Connection connection = getConnection();
		ResultSet rs = null;
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM `image` WHERE relational_id = ? AND type=?");
		try {
			ps.setInt(1, messageID);
			ps.setInt(2,type);
			rs = ps.executeQuery();
			while (rs.next()) {
				Image obj = new Image(rs.getString("id"), rs.getInt("relational_id"), rs.getInt("type"));
				returnVal.add(obj);

			}
		} finally {
			ps.close();
			if (rs != null)
				rs.close();
		}
		return returnVal;

	}

	@Override
	public String getOneImageByID(int id) throws SQLException{

		Connection connection = getConnection();
		ResultSet rs = null;
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM `image` WHERE relational_id = ? limit 0,1");
		try {
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				Image obj = new Image(rs.getString("id"), rs.getInt("relational_id"), rs.getInt("type"));
				return obj.getId();

			}
		} finally {
			ps.close();
			if (rs != null)
				rs.close();
		}
		return null;
	}


}