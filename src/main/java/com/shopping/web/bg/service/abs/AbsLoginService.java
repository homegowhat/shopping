package com.shopping.web.bg.service.abs;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.kuroshio.web.module.login.service.listener.ILoginListener;
import com.shopping.web.bg.bean.StringUtil;
import com.shopping.web.bg.service.ILoginService;
import com.shopping.web.bg.service.IUserService;
import com.shopping.web.db.dto.LoginSession;
import com.shopping.web.db.dto.User;


/**
 * @author homegowhat
 */
public abstract class AbsLoginService implements ILoginService {
	private Logger logger = Logger.getLogger(AbsLoginService.class);

	@Autowired
	@Qualifier("userService")
	private IUserService userService;

//	@Autowired
//	@Qualifier("oldwebsiteService")
//	private IWebsiteService oldWebsiteService;

//	@Autowired
//	@Qualifier("websiteService")
//	private com.kuroshio.web.module.website.service.IWebsiteService websiteService;

//	@Autowired
//	@Qualifier("mailDispatcherService")
//	private IMailDispatcherService mailService;
//
//	private HashSet<ILoginListener> loginListenerSet = new HashSet<ILoginListener>();
//	private Map<String, String> wbsLoginMap = Collections.synchronizedMap(new HashMap<String, String>());
//	private Map<String, LoginSession> wbsUserMap = Collections.synchronizedMap(new HashMap<String, LoginSession>());

	public AbsLoginService() {
	}

	public static void main(String[] args) {
		// Calendar c = Calendar.getInstance();
		// long nowTime = c.getTime().getTime();
		// System.out.println("nowTime = " + nowTime);
//		System.out.println(StringUtil.strToMD5("1234"));
	}

	protected abstract void insertUser(String email, String loginName, String password, String name, String status, String websiteId);

//	protected abstract void addMailRegistrationUser(String arg, String email, String loginName, String password, String name, String status, String websiteId);

//	protected abstract void opensRegistrationUser(String arg) throws Exception;

//	protected abstract OutsideUser getOutsideUserEntity(String outsideId, String type, String websiteId);
//
//	protected abstract OutsideUser getOutsideUserEntityByUserId(int userId, String type, String websiteId);
//
//	protected abstract void insertOutsideUser(String outsideId, String email, String loginName, String type, int userId, String websiteId);

//	protected abstract long getLoginErrorCount(String websiteId, String ip);

//	protected abstract void insertloginRecord(String websiteId, String ip, String loginName, boolean isSuccess);

//	protected abstract void setLoginSessionToDB(String websiteId, String username, String uuid);

//	protected abstract LoginSession getLoginSession(String value);

//	private void setWbsLoginNumber(String websiteId, String username) {
//		String uuid = UUID.randomUUID().toString();
//		wbsLoginMap.put(username, uuid);
//		setLoginSessionToDB(websiteId, username, uuid);
//	}

//	@Override
//	public String getWbsNumber(HttpServletRequest request) {
//		User user = getLoginUser(request);
//		return wbsLoginMap.get(user.getLoginName());
//	}

//	@Override
//	public LoginSession getWbsUser(String value) {
////		System.out.println("getWbsUser = " + value);
//		LoginSession loginSession = wbsUserMap.get(value);
//		if (loginSession != null) {
////			System.out.println("l s not null ........ ");
//			return loginSession;
//		} else {
////			System.out.println("l s is null ........ ");
//			loginSession = getLoginSession(value);
////			System.out.println("get l s ........ " + loginSession);
//			wbsUserMap.put(value, loginSession);
//			return loginSession;
//		}
//	}

	@Override
	public boolean isLogin(HttpServletRequest request) {

		return getLoginId(request) != null;
	}

	@Override
	public User getLoginUser(HttpServletRequest request) {
		Integer id = null;
		id = getLoginId(request);
		if (id != null)
			return userService.getUserById(id);
		else
			return null;
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public String getLoginId(HttpServletRequest request) {
////		String id = null;
////		if (request.getSession().getAttribute("id") != null) {
////			Map<String, String> map = (Map<String, String>) request.getSession().getAttribute("id");
////			id = map.get(websiteService.getWebsiteId(request));
////		}
//		String websiteId = websiteService.getWebsiteId(request);
//		return (String)request.getSession().getAttribute(websiteId);
//	}

//	public long getCautionLevel(HttpServletRequest request) {
//		String ip = request.getRemoteAddr();
//		return getLoginErrorCount(websiteService.getWebsiteId(request), ip);
//	}

	@SuppressWarnings("unchecked")
	@Override
	public void login(HttpServletRequest request, String account, String password) throws Exception {
		account = account.toLowerCase();
		password = password.toLowerCase();
		String ip = request.getRemoteAddr();
		User user = userService.getUserByLoginName(account);

		if (user == null) {
			throw new Exception("無此帳號或密碼錯誤!!");
		}
		if (user.getLoginName().equals(account)
		 && user.getPassword().equals(
		 StringUtil.strToMD5(password))
		) {
//			if (user.getStatus().equals(IUserService.LOCKED)) {
//				insertloginRecord(websiteId, ip, account, false);
//				throw new Exception("該帳號尚未開通!!");
//			}

//			if (user.getStatus().equals(IUserService.BLOCK)) {
//				insertloginRecord(websiteId, ip, account, false);
//				throw new Exception("該帳號已被封鎖!!");
//			}

//			Map<String, String> idMap = (Map<String, String>) request.getSession().getAttribute("id");
//			if (idMap == null) {
//				idMap = new HashMap<String, String>();
//			}
//			idMap.put(websiteId, String.valueOf(user.getId()));

//			insertloginRecord(websiteId, ip, account, true);
			request.getSession().setAttribute("id", user.getId());
//			setWbsLoginNumber(websiteId, account);

//			for (ILoginListener listener : loginListenerSet) {
//				listener.login(request, websiteId, user.getId());
//			}

		} else {
//			long longError = getCautionLevel(request);
//			if (longError < 10) {
//				if (longError >= 10) {
//					user.setStatus(IUserService.BLOCK);
//				}
//				throw new LoginErrorCountException("登入錯誤次數已經" + longError + "次", longError);
//
//			} else {
//				throw new LoginErrorCountException("登入錯誤次數已經" + longError + "次，已封鎖!~", longError);
//
//			}
		}

	}
	
//	@Override
//	public void loginByCard(HttpServletRequest request, User user) throws Exception {
//		String websiteId = websiteService.getWebsiteId(request);
//		String ip = request.getRemoteAddr();
//
//		if (user == null) {
//			throw new Exception("無此帳號或密碼錯誤!!");
//		}else {
//			String account = user.getLoginName();
//			if (user.getStatus().equals(IUserService.LOCKED)) {
//				insertloginRecord(websiteId, ip, account, false);
//				throw new Exception("該帳號尚未開通!!");
//			}
//
//			if (user.getStatus().equals(IUserService.BLOCK)) {
//				insertloginRecord(websiteId, ip, account, false);
//				throw new Exception("該帳號已被封鎖!!");
//			}
//
//
//			insertloginRecord(websiteId, ip, account, true);
//			request.getSession().setAttribute(websiteId, String.valueOf(user.getId()));
//			setWbsLoginNumber(websiteId, account);
//
//			for (ILoginListener listener : loginListenerSet) {
//				listener.login(request, websiteId, user.getId());
//			}
//
//		} 
//
//	}

	@SuppressWarnings("unchecked")
	@Override
	public void logout(HttpServletRequest request) {
		HttpSession session = request.getSession();

//		for (ILoginListener listener : loginListenerSet) {
//			listener.logout(request);
//		}
//		Map<String, Integer> map = (Map<String, Integer>) session.getAttribute("id");
//		if (map != null) {
//			map.remove(websiteService.getWebsiteId(request));
//			if (map.size() == 0)
//				session.removeAttribute("id");
//			session.invalidate();
//		}
//		String websiteId = websiteService.getWebsiteId(request);
//		session.removeAttribute(websiteId);
		session.invalidate();
	}

//	@Override
//	public void addLockedUser(HttpServletRequest request, String email, String loginName, String password, String name, String companyName, String domain) {
//		String websiteId = websiteService.getWebsiteId(request);
//		password = password.toLowerCase();
//		String pwd = StringUtil.strToMD5(password);
//		String arg = newRegistrationArg();
//		addMailRegistrationUser(arg, email, loginName, pwd, name, IUserService.LOCKED, websiteId);
//
//		StringBuffer strBuf = new StringBuffer();
//		strBuf.append("您好!<br />");
//		strBuf.append("感謝您加入'");
//		strBuf.append(companyName);
//		strBuf.append("'會員，<br />");
//		strBuf.append("若您沒有註冊請不要理會該信件。<br />");
//		strBuf.append("<a href=\"http://" + domain + "/open.rbt?arg=" + arg + "\">::::點此開通此帳號::::</a><br />");
//		strBuf.append(companyName);
//		strBuf.append("敬上<br />");
//		strBuf.append("p.s.此信箱由系統寄發請勿回復");
//
//		WebsiteMail wm = oldWebsiteService.getWebsiteMail(request, websiteId);
//
//		String webEmail = wm.getEmail();
//		String webEmailPass = wm.getPassword();
//
//		// 寄發驗證email
//
//		mailService.send(webEmail, webEmailPass, email, "'" + companyName + "'會員註冊開通信件", strBuf.toString(), null);
//
//	}

	// email點下開通的會員
//	@Override
//	public String openRegistrationUser(String arg) {
//		String msg = "會員帳號開通成功!!";
//		try {
//			opensRegistrationUser(arg);
//		} catch (Exception e) {
//			msg = "會員帳號開通失敗!!";
//			e.printStackTrace();
//		}
//
//		return msg;
//	}

//	@Override
//	public void getNewPwdMail(HttpServletRequest request, String email) {
//		String websiteId = websiteService.getWebsiteId(request);
//		try {
//			User user = userService.getUserByLoginName(email, websiteId);
//			String newPwd = StringUtil.getRandomStr(6);
//			newPwd = newPwd.toLowerCase();
//			user.setPassword(StringUtil.strToMD5(newPwd));
//			userService.editUser(user);
//
//			Website website = oldWebsiteService.getWebsite(websiteId);
//			String companyName = website.getSiteName();
//			String domain = website.getDomain();
//			// TODO 這邊要改成velocity
//			StringBuffer strBuf = new StringBuffer();
//			strBuf.append("您好!<br />");
//			strBuf.append("您忘記在'");
//			strBuf.append(companyName);
//			strBuf.append("'會員的密碼，已由系統隨機重新產生，<br />新密碼為:");
//			strBuf.append(newPwd);
//			strBuf.append("<br />請儘速登入，並修改成為您好記的密碼。<br /><a href=\"http://" + domain + "/index.rbt\">" + companyName + "</a><br />");
//			strBuf.append("登入" + companyName);
//			strBuf.append("敬上<br />");
//			strBuf.append("p.s.此信箱由系統寄發請勿回復");
//
//			// 寄發新密碼email
//			// mailService.sendMail(email, "'" + companyName + "'會員登入密碼重新產生信件",
//			// strBuf.toString());
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			e.printStackTrace();
//		}
//
//	}

//	@Override
//	public OutsideUser getOutsideUser(HttpServletRequest request, String outsideId, String type) {
//
//		return getOutsideUserEntity(outsideId, type, websiteService.getWebsiteId(request));
//	}
	
//	public OutsideUser getOutsideUser(String outsideId, String type, String websiteId) {
//
//		return getOutsideUserEntity(outsideId, type, websiteId);
//	}

//	@Override
//	public OutsideUser getOutsideUserByUserId(HttpServletRequest request, int id, String type) {
//		return getOutsideUserEntityByUserId(id, type, websiteService.getWebsiteId(request));
//	}

//	@Override
//	public void addOutsideUser(HttpServletRequest request, int userId, String outsideId, String email, String loginName, String name, String type) throws SQLException {
//		// User user = getUserByEmail(email);
//		// if (user == null) {
//		// int r = (int) (Math.random() * 10000);
//		// addUser(email, strToMD5(String.valueOf(r)), name, 1);
//		// }
//		// user = getUserByEmail(email);
//
//		// if (user != null) {
//
//		insertOutsideUser(outsideId, email, loginName, type, userId, websiteService.getWebsiteId(request));
//		// }
//	}

//	@Override
//	public boolean isBeanLogin(HttpServletRequest request) {
//		// TODO 還沒用到
//		return false;
//	}
//
//	@Override
//	public void addLoginListener(ILoginListener listener) {
//		loginListenerSet.add(listener);
//	}

	// 停用
	// @Override
	// public String getLoginSessionCode(HttpServletRequest request) {
	// return (String) request.getSession().getAttribute("code");
	// }

	private String newRegistrationArg() {
		Calendar c = Calendar.getInstance();
		long nowTime = c.getTime().getTime();
		StringBuffer bufStr = new StringBuffer();
		bufStr.append(nowTime);
		bufStr.append("_");
		bufStr.append(StringUtil.getRandomStr(100));

		return bufStr.toString();
	}

	public class LoginErrorCountException extends Exception {
		private static final long serialVersionUID = 6202405913285583532L;
		private long errorCount = 0;

		public LoginErrorCountException(String msg, long errorCount) {
			super(msg);
			this.errorCount = errorCount;
		}

		public long getErrorCount() {
			return errorCount;
		}

		public void setErrorCount(long errorCount) {
			this.errorCount = errorCount;
		}

	}
}
