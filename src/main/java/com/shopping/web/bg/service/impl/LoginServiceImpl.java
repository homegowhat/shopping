package com.shopping.web.bg.service.impl;

import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.shopping.web.bg.service.IUserService;
import com.shopping.web.bg.service.abs.AbsLoginService;
import com.shopping.web.db.dao.IUserDao;
import com.shopping.web.db.dao.UserDao;
import com.shopping.web.db.dto.LoginSession;
import com.shopping.web.db.dto.User;

import os.rabbit.dao.IDaoManager;
import os.rabbit.dao.IDaoTransaction;


/**
 * @author homegowhat db table
 *         user、outside_user、registration、website、websiteMail
 */
public class LoginServiceImpl extends AbsLoginService {
	private Logger logger = Logger.getLogger(LoginServiceImpl.class);

	@Autowired
	@Qualifier("daoManager")
	private IDaoManager daoManager;

	@Override
	protected void insertUser(String email, String loginName, String password, String name, String status, String websiteId) {
		IUserDao userDao = null;
		try {
			userDao = (IUserDao) daoManager.getDaoObject(IUserDao.class, UserDao.class);
			User user = new User(0, email,loginName, password, name, status, websiteId);
			userDao.insert(user);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			userDao = null;
		}
	}

//	@Override
//	protected void addMailRegistrationUser(String arg, String email, String loginName, String password, String name, String status, String websiteId) {
//		IDaoTransaction trx = null;
//		IUserDao userDao = null;
//		IRegistrationDao registrationDao = null;
//		try {
//			trx = daoManager.beginTransaction();
//
//			userDao = (IUserDao) trx.getDaoObject(UserDao.class);
//
//			User user = new User(0, email,loginName, password, name, IUserService.LOCKED, websiteId);
//			userDao.insert(user);
//
//			user = userDao.getUserByEmail(email, websiteId);
//			// 儲存會員註冊記錄，以便會員開通帳號
//			registrationDao = (IRegistrationDao) trx.getDaoObject(RegistrationDao.class);
//			registrationDao.insert(new Registration(0, user.getId(), arg, new Timestamp(System.currentTimeMillis())));
//			trx.commit();
//
//		} catch (SQLException e) {
//			logger.error(e.getMessage(), e);
//			e.printStackTrace();
//			try {
//				trx.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//		} finally {
//			trx = null;
//			registrationDao = null;
//			userDao = null;
//		}
//	}
//
//	@Override
//	protected void opensRegistrationUser(String arg) throws Exception {
//		IDaoTransaction trx = null;
//		IRegistrationDao registrationDao = null;
//		IUserDao userDao = null;
//		try {
//			trx = daoManager.beginTransaction();
//			registrationDao = (IRegistrationDao) trx.getDaoObject(RegistrationDao.class);
//			Registration registration = registrationDao.getRegistration(arg);
//			if (registration != null) {
//				userDao = (IUserDao) trx.getDaoObject(UserDao.class);
//				User user = userDao.getUserById(registration.getUserId());
//				user.setStatus(IUserService.NORMAL);
//				userDao.update(user);
//				registrationDao.delete(registration.getId());
//				trx.commit();
//			}
//		} catch (SQLException e) {
//			logger.error(e.getMessage(), e);
//			e.printStackTrace();
//			try {
//				trx.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//			throw new Exception(arg + " opensRegistrationUser fail !!");
//		} finally {
//			trx = null;
//			registrationDao = null;
//			userDao = null;
//		}
//	}
//
//	
//
//	@Override
//	protected OutsideUser getOutsideUserEntity(String outsideId, String type, String websiteId) {
//		IOutsideUserDao outsideUserDao = null;
//		try {
//			outsideUserDao = (IOutsideUserDao) daoManager.getDaoObject(IOutsideUserDao.class, OutsideUserDao.class);
//			return outsideUserDao.getUserId(outsideId, type, websiteId);
//
//		} catch (SQLException e) {
//			logger.error(e.getMessage(), e);
//			e.printStackTrace();
//			return null;
//		} finally {
//			outsideUserDao = null;
//		}
//
//	}
//
//	@Override
//	protected OutsideUser getOutsideUserEntityByUserId(int userId, String type, String websiteId) {
//		IOutsideUserDao outsideUserDao = null;
//		try {
//			outsideUserDao = (IOutsideUserDao) daoManager.getDaoObject(IOutsideUserDao.class, OutsideUserDao.class);
//			return outsideUserDao.getOutsideUser(userId, type, websiteId);
//
//		} catch (SQLException e) {
//			logger.error(e.getMessage(), e);
//			e.printStackTrace();
//			return null;
//		} finally {
//			outsideUserDao = null;
//		}
//
//	}
//
//	@Override
//	protected void insertOutsideUser(String outsideId, String email,String loginName, String type, int userId, String websiteId) {
//		IOutsideUserDao outsideUserDao = null;
//		try {
//			outsideUserDao = (IOutsideUserDao) daoManager.getDaoObject(IOutsideUserDao.class, OutsideUserDao.class);
//			OutsideUser outsideUser = getOutsideUser(outsideId, type, websiteId);
//			if (outsideUser == null) {
//				outsideUser = new OutsideUser(0, outsideId, email, type, userId, websiteId);
//				outsideUserDao.insert(outsideUser);
//				
//			}
//
//		} catch (SQLException e) {
//			logger.error(e.getMessage(), e);
//			e.printStackTrace();
//		} finally {
//			outsideUserDao = null;
//		}
//	}
//
//	@Override
//	protected long getLoginErrorCount(String websiteId, String ip) {
//		ILoginRecordDao loginRecordDao = null;
//		try {
//			loginRecordDao = (ILoginRecordDao) daoManager.getDaoObject(ILoginRecordDao.class, LoginRecordDao.class);
//			long now = System.currentTimeMillis();
//			Timestamp startTime = new Timestamp(now);
//			Timestamp endTime = new Timestamp(now - 1000*60*30);
//			return loginRecordDao.getLoginErrorRecordCount(startTime, endTime, ip, websiteId, false);
//
//		} catch (SQLException e) {
//			logger.error(e.getMessage(), e);
//			e.printStackTrace();
//			return 0;
//		} finally {
//			loginRecordDao = null;
//		}
//	}
//
//	@Override
//	protected void insertloginRecord(String websiteId, String ip, String useAccount, boolean isSuccess) {
//		ILoginRecordDao loginRecordDao = null;
//		try {
//			loginRecordDao = (ILoginRecordDao) daoManager.getDaoObject(ILoginRecordDao.class, LoginRecordDao.class);
//			LoginRecord loginRecord = new LoginRecord(0, new Timestamp(System.currentTimeMillis()), ip,isSuccess, websiteId, useAccount);
//			loginRecordDao.insert(loginRecord);
//		} catch (SQLException e) {
//			logger.error(e.getMessage(), e);
//			e.printStackTrace();
//		} finally {
//			loginRecordDao = null;
//		}
//	}
//
//	@Override
//	protected void setLoginSessionToDB(String websiteId, String username, String uuid) {
//		try {
//			ILoginSessionDao loginSessionDao = (ILoginSessionDao) daoManager.getDaoObject(ILoginSessionDao.class, LoginSessionDao.class);
//			LoginSession loginSession = loginSessionDao.getByUsername(username);
//			if (loginSession != null) {
//				loginSession.setValue(uuid);
//				loginSessionDao.update(loginSession);
//			} else {
//				IUserDao userDao = (IUserDao) daoManager.getDaoObject(IUserDao.class, UserDao.class);
//				User user = userDao.getUserByEmail(username, websiteId);
////				System.out.println("usr = "+user);
//				loginSessionDao.insert(new LoginSession(0, username, user.getName(), uuid));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	@Override
//	protected LoginSession getLoginSession(String value) {
//		try {
//			ILoginSessionDao loginSession = (ILoginSessionDao) daoManager.getDaoObject(ILoginSessionDao.class, LoginSessionDao.class);
//			return loginSession.getByValue(value);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public String getRealPath() {
//		return context.getRealPath("/");
//	}

@Autowired
private ServletContext context;


@Override
public Integer getLoginId(HttpServletRequest request) {
	return (Integer)request.getSession().getAttribute("id");
}


}
