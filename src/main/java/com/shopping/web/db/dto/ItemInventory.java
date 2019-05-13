package com.shopping.web.db.dto;
import java.sql.*;
import java.util.HashSet;

public class ItemInventory {
	private HashSet<String> modifieds = new HashSet<String>();
	private int id;
	private int inventory;
	private int itemId;
	public ItemInventory(int id) {
		this.id = id;
	}
	
	public ItemInventory(int id,int inventory,int itemId) {
		this.id = id;
		this.inventory = inventory;
		this.itemId = itemId;
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
	public void setInventory(int inventory) {
		if(inventory != this.inventory) {
			modifieds.add("inventory");
		}
	
	
		this.inventory = inventory;
	}
	public void setItemId(int itemId) {
		if(itemId != this.itemId) {
			modifieds.add("itemId");
		}
	
	
		this.itemId = itemId;
	}
	public int getId() {
		return this.id;
	}
	public int getInventory() {
		return this.inventory;
	}
	public int getItemId() {
		return this.itemId;
	}
}