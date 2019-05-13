package com.shopping.web.bg.page;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;

import com.shopping.web.bg.service.IItemService;
import com.shopping.web.bg.service.ILoginService;
import com.shopping.web.db.dto.Catalog;
import com.shopping.web.db.dto.Item;

import os.rabbit.component.PageNavigator;
import os.rabbit.component.test.DivAlone1;
import os.rabbit.components.Button;
import os.rabbit.components.Component;
import os.rabbit.components.IAuthorizationValidator;
import os.rabbit.components.IButtonListener;
import os.rabbit.components.IComponentListener;
import os.rabbit.components.ITreeDataProvider;
import os.rabbit.components.ITreeListener;
import os.rabbit.components.Image;
import os.rabbit.components.Label;
import os.rabbit.components.Link;
import os.rabbit.components.ListBuffer;
import os.rabbit.components.SpringBeanSupportComponent;
import os.rabbit.components.Tree;
import os.rabbit.components.WebPage;
import os.rabbit.components.ajax.AjaxButton;
import os.rabbit.parser.Tag;

public class ItemManager extends SpringBeanSupportComponent {
	private DecimalFormat dFormat = new DecimalFormat("#######");
	
	private Button btnPopForm;
	
	private ListBuffer listPic;
	private Link linkImg;
	private Image imgPic;
	private Label lblTitle;
	private Label lblPrice;
	private Label lblDesc;
	private Link btnEditProduct;
	private Link btnEditProductPic;
	private AjaxButton btnDelProduct;

	private Label lblSelectCatalogName;
	private AjaxButton btnShowCatalogList;

//	private DivAlone1 cmpCatalogTree;
	private Component myModal;
	private AjaxButton btnCloseTree;
	private Tree<Catalog> treeCatalog;
	private Link linkSelectCatalog;
	private Label lblTreeCatalogName;

	

	private PageNavigator topPageCode;
	private PageNavigator downPageCode;

	public ItemManager(Tag tag) {
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

		btnPopForm.addButtonListener(new IButtonListener() {

			@Override
			public void click() {
				String page = btnPopForm.getTag().getAttribute("page");
				getPage().setRedirect(page);
				
			}
		});

		btnDelProduct.addButtonListener(new IButtonListener() {
			@Override
			public void click() {
				Integer id = getPage().getParameterAsInteger("id");
				try {
					IItemService itemService = (IItemService)getBean("itemService");
					itemService.deleteItem(id);
					listPic.repaint();
					getPage().executeScript("alert('刪除成功!!');");
				} catch (Exception e) {
					getPage().executeScript("alert('" + e.getMessage() + "');");
				}
			}
		});
		btnDelProduct.setConfirm("您確定是否刪除?");

//		btnShowCatalogList.addButtonListener(new IButtonListener() {
//
//			@Override
//			public void click() {
//				 System.out.println("1234‧‧‧‧‧");
//				cmpCatalogTree.show();
//				 System.out.println("5678‧‧‧‧‧");
//			}
//
//		});
//		btnCloseTree.addButtonListener(new IButtonListener() {
//			@Override
//			public void click() {
//				cmpCatalogTree.hide();
//			}
//
//		});
//
//		cmpCatalogTree.addComponentListener(new IComponentListener() {
//
//			@Override
//			public void afterBuild() {
//
//			}
//
//			@Override
//			public void afterRender() {
//
//			}
//
//			@Override
//			public void beforeRender() {
//				Catalog root = new Catalog(0);
//				root.setName("全部產品");
//				treeCatalog.setRoot(root);
//			}
//
//			@Override
//			public void initial() {
//				// TODO
//
//			}
//
//		});

		// lblTreeCatalogName.setTagAttributeModifier("style", true);
		treeCatalog.setTreeListener(new ITreeListener<Catalog>() {

			@Override
			public void beforeNodeRender(PrintWriter writer, Catalog node) {
				linkSelectCatalog.setRedirectURL("?catalogId=" + node.getId());
				lblTreeCatalogName.setValue(node.getName());

				Integer id = getPage().getParameterAsInteger("id");
				if (id == null) {
					id = 0;
				}
				if (node.getId() == id) {
					lblTreeCatalogName.setTagAttribute("style", "background-color:#000; color:#FFF");
				} else {
					lblTreeCatalogName.setTagAttribute("style", "background-color:#FFF; color:#000");

				}
			}

			@Override
			public void afterNodeRender(PrintWriter writer, Catalog node) {

			}

		});

		treeCatalog.setDataProvider(new ITreeDataProvider<Catalog>() {

			@Override
			public boolean isExpand(Catalog object) {
				return true;
			}

			@Override
			public boolean hasChildren(Catalog parent) {
				IItemService itemService = (IItemService)getBean("itemService");
				Long count = itemService.getProductCatalogCountByParentId( parent == null ? 0 : parent.getId());
				System.out.println("catalog children count = "+count);
				return count > 0;

			}

			@Override
			public Catalog getNode(String id) {
				if (id.equals("0")) {
					Catalog root = new Catalog(0);
					root.setName("全部產品");
					return root;
				}
				IItemService itemService = (IItemService)getBean("itemService");
				Catalog catalog = itemService.getCategory( Integer.parseInt(id));
				return catalog;
			}

			@Override
			public String getId(Catalog object) {
				return String.valueOf(object.getId());
			}

			@Override
			public List<Catalog> getChildrens(Catalog parent) {

				IItemService itemService = (IItemService)getBean("itemService");
				List<Catalog> list = itemService.getCatalogProductList( parent == null ? 0 : parent.getId());
				System.out.println("catalog list size = "+list.size());
				return list;

			}
		});

	

	}

	@Override
	protected void beforeRender() {
		super.beforeRender();

		IItemService itemService = (IItemService)getBean("itemService");

	
		Integer catalogId = getPage().getParameterAsInteger("catalogId");
		System.out.println("catalogId="+catalogId);
		if (catalogId == null) {
			catalogId = 0;
		}
		Integer pageCode = getPage().getParameterAsInteger("pageCode");
		if (pageCode == null) {
			pageCode = 1;
		}
//		Long totalRowCount = warehouseService.getProductCount(getPage().getRequest(), websiteId, catalogId);
		Long totalRowCount = itemService.getItemCountByCategory(catalogId);
		

//		topPageCode.useTiles();
//		downPageCode.useTiles();
		topPageCode.setValue(pageCode);
		downPageCode.setValue(pageCode);
		topPageCode.setTotalRowCount(totalRowCount);
		downPageCode.setTotalRowCount(totalRowCount);
		downPageCode.setType("down");
		if (catalogId != 0) {
			topPageCode.addArg("catalogId", String.valueOf(catalogId));
			downPageCode.addArg("catalogId", String.valueOf(catalogId));
			// 撈分類名
//			Catalog catalog = warehouseService.getCatalog(getPage().getRequest(), catalogId);
			Catalog catalog = itemService.getCategory(catalogId);
			System.out.println("catalog="+catalog);
			if (catalog != null) {
				lblSelectCatalogName.setValue(catalog.getName());
				// lblCatalogName.setValue(catalog.getName());
			} else {
				lblSelectCatalogName.setValue("全部產品");
				// lblCatalogName.setValue("未選擇");
			}
		} else {
			lblSelectCatalogName.setValue("全部產品");
			// lblCatalogName.setValue("未選擇");
		}

		int pageSize = downPageCode.getPageSize();
		List<Item> list = itemService.getItemList(catalogId, pageCode, pageSize);
		// System.out.println("size = " + list.size());
		// listPic.setCollection(list);
		if (list.size() == 0){
			listPic.setEmptyDataMessage("<div >目前尚無資料</div>");
		}else{
			String pic = null;
			for (Item item : list) {
				lblTitle.setValue(item.getTitle());
				lblPrice.setValue(dFormat.format(item.getUnitPrice()));
				lblDesc.setValue(item.getDescrip());
//				linkImg.setRedirectURL("./../../x.Pic?id=" + item.getPic() + "&w=" + websiteId + "&t=PRODUCT");
//				linkImg.setTagAttribute("title", item.getPicDesc() == null ? "" : item.getPicDesc());
//
				pic = itemService.getOneItemPic(item.getId());
				imgPic.setImageURL("./../../x.Pic?id=" + pic );
				btnDelProduct.setCallbackParameter("id", String.valueOf(item.getId()));
				
				String gotoPage = btnEditProduct.getTag().getAttribute("goto");
				btnEditProduct.setRedirectURL(gotoPage + "?id=" + item.getId());
				
				String gotoPicPage = btnEditProductPic.getTag().getAttribute("goto");
				btnEditProductPic.setRedirectURL(gotoPicPage + "?id=" + item.getId());
				
				listPic.flush();
			}
		}
	}

}
