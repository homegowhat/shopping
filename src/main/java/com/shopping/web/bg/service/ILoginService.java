package com.shopping.web.bg.service;

import javax.servlet.http.HttpServletRequest;

import com.shopping.web.db.dto.User;


public interface ILoginService {

    /**
     * 外部登入
     */
    public static final String FACEBOOK = "FACEBOOK";
    public static final String YAHOO = "YAHOO";

    /**
     * 登入
     * 
     * @param request
     * @param email
     * @param password
     * @param websiteId
     * @return
     */
    void login(HttpServletRequest request, String account, String password)throws Exception;

    /**
     * 登出
     * 
     * @param request
     * @param websiteId
     */
    void logout(HttpServletRequest request);
    /**
     * 取得login警示等級
     * @param request
     * @return
     */
//   long getCautionLevel(HttpServletRequest request);
   /**
    * 是否禁止登入
    * @param request
    * @return
    */
//   boolean isBeanLogin(HttpServletRequest request);
   /**
    * 是否登入
    * 
    * @param request
    * @param websiteId
    * @return
    */
   boolean isLogin(HttpServletRequest request);
    /**
     * 取得登入user實体
     * 
     * @param request
     * @param websiteId
     * @return
     */
    User getLoginUser(HttpServletRequest request);
    /**
     * 取得登入user的id
     * 
     * @param request
     * @param websiteId
     * @return
     */
    Integer getLoginId(HttpServletRequest request) ;
    /**
     * 取得外部網站登入者資料
     * 
     * @param outsideId
     * @param type
     * @param websiteId
     * @return
     */
//    OutsideUser getOutsideUser(HttpServletRequest request, String outsideId, String type);

    /**
     * 取得外部網站登入者資料
     * 
     * @param id
     * @param type
     * @param websiteId
     * @return
     */
//    OutsideUser getOutsideUserByUserId(HttpServletRequest request, int id, String type);

    /**
     * 新增外部網站登入者
     * 
     * @param userId
     * @param outsideId
     * @param email
     * @param name
     * @param type
     * @param websiteId
     * @throws SQLException
     */
//    void addOutsideUser(HttpServletRequest request, int userId, String outsideId, String email, String loginName, String name, String type) throws SQLException;


    /**
     * 新增登入時事件
     * 
     * @param listener
     */
//    void addLoginListener(ILoginListener listener);


    /**
     * 新增未開通的使用者
     * 
     * @param request
     * @param email
     * @param rePassword
     * @param name
     * @param websiteId
     * @param companyName
     * @param domain
     */
//    void addLockedUser(HttpServletRequest request, String email, String loginName, String rePassword, String name, String companyName, String domain);

    /**
     * email點下開通的會員
     * 
     * @param arg
     * @return
     */
//    String openRegistrationUser(String arg);

    /**
     * 寄發新密碼mail
     * 
     * @param email
     * @param websiteId
     */
//    void getNewPwdMail(HttpServletRequest request, String email);

    
//    String getWbsNumber(HttpServletRequest request);
//    LoginSession getWbsUser(String value);

//	void loginByCard(HttpServletRequest request, User user) throws Exception;
}
