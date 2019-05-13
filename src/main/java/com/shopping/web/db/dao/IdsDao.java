package com.shopping.web.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.shopping.web.db.AbsBaseDao;
import com.shopping.web.db.dto.Ids;



public class IdsDao extends AbsBaseDao implements IIdsDao {


	public void insert(Ids ids) throws SQLException {
		Connection connection = getConnection();
		
		PreparedStatement ps = connection.prepareStatement("INSERT INTO `ids`(`id`,`website_id`,`name`,`value`) VALUES(?,?,?,?)");
		ps.setInt(1, ids.getId());
		ps.setString(2, ids.getWebsiteId());
		ps.setString(3, ids.getName());
		ps.setInt(4, ids.getValue());
		ps.execute();

	}

	public Ids get(String websiteId,String name) throws SQLException {
		Connection connection = getConnection();
		
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM ids WHERE website_id = ? AND name = ?");
					ps.setString(1, websiteId);
					ps.setString(2, name);
		
		for(ResultSet rs = ps.executeQuery(); rs.next(); ) {
			Ids obj = new Ids(
				rs.getInt("id"),
				rs.getString("website_id"),
				rs.getString("name"),
				rs.getInt("value")
			);
			return obj;
		}
		return null;
	}

	public void update(Ids ids) throws SQLException {
		Connection connection = getConnection();
		StringBuffer updateColumnSQL = new StringBuffer();
		if(ids.isModified("value")) {
			updateColumnSQL.append("`value` = ?,");
		}
		if(updateColumnSQL.length() > 0) {
			updateColumnSQL.deleteCharAt(updateColumnSQL.length() - 1);
		} else {
			return;
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE `ids` SET ");
		sql.append(updateColumnSQL.toString());
		sql.append(" WHERE ");
		sql.append(" `id` = ? AND");
		String sqlCode = sql.toString();
		if(sqlCode.endsWith("AND")) {
			sqlCode = sqlCode.substring(0, sqlCode.length() - 3);
		}
		PreparedStatement ps = connection.prepareStatement(sqlCode);
		
		
		int index = 1;
		if(ids.isModified("value")) {
			ps.setInt(index++, ids.getValue());
		}

		ps.setInt(index++, ids.getId());
		
		ps.execute();

	}



}