package com.shopping.web.db.dto;
import java.sql.*;
import java.util.HashSet;

public class ItemPromotion {
	private HashSet<String> modifieds = new HashSet<String>();
	private int id;
	private int itemId;
	private double promotionPrice;
	private Date startDate;
	private Date endDate;
	public ItemPromotion(int id) {
		this.id = id;
	}
	
	public ItemPromotion(int id,int itemId,double promotionPrice,Date startDate,Date endDate) {
		this.id = id;
		this.itemId = itemId;
		this.promotionPrice = promotionPrice;
		this.startDate = startDate;
		this.endDate = endDate;
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
	public void setPromotionPrice(double promotionPrice) {
		if(promotionPrice != this.promotionPrice) {
			modifieds.add("promotionPrice");
		}
	
	
		this.promotionPrice = promotionPrice;
	}
	public void setStartDate(Date startDate) {
		if(startDate != null && this.startDate != null) {
			if(!startDate.equals(this.startDate)) {
				modifieds.add("startDate");
			}
		}
		if(startDate == null && this.startDate != null) {
			modifieds.add("startDate");
		}
		if(startDate != null && this.startDate == null) {
			modifieds.add("startDate");
		}
	
	
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate) {
		if(endDate != null && this.endDate != null) {
			if(!endDate.equals(this.endDate)) {
				modifieds.add("endDate");
			}
		}
		if(endDate == null && this.endDate != null) {
			modifieds.add("endDate");
		}
		if(endDate != null && this.endDate == null) {
			modifieds.add("endDate");
		}
	
	
		this.endDate = endDate;
	}
	public int getId() {
		return this.id;
	}
	public int getItemId() {
		return this.itemId;
	}
	public double getPromotionPrice() {
		return this.promotionPrice;
	}
	public Date getStartDate() {
		return this.startDate;
	}
	public Date getEndDate() {
		return this.endDate;
	}
}