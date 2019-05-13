package com.shopping.web.db.dto;
import java.sql.*;
import java.util.HashSet;

public class ItemImage {
	private HashSet<String> modifieds = new HashSet<String>();
	private int id;
	private int itemId;
	private String src;
	private String name;
	public ItemImage(int id) {
		this.id = id;
	}
	
	public ItemImage(int id,int itemId,String src,String name) {
		this.id = id;
		this.itemId = itemId;
		this.src = src;
		this.name = name;
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
	public void setItemId(int itemId) {
		if(itemId != this.itemId) {
			modifieds.add("itemId");
		}
	
	
		this.itemId = itemId;
	}
	public void setSrc(String src) {
		if(src != null && this.src != null) {
			if(!src.equals(this.src)) {
				modifieds.add("src");
			}
		}
		if(src == null && this.src != null) {
			modifieds.add("src");
		}
		if(src != null && this.src == null) {
			modifieds.add("src");
		}
	
	
		this.src = src;
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
	public int getId() {
		return this.id;
	}
	public int getItemId() {
		return this.itemId;
	}
	public String getSrc() {
		return this.src;
	}
	public String getName() {
		return this.name;
	}
}