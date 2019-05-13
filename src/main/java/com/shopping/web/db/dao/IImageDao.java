package com.shopping.web.db.dao;

import java.sql.SQLException;
import java.util.List;

import com.shopping.web.db.dto.Image;


public interface IImageDao {
	public void insert(Image image) throws SQLException;

	
	public void delete(String id) throws SQLException;
	public void update(Image image) throws SQLException;

	public List<Image> getImageListByID(int messageID, int type) throws SQLException;


	public String getOneImageByID(int id)throws SQLException;

}