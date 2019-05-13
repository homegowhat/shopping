package com.shopping.web.bg.service.abs;

import org.apache.log4j.Logger;

import com.shopping.web.bg.bean.PagingBean;
import com.shopping.web.bg.bean.StringUtil;
import com.shopping.web.bg.service.IUserService;
import com.shopping.web.db.dto.User;


/**
 * @author homegowhat db table
 */
public abstract class AbsUserService implements IUserService {
	private Logger logger = Logger.getLogger(AbsUserService.class);

	public AbsUserService() {
	}

	public static void main(String[] args) {
//		System.out.println("p = "+StringUtil.strToMD5("1234"));
	}


	protected abstract void updateUser(User user);



	protected abstract int insertUser(String email, String loginName, String password, String name, String status);

	protected abstract PagingBean<User> getUsersList( String status, int pageCode, int pageSize);

	
	@Override
	public int addUser(String email, String loginName, String password, String name, String status) {
		String pwd = StringUtil.strToMD5(password);
		return insertUser(email, loginName, pwd, name, status);
	}

	@Override
	public void editUser(User user) {
		updateUser(user);
	}

	@Override
	public void editPassword(User user, String oldPwd, String newPwd) throws Exception {
		user.setPassword(StringUtil.strToMD5(newPwd));
		editUser(user);

	}

	

	@Override
	public User getUserByLoginName(String email) {
		return getUserByMail(email);
	}

	@Override
	public PagingBean<User> getUserList(String status, Integer pageCode, int pageSize) {
		return getUsersList( status, pageCode, pageSize);
	}

}
