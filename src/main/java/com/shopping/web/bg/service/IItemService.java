package com.shopping.web.bg.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.shopping.web.db.dto.Catalog;
import com.shopping.web.db.dto.Image;
import com.shopping.web.db.dto.Item;
import com.shopping.web.db.dto.Manufacturer;


public interface IItemService {
	public static final String MATERIAL = "MATERIAL";
	public static final String PRODUCT = "PRODUCT";
	public static final String INVENTORY_PRODUCT = "INVENTORY_PRODUCT";
	public static final String INVENTORY_MATERIAL = "INVENTORY_MATERIAL";

	public static final String PRODUCT_CATALOG = "PRODUCT_CATALOG";

	List<Item> getProductList(Integer catalogId, int pageCode, int pageSize);

	List<Item> getItemList(Integer catalogId, Integer pageCode, int pageSize);

	void addItem(int catalogId, String productname, int price, int money, int manufacturerId, String content, String picDesc, String produectNum);

	void deleteItem(Integer id);
	
	void updateItem(Item inventory);
	
	Item getItem(int id);

	Long getProductCatalogCountByParentId(int i);

	Long getItemCountByCategory(Integer catalogId);
	

	Catalog getCategory(Integer catalogId);

	List<Catalog> getCatalogProductList(int i);

	void updateCategory(Catalog catalog);

	void deleteCategory(int id);

	void addCatalogProduct(Integer id, String name, Object object);

	void addItemPic(int parseInt, String value);

	void deleteItemPic(String id);

	List<Image> getImageByItemId(int parseInt);

	String getOneItemPic(int id);

	void deleteManufacturer(Integer id);

	List<Manufacturer> getManufacturerList(Integer pageCode, int i, String keyword);

	long getManufacturerCount(String keyword);

	Manufacturer getManufacturer(int parseInt);

	void addManufacturer(String name, String link);

	void editManufacturer(Manufacturer store);





	

	}
