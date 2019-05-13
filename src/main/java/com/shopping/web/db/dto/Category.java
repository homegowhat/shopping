package com.shopping.web.db.dto;
import java.sql.*;
import java.util.HashSet;

public class Category {
	private HashSet<String> modifieds = new HashSet<String>();
	private int id;
	private String name;
	private int parentId;
	public Category(int id) {
		this.id = id;
	}
	
	public Category(int id,String name,int parentId) {
		this.id = id;
		this.name = name;
		this.parentId = parentId;
	}
	

	public boolean isModified(String fieldName) {
		return modifieds.contains(fieldName);
	}
	public void setId(int id) {
		if(id != this.id) {
			modifieds.add("id");
		}
	
	
		this.id = id;
	}
	public void setName(String name) {
		if(name != null && this.name != null) {
			if(!name.equals(this.name)) {
				modifieds.add("name");
			}
		}
		if(name == null && this.name != null) {
			modifieds.add("name");
		}
		if(name != null && this.name == null) {
			modifieds.add("name");
		}
	
	
		this.name = name;
	}
	public void setParentId(int parentId) {
		if(parentId != this.parentId) {
			modifieds.add("parentId");
		}
	
	
		this.parentId = parentId;
	}
	public int getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public int getParentId() {
		return this.parentId;
	}
}