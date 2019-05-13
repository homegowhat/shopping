package com.shopping.web.bg.service.impl;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.shopping.web.bg.service.IItemService;
import com.shopping.web.db.dao.CatalogDao;
import com.shopping.web.db.dao.ICatalogDao;
import com.shopping.web.db.dao.IImageDao;
import com.shopping.web.db.dao.IItemDao;
import com.shopping.web.db.dao.IManufacturerDao;
import com.shopping.web.db.dao.ImageDao;
import com.shopping.web.db.dao.ItemDao;
import com.shopping.web.db.dao.ManufacturerDao;
import com.shopping.web.db.dto.Catalog;
import com.shopping.web.db.dto.Image;
import com.shopping.web.db.dto.Item;
import com.shopping.web.db.dto.Manufacturer;

import os.rabbit.dao.IDaoManager;

public class ItemServiceImpl implements IItemService {
	private static Logger logger = Logger.getLogger(ItemServiceImpl.class);

	@Autowired
	@Qualifier("daoManager")
	private IDaoManager daoManager;

	@Override
	public List<Item> getProductList(Integer category, int pageCode, int pageSize) {
		try {
			pageCode = (pageCode - 1) * pageSize;
			IItemDao itemDao = (IItemDao) daoManager.getDaoObject(IItemDao.class, ItemDao.class);
			return itemDao.listByCategory(category, pageCode, pageSize);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return Collections.emptyList();
	}

	@Override
	public Long getItemCountByCategory(Integer category) {
		try {
			IItemDao itemDao = (IItemDao) daoManager.getDaoObject(IItemDao.class, ItemDao.class);
			return itemDao.countBycategory(category);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return 0l;
	}

	@Override
	public Catalog getCategory(Integer catalogId) {
		try {
			ICatalogDao catalogDao = (ICatalogDao) daoManager.getDaoObject(ICatalogDao.class, CatalogDao.class);
			return catalogDao.getCatalogById(catalogId);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<Item> getItemList(Integer category, Integer pageCode, int pageSize) {
		try {
			IItemDao itemDao = (IItemDao) daoManager.getDaoObject(IItemDao.class, ItemDao.class);
			pageCode = (pageCode - 1) * pageSize;
			return itemDao.listByCategory(category, pageCode, pageSize);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return Collections.emptyList();
	}

	@Override
	public Long getProductCatalogCountByParentId(int catalogId) {

		return getCatalogCount(catalogId, INVENTORY_PRODUCT);
	}

	@Override
	public List<Catalog> getCatalogProductList(int parentId) {
		return getCatlogList(parentId, INVENTORY_PRODUCT);
	}

	private List<Catalog> getCatlogList(int parentId, String type) {
		List<Catalog> list = null;
		try {
			ICatalogDao catalogDao = (ICatalogDao) daoManager.getDaoObject(ICatalogDao.class, CatalogDao.class);
			list = catalogDao.getCatalogByParentId(parentId, type);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (list == null)
			list = Collections.emptyList();
		return list;
	}

	private Long getCatalogCount(Integer catalogId, String type) {
		try {
			ICatalogDao catalogDao = (ICatalogDao) daoManager.getDaoObject(ICatalogDao.class, CatalogDao.class);
			Long count = catalogDao.getCatalogCountByParentId(catalogId, type);
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0l;
	}

	@Override
	public void deleteItem(Integer id) {
		try {
			IItemDao itemDao = (IItemDao) daoManager.getDaoObject(IItemDao.class, ItemDao.class);
			itemDao.delete(id);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void updateCategory(Catalog catalog) {
		try {
			ICatalogDao catalogDao = (ICatalogDao) daoManager.getDaoObject(ICatalogDao.class, CatalogDao.class);
			catalogDao.update(catalog);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void deleteCategory(int id) {
		try {
			IItemDao itemDao = (IItemDao) daoManager.getDaoObject(IItemDao.class, ItemDao.class);
			long pCount = itemDao.countBycategory(id);
			if (pCount == 0) {

				ICatalogDao catalogDao = (ICatalogDao) daoManager.getDaoObject(ICatalogDao.class, CatalogDao.class);
				catalogDao.delete(id);

			} else {
				throw new Exception("該分類下有料件，無法刪除");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void addCatalogProduct(Integer id, String name, Object object) {
		try {
			ICatalogDao catalogDao = (ICatalogDao) daoManager.getDaoObject(ICatalogDao.class, CatalogDao.class);
			Catalog catalog = new Catalog(0, id, name, INVENTORY_PRODUCT, 0, null, null);
			catalogDao.insert(catalog);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void addItem(int category, String title, int unitPrice, int shippingCharge, int manufacturerId, String descrip, String picDesc, String produectNum) {
		try {
			Item item = new Item(0,manufacturerId, category, 0, title, descrip, unitPrice, shippingCharge);
			IItemDao itemDao = (IItemDao) daoManager.getDaoObject(IItemDao.class, ItemDao.class);
			itemDao.insert(item);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void updateItem(Item item) {
		try {
			IItemDao itemDao = (IItemDao) daoManager.getDaoObject(IItemDao.class, ItemDao.class);
			itemDao.update(item);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public Item getItem(int id) {
		try {
			IItemDao itemDao = (IItemDao) daoManager.getDaoObject(IItemDao.class, ItemDao.class);
			return itemDao.get(id);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public void addItemPic(int id, String value) {
		try {
			IImageDao imageDao = (IImageDao) daoManager.getDaoObject(IImageDao.class, ImageDao.class);
			// IImageDao imageDao = this.imageDao();
			imageDao.insert(new Image(value, id, 0));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	@Override
	public void deleteItemPic(String id) {
		try {
			IImageDao imageDao = (IImageDao) daoManager.getDaoObject(IImageDao.class, ImageDao.class);

			imageDao.delete(id);


		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	@Override
	public List<Image> getImageByItemId(int id) {
		try {
			IImageDao imageDao = (IImageDao) daoManager.getDaoObject(IImageDao.class, ImageDao.class);
			return imageDao.getImageListByID(id, 0);
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public String getOneItemPic(int id) {
		try {
			IImageDao imageDao = (IImageDao) daoManager.getDaoObject(IImageDao.class, ImageDao.class);
			return imageDao.getOneImageByID(id);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void deleteManufacturer(Integer id) {
		try {
			IManufacturerDao manufacturerDaoDao = (IManufacturerDao) daoManager.getDaoObject(IManufacturerDao.class, ManufacturerDao.class);
			manufacturerDaoDao.delete(id);
		} catch (Exception e) {
			
		}
	}

	@Override
	public List<Manufacturer> getManufacturerList(Integer pageCode, int pageSize, String keyword) {
		try {
			pageCode = (pageCode - 1) * pageSize;
			IManufacturerDao manufacturerDaoDao = (IManufacturerDao) daoManager.getDaoObject(IManufacturerDao.class, ManufacturerDao.class);
			if(keyword == null || keyword.equals(""))
				return manufacturerDaoDao.list(pageCode, pageSize);
			else
				return manufacturerDaoDao.listByKey("%"+keyword+"%", pageCode, pageSize);
			
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public long getManufacturerCount(String keyword) {
		try {
			IManufacturerDao manufacturerDaoDao = (IManufacturerDao) daoManager.getDaoObject(IManufacturerDao.class, ManufacturerDao.class);
			if(keyword == null || keyword.equals(""))
				return manufacturerDaoDao.count();
			else
				return manufacturerDaoDao.countByKey(keyword);
			
		} catch (Exception e) {
		}
		return 0;
	}

	@Override
	public Manufacturer getManufacturer(int id) {
		try {
			IManufacturerDao manufacturerDaoDao = (IManufacturerDao) daoManager.getDaoObject(IManufacturerDao.class, ManufacturerDao.class);
				return manufacturerDaoDao.get(id);
			
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public void addManufacturer(String name, String link) {
		try {
			IManufacturerDao manufacturerDaoDao = (IManufacturerDao) daoManager.getDaoObject(IManufacturerDao.class, ManufacturerDao.class);
			Manufacturer m = new Manufacturer(0, name, link);
			manufacturerDaoDao.insert(m);
			
		} catch (Exception e) {
		}
	}

	@Override
	public void editManufacturer(Manufacturer m) {
		try {
			IManufacturerDao manufacturerDaoDao = (IManufacturerDao) daoManager.getDaoObject(IManufacturerDao.class, ManufacturerDao.class);
			manufacturerDaoDao.update(m);
			
		} catch (Exception e) {
		}
	}

}
