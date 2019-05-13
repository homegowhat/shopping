package com.shopping.web.bg.page;

import javax.servlet.http.Cookie;

import org.apache.commons.codec.binary.Base64;

import com.shopping.web.bg.service.ILoginService;
import com.shopping.web.bg.service.abs.AbsLoginService.LoginErrorCountException;
import com.shopping.web.db.dto.User;

import os.rabbit.RenderInterruptedException;
import os.rabbit.components.Component;
import os.rabbit.components.Form;
import os.rabbit.components.IButtonListener;
import os.rabbit.components.IFormListener;
import os.rabbit.components.SpringBeanSupportComponent;
import os.rabbit.components.ajax.AjaxButton;
import os.rabbit.components.form.BooleanCheckBox;
import os.rabbit.components.form.TextBox;
import os.rabbit.components.form.VerificationField;
import os.rabbit.components.validators.RequiredValidator;
import os.rabbit.modifiers.RemoveAttributeModifier;
import os.rabbit.parser.Tag;


public class LoginPage extends SpringBeanSupportComponent {

	private Form form;
	private TextBox txtUsername;
	private TextBox txtPassword;
	private Component cmpValidation;
	private BooleanCheckBox chkRememberMe;
	private VerificationField txtValidationCode;
	private AjaxButton jbtnGetNewValidatePic;
	
	private String websiteId = "shopping";

	public LoginPage(Tag tag) {
		super(tag);
		new RemoveAttributeModifier(this, "validate");
		new RemoveAttributeModifier(this, "useLoginName");
	}

	@Override
	protected void beforeRender() {
		ILoginService loginService = (ILoginService) getBean("loginService");
		User user = null;
		String validate = this.getTag().getAttribute("validate");
		
		if (validate == null || validate.equals("0"))
			cmpValidation.setVisible(false);
		else
			cmpValidation.setVisible(true);

		String loginName = txtUsername.getValue();
		if (loginName != null) {
//			cmpValidation.setVisible(loginService.getCautionLevel(getPage().getRequest()) > 3);
		}

		user = loginService.getLoginUser(getPage().getRequest());
		if (user != null) {
			String page = form.getTag().getAttribute("goto");
			if (page != null) {
				getPage().setRedirect(page);
				throw new RenderInterruptedException();
			}
		}

		String username = null;
		String password = null;
		String remember = null;
		Cookie cookies[] = getPage().getRequest().getCookies();
		// request物件獲取Cookie
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(websiteId + "_USER_NAME"))
					username = cookies[i].getValue();
				else if (cookies[i].getName().equals(websiteId + "_PASSWORD"))
					password = cookies[i].getValue();
				else if (cookies[i].getName().equals(websiteId + "_REMEMBER_ME"))
					remember = cookies[i].getValue();
			}
		}

		chkRememberMe.setValue(remember != null);
		if (remember != null && username != null && password != null && !username.equals("") && !password.equals("")) {
			try {
				Base64 base64 = new Base64();
				password = new String(base64.decode(password.getBytes()));

				loginService.login(getPage().getRequest(),  username, password);

//				setCookie(websiteId, username, password);

				String url = (String) getPage().getRequest().getAttribute("UNAUTHORIZED_URI");
				url = form.getTag().getAttribute("goto");

				getPage().setRedirect(url);
				throw new RenderInterruptedException();
			} catch (Exception e) {
				getPage().executeScript("alert('" + e.getMessage() + "');");
				// Cookie c3 = new Cookie(websiteId + "_REMEMBER_ME", null);
				// c3.setMaxAge(60 * 60 * 24 * 15);
				// getPage().getResponse().addCookie(c3);
				// getPage().setRedirect("Login.rbt");
			}

		}

		// getPage().executeScript("$(document).ready(function(){$(\"#txtUsername\").focus();});");
	}

	@Override
	protected void initial() {
		super.initial();
		new RemoveAttributeModifier(form, "goto");
		txtUsername.addValidator(new RequiredValidator("帳號"));
		txtPassword.addValidator(new RequiredValidator("密碼"));

		form.addFormListener(new IFormListener() {

			@Override
			public void submit() {
				ILoginService loginService = (ILoginService) getBean("loginService");
//				if (loginService.getCautionLevel(getPage().getRequest()) >= 3) {
//					String validationCode = txtValidationCode.getValue();
//					if (validationCode == null) {
//						form.error("驗證碼必須填寫");
//						return;
//					}
//					if (!txtValidationCode.getCode().equals(validationCode)) {
//						form.error("驗證碼不正確");
//						return;
//					}
//				}

				try {
					boolean remember = chkRememberMe.getValue();
					loginService.login(getPage().getRequest(), txtUsername.getValue(), txtPassword.getValue());
					if (remember)
						setCookie(websiteId, txtUsername.getValue(), txtPassword.getValue());
					String page = form.getTag().getAttribute("goto");
					if (page != null) {
						getPage().setRedirect(page);
//						System.out.println("登入後導頁....");
						throw new RenderInterruptedException();
					}
				} catch (LoginErrorCountException e) {

					StringBuffer errorMsg = new StringBuffer();
					errorMsg.append(e.getMessage());
					Long count = e.getErrorCount();
					if (count != null) {
						errorMsg.append("<BR />*達10次該帳號將會被封鎖!");
					}
					form.error(errorMsg.toString());
				} catch (Exception e) {
					form.error(e.getMessage());
					e.printStackTrace();
				}

			}
		});

		jbtnGetNewValidatePic.addButtonListener(new IButtonListener() {
			@Override
			public void click() {
				txtValidationCode.repaintImage();
			}
		});

	}

	private void setCookie(String websiteId, String username, String password) {
		Cookie c1 = new Cookie(websiteId + "_USER_NAME", username);
		c1.setMaxAge(60 * 60 * 24 * 15);
		getPage().getResponse().addCookie(c1);
		Base64 base64 = new Base64();
		password = new String(base64.encode(password.getBytes()));

		Cookie c2 = new Cookie(websiteId + "_PASSWORD", password);
		c2.setMaxAge(60 * 60 * 24 * 15);
		getPage().getResponse().addCookie(c2);
		Cookie c3 = new Cookie(websiteId + "_REMEMBER_ME", new String(base64.encode("true".getBytes())));
		c3.setMaxAge(60 * 60 * 24 * 15);
		getPage().getResponse().addCookie(c3);
	}
	

}