package com.shopping.web.db.dto;
import java.sql.*;
import java.util.HashSet;

public class Catalog {
	private HashSet<String> modifieds = new HashSet<String>();
	private int id;
	private int parentId;
	private String name;
	private String type;
	private int sort;
	private String websiteId;
	private String pic;
	public Catalog(int id) {
		this.id = id;
	}
	
	public Catalog(int id,int parentId,String name,String type,int sort,String websiteId,String pic) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.type = type;
		this.sort = sort;
		this.websiteId = websiteId;
		this.pic = pic;
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
	public void setParentId(int parentId) {
		if(parentId != this.parentId) {
			modifieds.add("parentId");
		}
	
	
		this.parentId = parentId;
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
	public void setPic(String pic) {
		if(pic != null && this.pic != null) {
			if(!pic.equals(this.pic)) {
				modifieds.add("pic");
			}
		}
		if(pic == null && this.pic != null) {
			modifieds.add("pic");
		}
		if(pic != null && this.pic == null) {
			modifieds.add("pic");
		}
	
	
		this.pic = pic;
	}
	public void setType(String type) {
		if(type != null && this.type != null) {
			if(!type.equals(this.type)) {
				modifieds.add("type");
			}
		}
		if(type == null && this.type != null) {
			modifieds.add("type");
		}
		if(type != null && this.type == null) {
			modifieds.add("type");
		}
	
	
		this.type = type;
	}
	public void setSort(int sort) {
		if(sort != this.sort) {
			modifieds.add("sort");
		}
	
	
		this.sort = sort;
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
	public int getId() {
		return this.id;
	}
	public int getParentId() {
		return this.parentId;
	}
	public String getName() {
		return this.name;
	}
	public String getPic() {
		return this.pic;
	}
	public String getType() {
		return this.type;
	}
	public int getSort() {
		return this.sort;
	}
	public String getWebsiteId() {
		return this.websiteId;
	}
}