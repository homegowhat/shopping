package com.shopping.web.db.dto;
import java.sql.*;
import java.util.HashSet;

public class Item {
	private HashSet<String> modifieds = new HashSet<String>();
	private int id;
	private int manufacturerId;
	private int category;
	private int status;
	private String title;
	private String descrip;
	private double unitPrice;
	private double shippingCharge;
	public Item(int id) {
		this.id = id;
	}
	
	public Item(int id,int manufacturerId,int category,int status,String title,String descrip,double unitPrice,double shippingCharge) {
		this.id = id;
		this.manufacturerId = manufacturerId;
		this.category = category;
		this.status = status;
		this.title = title;
		this.descrip = descrip;
		this.unitPrice = unitPrice;
		this.shippingCharge = shippingCharge;
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
	public void setManufacturerId(int manufacturerId) {
		if(manufacturerId != this.manufacturerId) {
			modifieds.add("manufacturerId");
		}
	
	
		this.manufacturerId = manufacturerId;
	}
	public void setCategory(int category) {
		if(category != this.category) {
			modifieds.add("category");
		}
	
	
		this.category = category;
	}
	public void setStatus(int status) {
		if(status != this.status) {
			modifieds.add("status");
		}
	
	
		this.status = status;
	}
	public void setTitle(String title) {
		if(title != null && this.title != null) {
			if(!title.equals(this.title)) {
				modifieds.add("title");
			}
		}
		if(title == null && this.title != null) {
			modifieds.add("title");
		}
		if(title != null && this.title == null) {
			modifieds.add("title");
		}
	
	
		this.title = title;
	}
	public void setDescrip(String descrip) {
		if(descrip != null && this.descrip != null) {
			if(!descrip.equals(this.descrip)) {
				modifieds.add("descrip");
			}
		}
		if(descrip == null && this.descrip != null) {
			modifieds.add("descrip");
		}
		if(descrip != null && this.descrip == null) {
			modifieds.add("descrip");
		}
	
	
		this.descrip = descrip;
	}
	public void setUnitPrice(double unitPrice) {
		if(unitPrice != this.unitPrice) {
			modifieds.add("unitPrice");
		}
	
	
		this.unitPrice = unitPrice;
	}
	public void setShippingCharge(double shippingCharge) {
		if(shippingCharge != this.shippingCharge) {
			modifieds.add("shippingCharge");
		}
	
	
		this.shippingCharge = shippingCharge;
	}
	public int getId() {
		return this.id;
	}
	public int getManufacturerId() {
		return this.manufacturerId;
	}
	public int getCategory() {
		return this.category;
	}
	public int getStatus() {
		return this.status;
	}
	public String getTitle() {
		return this.title;
	}
	public String getDescrip() {
		return this.descrip;
	}
	public double getUnitPrice() {
		return this.unitPrice;
	}
	public double getShippingCharge() {
		return this.shippingCharge;
	}
}