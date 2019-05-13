package com.shopping.web.db.dto;
import java.sql.*;
import java.util.HashSet;

public class Manufacturer {
	private HashSet<String> modifieds = new HashSet<String>();
	private int id;
	private String name;
	private String link;
	public Manufacturer(int id) {
		this.id = id;
	}
	
	public Manufacturer(int id,String name,String link) {
		this.id = id;
		this.name = name;
		this.link = link;
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
	public void setLink(String link) {
		if(link != null && this.link != null) {
			if(!link.equals(this.link)) {
				modifieds.add("link");
			}
		}
		if(link == null && this.link != null) {
			modifieds.add("link");
		}
		if(link != null && this.link == null) {
			modifieds.add("link");
		}
	
	
		this.link = link;
	}
	public int getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public String getLink() {
		return this.link;
	}
}