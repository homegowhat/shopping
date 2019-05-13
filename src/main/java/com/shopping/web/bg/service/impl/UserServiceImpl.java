package com.shopping.web.bg.service.impl;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.shopping.web.bg.bean.PagingBean;
import com.shopping.web.bg.service.abs.AbsUserService;
import com.shopping.web.db.dao.IIdsDao;
import com.shopping.web.db.dao.IUserDao;
import com.shopping.web.db.dao.IdsDao;
import com.shopping.web.db.dao.UserDao;
import com.shopping.web.db.dto.Ids;
import com.shopping.web.db.dto.User;

import os.rabbit.dao.IDaoManager;


/**
 * @author homegowhat 
 */
public class UserServiceImpl extends AbsUserService {
	private Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	@Qualifier("daoManager")
	private IDaoManager daoManager;

	@Override
	public User getUserByMail(String email) {
		IUserDao userDao = null;
		try {
			userDao = (IUserDao) daoManager.getDaoObject(IUserDao.class, UserDao.class);

			return userDao.getUserByEmail(email);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			return null;
		} finally {
			userDao = null;
		}

	}

	@Override
	protected void updateUser(User user) {
		IUserDao userDao = null;
		try {
			userDao = (IUserDao) daoManager.getDaoObject(IUserDao.class, UserDao.class);
			userDao.update(user);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			userDao = null;
		}
	}

	@Override
	public User getUserById(int id) {
		IUserDao userDao = null;
		try {
			userDao = (IUserDao) daoManager.getDaoObject(IUserDao.class, UserDao.class);
			return userDao.getUserById(id);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			return null;
		} finally {
			userDao = null;
		}

	}

	@Override
	protected int insertUser(String email,String loginName, String password, String name, String status) {
		IUserDao userDao = null;
		try {
			int id = getNewUserId( "shopping");
			userDao = (IUserDao) daoManager.getDaoObject(IUserDao.class, UserDao.class);
			User user = new User(id, email,loginName, password, name, status, null);
			userDao.insert(user);
			
			return id;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			return 0;
		} finally {
			userDao = null;
		}
	}
	
//	@Override
	protected void deleteUser(int id) {
//		IUserDao userDao = null;
//		try {
//			userDao = (IUserDao) daoManager.getDaoObject(IUserDao.class, UserDao.class);
//			userDao.
//			
//		} catch (SQLException e) {
//			logger.error(e.getMessage(), e);
//			e.printStackTrace();
//		} finally {
//			userDao = null;
//		}
	}
	
	private int getNewUserId(String websiteId) {
		try {
			IIdsDao idsDao = (IIdsDao) daoManager.getDaoObject(IIdsDao.class, IdsDao.class);
			Ids ids = idsDao.get(websiteId, "USER");
			if (ids == null) {
				idsDao.insert(new Ids(0, websiteId, "USER", 1));
				return 1;
			}
			int newId = ids.getValue() + 1;
			ids.setValue(newId);
			idsDao.update(ids);
			return newId;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	protected PagingBean<User> getUsersList(String status, int pageCode, int pageSize) {
		IUserDao userDao = null;
		PagingBean<User> pb = new PagingBean<User>();
		try {
			userDao = (IUserDao) daoManager.getDaoObject(IUserDao.class, UserDao.class);
			pageCode = (pageCode - 1) * pageSize;
			pb.setDataList(userDao.getUserList( status, pageCode, pageSize));
	
			pb.setTotalCount(userDao.getUserCount( status));
		
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			userDao = null;
		}
		return pb;
	}







}
