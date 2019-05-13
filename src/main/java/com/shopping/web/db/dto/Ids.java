package com.shopping.web.db.dto;
import java.sql.*;
import java.util.HashSet;

public class Ids {
	private HashSet<String> modifieds = new HashSet<String>();
	private int id;
	private String websiteId;
	private String name;
	private int value;
	public Ids(int id) {
		this.id = id;
	}
	
	public Ids(int id,String websiteId,String name,int value) {
		this.id = id;
		this.websiteId = websiteId;
		this.name = name;
		this.value = value;
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
	public void setWebsiteId(String websiteId) {
		if(websiteId != null && this.websiteId != null) {
			if(!websiteId.equals(this.websiteId)) {
				modifieds.add("websiteId");
			}
		}
		if(websiteId == null && this.websiteId != null) {
			modifieds.add("websiteId");
		}
		if(websiteId != null && this.websiteId == null) {
			modifieds.add("websiteId");
		}
	
	
		this.websiteId = websiteId;
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
	public void setValue(int value) {
		if(value != this.value) {
			modifieds.add("value");
		}
	
	
		this.value = value;
	}
	public int getId() {
		return this.id;
	}
	public String getWebsiteId() {
		return this.websiteId;
	}
	public String getName() {
		return this.name;
	}
	public int getValue() {
		return this.value;
	}
}