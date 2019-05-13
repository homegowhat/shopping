package com.shopping.web.db.dto;
import java.util.HashSet;

public class Image {
	private HashSet<String> modifieds = new HashSet<String>();
	private String id;
	private int relationalId;
	private int type;
	public Image(String id) {
		this.id = id;
	}
	
	public Image(String id,int relationalId, int type) {
		this.id = id;
		this.relationalId = relationalId;
		this.type = type;
	}
	

	public boolean isModified(String fieldName) {
		return modifieds.contains(fieldName);
	}
	public void setId(String id) {
		if(id != null && this.id != null) {
			if(!id.equals(this.id)) {
				modifieds.add("id");
			}
		}
		if(id == null && this.id != null) {
			modifieds.add("id");
		}
		if(id != null && this.id == null) {
			modifieds.add("id");
		}
	
	
		this.id = id;
	}
	public void setRelationalId(int relationalId) {
		if(relationalId != this.relationalId) {
			modifieds.add("relationalId");
		}
	
	
		this.relationalId = relationalId;
	}
	public void setType(int type) {
		if(type != this.type) {
			modifieds.add("type");
		}
	
	
		this.type = type;
	}
	
	public String getId() {
		return this.id;
	}
	public int getRelationalId() {
		return this.relationalId;
	}
	
	public int getType() {
		return this.type;
	}
}