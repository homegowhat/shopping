package com.shopping.web.bg.page;

import java.io.PrintWriter;
import java.util.List;

import com.shopping.web.bg.service.IItemService;
import com.shopping.web.bg.service.ILoginService;
import com.shopping.web.db.dto.Item;

import os.rabbit.IRender;
import os.rabbit.ITrigger;
import os.rabbit.callbacks.AjaxInvokeCallback;
import os.rabbit.component.JQueryUiDialog;
import os.rabbit.components.Button;
import os.rabbit.components.Component;
import os.rabbit.components.Form;
import os.rabbit.components.IAuthorizationValidator;
import os.rabbit.components.IButtonListener;
import os.rabbit.components.IComponentListener;
import os.rabbit.components.IFormListener;
import os.rabbit.components.Image;
import os.rabbit.components.Label;
import os.rabbit.components.Link;
import os.rabbit.components.ListBuffer;
import os.rabbit.components.SpringBeanSupportComponent;
import os.rabbit.components.WebPage;
import os.rabbit.components.ajax.AjaxButton;
import os.rabbit.components.form.FileUpload;
import os.rabbit.components.form.TextBox;
import os.rabbit.parser.Tag;

public class PictureManager extends SpringBeanSupportComponent {
	private Component cmpP;
	private Label lblStoreName;
	private AjaxButton btnPopForm;
	private AjaxButton btnCloseForm;
	private JQueryUiDialog uploadBlock;
	private Form uploadForm;
	private TextBox txtStoreId;
	private FileUpload uploadFile;

	private ListBuffer listPic;
	private Link linkImg;
	private Image imgPic;
	private Button btnDel;

	private JQueryUiDialog divUpload;
	private AjaxInvokeCallback callback;

	public PictureManager(Tag tag) {
		super(tag);

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

		cmpP.addComponentListener(new IComponentListener() {

			@Override
			public void afterBuild() {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterRender() {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeRender() {
				IItemService itemService = (IItemService)getBean("itemService");
			
				List<com.shopping.web.db.dto.Image> list = itemService.getImageByItemId(Integer.parseInt(txtStoreId.getValue()));


				if (list != null && list.size() > 0)
					for (com.shopping.web.db.dto.Image img : list) {
						linkImg.setRedirectURL("w.Pic?id=" + img.getId());
						imgPic.setImageURL("w.Pic?id=" + img.getId());
						btnDel.setCallbackParameter("id", String.valueOf(img.getId()));
						listPic.flush();
					}

			}

			@Override
			public void initial() {
				// TODO Auto-generated method stub

			}

		});
		

		btnPopForm.addButtonListener(new IButtonListener() {

			@Override
			public void click() {
				divUpload.show();
				// uploadBlock.show();
			}
		});
		btnCloseForm.addButtonListener(new IButtonListener() {

			@Override
			public void click() {
				uploadBlock.hide();
			}
		});
		btnCloseForm.addUpdateComponent(uploadFile);
		// 圖片上傳的設定------------------------------------------------------
		uploadForm.addFormListener(new IFormListener() {

			@Override
			public void submit() {
				if (uploadFile.getValue() == null) {
					uploadForm.error("上傳的檔案尚未選擇!!");

				} else {

					IItemService itemService = (IItemService)getBean("itemService");
					itemService.addItemPic( Integer.parseInt(txtStoreId.getValue()), uploadFile.getValue());
				}
				String url = getPage().getRequestURI();
				getPage().setRedirect(url + "?id=" + txtStoreId.getValue());
			}
		});
		btnDel.addUpdateComponent(txtStoreId);
		btnDel.addButtonListener(new IButtonListener() {

			@Override
			public void click() {
				String id = getPage().getParameter("id");
				IItemService itemService = (IItemService)getBean("itemService");
				itemService.deleteItemPic(id);
				// 出現無窮迴圈的問題
				String url = getPage().getRequestURI();
				String[] ar = url.split("/");
				url = ar[ar.length - 1];
				url = url.replace("rbt", "tiles");
				getPage().setRedirect(url + "?id=" + txtStoreId.getValue());
			}
		});
		btnDel.setConfirm("確定是否刪除???");

		callback = new AjaxInvokeCallback(getPage(), new ITrigger() {

			@Override
			public void invoke() {

				cmpP.repaint();

			}
		});
		
		callback.addUpdateComponent(txtStoreId);

		getPage().addScript("repaintPiclist", new IRender() {
			@Override
			public void render(PrintWriter writer) {
				// 用一個SCRIPT包起來
				writer.println("function repaintPiclist(id) {$(\"#txtStoreId\").val(id)");
				callback.render(writer);

				writer.println("}");

			}
		});

	}

	@Override
	protected void beforeRender() {
		super.beforeRender();
		IItemService itemService = (IItemService)getBean("itemService");
		Integer itemId = getPage().getParameterAsInteger("id");

		if (itemId == null) {
			itemId = Integer.parseInt(txtStoreId.getValue());
		}
		Item item = itemService.getItem(itemId);

		lblStoreName.setValue(item.getTitle());
		txtStoreId.setValue(String.valueOf(itemId));
	}

}
