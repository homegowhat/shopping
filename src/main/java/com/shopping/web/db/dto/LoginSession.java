package com.shopping.web.db.dto;
import java.sql.*;
import java.util.HashSet;

public class LoginSession {
	private HashSet<String> modifieds = new HashSet<String>();
	private int id;
	private String username;
	private String name;
	private String value;
	public LoginSession(int id) {
		this.id = id;
	}
	
	public LoginSession(int id,String username,String name,String value) {
		this.id = id;
		this.username = username;
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
	public void setUsername(String username) {
		if(username != null && this.username != null) {
			if(!username.equals(this.username)) {
				modifieds.add("username");
			}
		}
		if(username == null && this.username != null) {
			modifieds.add("username");
		}
		if(username != null && this.username == null) {
			modifieds.add("username");
		}
	
	
		this.username = username;
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
	public void setValue(String value) {
		if(value != null && this.value != null) {
			if(!value.equals(this.value)) {
				modifieds.add("value");
			}
		}
		if(value == null && this.value != null) {
			modifieds.add("value");
		}
		if(value != null && this.value == null) {
			modifieds.add("value");
		}
	
	
		this.value = value;
	}
	public int getId() {
		return this.id;
	}
	public String getUsername() {
		return this.username;
	}
	public String getName() {
		return this.name;
	}
	public String getValue() {
		return this.value;
	}
}