package com.shopping.web.db.dto;
import java.util.HashSet;

public class User {
	private HashSet<String> modifieds = new HashSet<String>();
	private int id;
	private String email;
	private String loginName;
	private String password;
	private String name;
	private String status;
	private String websiteId;
	public User(int id,String email,String loginName) {
		this.id = id;
		this.email = email;
		this.loginName = loginName;
	}
	
	public User(int id,String email,String loginName, String password,String name,String status,String websiteId) {
		this.id = id;
		this.email = email;
		this.loginName = loginName;
		this.password = password;
		this.name = name;
		this.status = status;
		this.websiteId = websiteId;
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
	public void setEmail(String email) {
		if(email != null && this.email != null) {
			if(!email.equals(this.email)) {
				modifieds.add("email");
			}
		}
		if(email == null && this.email != null) {
			modifieds.add("email");
		}
		if(email != null && this.email == null) {
			modifieds.add("email");
		}
	
	
		this.email = email;
	}
	
	public void setLoginName(String loginName) {
		if(email != null && this.loginName != null) {
			if(!email.equals(this.loginName)) {
				modifieds.add("loginName");
			}
		}
		if(email == null && this.loginName != null) {
			modifieds.add("loginName");
		}
		if(email != null && this.loginName == null) {
			modifieds.add("loginName");
		}
	
	
		this.loginName = loginName;
	}
	
	
	public void setPassword(String password) {
		if(password != null && this.password != null) {
			if(!password.equals(this.password)) {
				modifieds.add("password");
			}
		}
		if(password == null && this.password != null) {
			modifieds.add("password");
		}
		if(password != null && this.password == null) {
			modifieds.add("password");
		}
	
	
		this.password = password;
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
	public void setStatus(String status) {
		if(status != this.status) {
			modifieds.add("status");
		}
	
	
		this.status = status;
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
	public String getEmail() {
		return this.email;
	}
	public String getLoginName() {
		return this.loginName;
	}
	public String getPassword() {
		return this.password;
	}
	public String getName() {
		return this.name;
	}
	public String getStatus() {
		return this.status;
	}
	public String getWebsiteId() {
		return this.websiteId;
	}
}