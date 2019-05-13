package com.shopping.web.bg.service;

import com.shopping.web.bg.bean.PagingBean;
import com.shopping.web.db.dto.User;

public interface IUserService {

	 /**
     * 使用者登入狀態 LOCKED 未開通 NORMAL 正常 BLOCK 封 DELETE 刪除
     */
    public static final String LOCKED = "LOCKED";
    public static final String NORMAL = "NORMAL";
    public static final String BLOCK = "BLOCK";
    public static final String DELETE = "DELETE";

    /**
     * 新增登入使用者
     * 
     * @param email
     * @param password
     * @param name
     * @param status
     * @param websiteId
     */
    int addUser(String email,String loginName, String password, String name, String status);

    /**
     * 修改登入使用者資料
     * 
     * @param user
     */
    void editUser(User user);


    /**
     * 使用LoginName(可能是email或該網站唯一值)取得user資料
     * 
     * @param email
     * @param websiteId
     * @return
     */
    User getUserByLoginName(String loginName);
//TODO 這是需要加的 method
    //getUserByEmail(String email, String websiteId);

    /**
     * 修改密碼
     * 
     * @param user
     * @param websiteId
     * @param oldPwd
     * @param newPwd
     * @throws Exception
     */
    void editPassword(User user,  String oldPwd, String newPwd) throws Exception;


    /**
     * 取得會員列表
     * 
     * @param websiteId
     * @param status
     * @param pageCode
     * @param pageSize
     * @return
     */
    PagingBean<User> getUserList( String status, Integer pageCode, int pageSize);

	User getUserByMail(String mail);

	User getUserById(int id );
}
