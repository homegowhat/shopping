package com.shopping.web.bg.page;

import java.util.List;

import com.shopping.web.bg.service.IItemService;
import com.shopping.web.bg.service.ILoginService;
import com.shopping.web.db.dto.Manufacturer;

import os.rabbit.component.PageNavigator;
import os.rabbit.components.Component;
import os.rabbit.components.IAuthorizationValidator;
import os.rabbit.components.IButtonListener;
import os.rabbit.components.IComponentListener;
import os.rabbit.components.Label;
import os.rabbit.components.Link;
import os.rabbit.components.ListBuffer;
import os.rabbit.components.SpringBeanSupportComponent;
import os.rabbit.components.WebPage;
import os.rabbit.components.ajax.AjaxButton;
import os.rabbit.components.form.TextBox;
import os.rabbit.parser.Tag;

public class ManufacturerManager extends SpringBeanSupportComponent {

	private TextBox txtCatalog;
	private ListBuffer listCatalog;
	private AjaxButton btnCatalog;
	private Label lblCatalog;
	
	private Component cmpStore;
	private ListBuffer listStores  ;
	private Label lblSName, lblSDesp;
	private Link modifyStor,linkStore,modifyPic;
	private AjaxButton btnDel;
	private PageNavigator txtPageCode;
	
	private AjaxButton btnKeyword;
	private TextBox txtKeyword;
	
	public ManufacturerManager(Tag tag) {
		super(tag);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void beforeRender() {
		//page load first step
		super.beforeRender();
		
		
		String catalog = getPage().getParameter("c");
		if(catalog == null){		
			catalog = "ALL";
		}
		txtCatalog.setValue(catalog);
		txtKeyword.setValue(getPage().getParameter("k"));
		
		
	}
	

	@Override
	protected void initial() {
		super.initial();
		//頁面送出後會做的initial
		getPage().addAuthorizationValidator(new IAuthorizationValidator() {
			@Override
			public boolean validate(WebPage page) {
				ILoginService userService = (ILoginService) getBean("loginService");
				return userService.isLogin(getPage().getRequest());
			}
		});
		
		cmpStore.addComponentListener(new IComponentListener() {
			
			@Override
			public void initial() {
				
			}
			
			@Override
			public void beforeRender() {
				IItemService itemService = (IItemService)getBean("itemService");
				Integer pageCode = getPage().getParameterAsInteger("pageCode");
				if(pageCode == null)pageCode = 1; 
				
				String keyword = txtKeyword.getValue();
				
				long totalStore = itemService.getManufacturerCount(keyword);
				
				txtPageCode.setValue(pageCode);
				txtPageCode.setPageSize(10);
				txtPageCode.setTotalRowCount(totalStore);
				if(keyword != null)
				txtPageCode.addArg("k", keyword);
				
				List<Manufacturer> list = itemService.getManufacturerList(pageCode, 10, keyword);
				
				if(list != null && list.size() > 0)
				for (Manufacturer m : list) {
					lblSName.setValue(m.getName());
					lblSDesp.setValue(m.getLink());
					btnDel.setCallbackParameter("id", String.valueOf(m.getId()));
					listStores.flush();
				}
			}
			
			@Override
			public void afterRender() {
				
			}
			
			@Override
			public void afterBuild() {
				
			}
		});
		
		btnDel.addButtonListener(new IButtonListener() {
			
			@Override
			public void click() {
				Integer id = getPage().getParameterAsInteger("id");
				IItemService itemService = (IItemService)getBean("itemService");
				String msg = null;
				try {
					itemService.deleteManufacturer( id);
					msg = "location.href='manufacturer_manager.rbt?pageCode="+txtPageCode.getValue()+"'";
				} catch (Exception e) {
					msg = "alert('"+e.getMessage()+"');";
				}
				
				getPage().executeScript(msg);
			}
		});
		btnDel.addUpdateComponent(txtPageCode);
		btnDel.setConfirm("您確定是否刪除??");
		
		btnCatalog.addButtonListener(new IButtonListener() {

			@Override
			public void click() {
				txtCatalog.setValue(getPage().getParameter("c"));
				txtCatalog.repaint();
				cmpStore.repaint();
			}
		});
		btnCatalog.addUpdateComponent(txtKeyword);
		btnKeyword.addUpdateComponent(txtCatalog);
		btnKeyword.addUpdateComponent(txtKeyword);
		btnKeyword.addButtonListener(new IButtonListener() {
			@Override
			public void click() {
			    cmpStore.repaint();
			}
		});
		
	}

}
