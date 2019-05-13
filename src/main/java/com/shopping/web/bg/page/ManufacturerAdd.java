package com.shopping.web.bg.page;

import com.shopping.web.bg.service.IItemService;
import com.shopping.web.bg.service.ILoginService;
import com.shopping.web.db.dto.Manufacturer;

import os.rabbit.component.validators.SizeValidator;
import os.rabbit.components.Form;
import os.rabbit.components.IAuthorizationValidator;
import os.rabbit.components.IFormListener;
import os.rabbit.components.Label;
import os.rabbit.components.ListBuffer;
import os.rabbit.components.SpringBeanSupportComponent;
import os.rabbit.components.WebPage;
import os.rabbit.components.form.CheckBox;
import os.rabbit.components.form.CheckBoxGroup;
import os.rabbit.components.form.TextBox;
import os.rabbit.components.validators.RequiredValidator;
import os.rabbit.parser.Tag;

public class ManufacturerAdd extends SpringBeanSupportComponent {

	// private ListBuffer listStore;
	private Form storeForm;
	private TextBox txtName;
	private TextBox tbSTel;
	private TextBox tbSAddress;
	private TextBox txtLink;
//	private TextBox taSMenu;
	private TextBox txtEditId;
//	private FileUpload tbSImg;
//	private Component cmpPic;
	
	private CheckBoxGroup chbGroup ;
	private ListBuffer listChb;
	private CheckBox chbCatalog;
	private Label lblCatalog;

	public ManufacturerAdd(Tag tag) {
		super(tag);
	}

	@Override
	protected void beforeRender() {
		super.beforeRender();
		

		String id = getPage().getParameter("id");
		txtEditId.setValue(id);
		
		
	}

	@Override
	protected void initial() {
		super.initial();

		getPage().addAuthorizationValidator(new IAuthorizationValidator() {
			@Override
			public boolean validate(WebPage page) {
				ILoginService userService = (ILoginService) getBean("loginService");
				return userService.isLogin(getPage().getRequest());
			}
		});

		storeForm.addFormListener(new IFormListener() {
			@Override
			public void submit() {
				IItemService itemService = (IItemService)getBean("itemService");
				String storeId = txtEditId.getValue();

				
				
				if (storeId != null) {
					// update
					try {
						Manufacturer store = itemService.getManufacturer(Integer.parseInt(storeId));
						store.setName(txtName.getValue());
						store.setLink(txtLink.getValue());

						itemService.editManufacturer( store);
						getPage().setRedirect(storeForm.getTag().getAttribute("url"));
					} catch (Exception e) {
						e.printStackTrace();
						storeForm.error("輸入資料型態有誤或過長!!");
					}
				} else {
					String name = txtName.getValue();
					String link = txtLink.getValue();

					try {
						itemService.addManufacturer(name, link);
						getPage().setRedirect(storeForm.getTag().getAttribute("url"));
					} catch (Exception e) {
						e.printStackTrace();
						storeForm.error("輸入資料型態有誤或過長!!");
					}
				}
			}
		});

		txtName.addValidator(new RequiredValidator("NAME"));
		txtName.addValidator(new SizeValidator("NAME", 128));
		txtLink.addValidator(new SizeValidator("Link", 255));
		
		
	}

}
