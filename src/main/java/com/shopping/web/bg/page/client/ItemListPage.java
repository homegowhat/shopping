package com.shopping.web.bg.page.client;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;

import com.kuroshio.web.module.login.service.ILoginService;
import com.kuroshio.web.module.picture.service.IPictureService;
import com.kuroshio.web.module.website.service.IWebsiteService;
import com.kuroshio.web.module.xxx.bean.Inventory;
import com.kuroshio.web.module.xxx.service.ISssService;
import com.kuroshio.web.module.xxx.service.IWwwService;
import com.shopping.web.bg.service.IItemService;
import com.shopping.web.db.dto.Catalog;
import com.shopping.web.db.dto.Item;

import os.rabbit.component.JQueryUiDialog;
import os.rabbit.component.PageNavigator;
import os.rabbit.components.ComponentList;
import os.rabbit.components.IButtonListener;
import os.rabbit.components.IComponentListener;
import os.rabbit.components.IListListener;
import os.rabbit.components.ITreeDataProvider;
import os.rabbit.components.ITreeListener;
import os.rabbit.components.Image;
import os.rabbit.components.Label;
import os.rabbit.components.Link;
import os.rabbit.components.SpringBeanSupportComponent;
import os.rabbit.components.Tree;
import os.rabbit.components.ajax.AjaxButton;
import os.rabbit.parser.Tag;


public class ItemListPage extends SpringBeanSupportComponent {
	private DecimalFormat dFormat = new DecimalFormat("#######");

	private ComponentList<Item> listPic;
	private Link linkImg;
	private Image imgPic;
	private Label lblName;
	private Label lblPrice;
	private AjaxButton btnOrderProduct;

	private Label lblSelectCatalogName;
	private AjaxButton btnShowCatalogList;

	private JQueryUiDialog cmpCatalogTree;
	private AjaxButton btnCloseTree;
	private Tree<Catalog> treeCatalog;
	private Link linkSelectCatalog;
	private Label lblTreeCatalogName;

	private PageNavigator topPageCode;
	private PageNavigator downPageCode;

	public ItemListPage(Tag tag) {
		super(tag);

	}

	@Override
	protected void initial() {
		super.initial();
		btnOrderProduct.addButtonListener(new IButtonListener() {

			@Override
			public void click() {
//				String websiteId = ProductListByWeddingPage.this.getTag().getAttribute("websiteId");
				IWebsiteService websiteService = (IWebsiteService)getBean("websiteService");
				String websiteId = websiteService.getWebsiteId(getPage().getRequest());
				ILoginService userService = (ILoginService) getBean("loginService");
				String userId = userService.getLoginId(getPage().getRequest());
				if (userId == null || userId.equals("0")) {
					getPage().executeScript("alert('請先登入!!');");
					return;
				}
				Integer id = getPage().getParameterAsInteger("id");
				ISssService salesService = (ISssService) getBean("sssService");
				salesService.saveSalesOrderProduct(getPage().getRequest(), websiteId, id);
				getPage().executeScript("alert('已加入購物車!!');window.location.href='shopping_car.tiles';");

			}

		});

		btnShowCatalogList.addButtonListener(new IButtonListener() {

			@Override
			public void click() {
				cmpCatalogTree.show();
			}

		});
		btnCloseTree.addButtonListener(new IButtonListener() {
			@Override
			public void click() {
				cmpCatalogTree.hide();
			}

		});

		cmpCatalogTree.addComponentListener(new IComponentListener() {

			@Override
			public void afterBuild() {

			}

			@Override
			public void afterRender() {

			}

			@Override
			public void beforeRender() {
				Catalog root = new Catalog(0);
				root.setName("全部產品");
				treeCatalog.setRoot(root);
			}

			@Override
			public void initial() {
				// TODO

			}

		});

		// lblTreeCatalogName.setTagAttributeModifier("style", true);
		treeCatalog.setTreeListener(new ITreeListener<Catalog>() {

			@Override
			public void beforeNodeRender(PrintWriter writer, Catalog node) {
				linkSelectCatalog.setRedirectURL("?catalogId=" + node.getId());
				// linkSelectCatalog.setCallbackParameter("catalogId",
				// String.valueOf(node.getId()));
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
				return list;

			}
		});

		// 圖片上傳的設定------------------------------------------------------

		listPic.setListListener(new IListListener<Item>() {

			@Override
			public void emptyRender(PrintWriter writer) {
				writer.write("<tr  class=\"listRow\" ><td  colspan=\"10\" align=\"center\" >目前尚無資料</td></tr>");

			}

			@Override
			public void beforeEachRender(PrintWriter arg0, Item picture, int index) {
				lblName.setValue(picture.getTitle());
				lblPrice.setValue(dFormat.format(picture.getUnitPrice()));
//				String websiteId = ProductListByWeddingPage.this.getTag().getAttribute("websiteId");
				IWebsiteService websiteService = (IWebsiteService)getBean("websiteService");
				String websiteId = websiteService.getWebsiteId(getPage().getRequest());
				
				//data-largesrc="" data-title=""  data-description=""
				linkImg.setRedirectURL(linkImg.getTag().getAttribute("href") + "?id=" + picture.getId());
				linkImg.setTagAttribute("data-title", picture.getTitle());
				//linkImg.setTagAttribute("data-description", picture.getDesc());
//				linkImg.setTagAttribute("data-largesrc", "./../x.Pic?id=" + picture.getPic() + "&w=" + websiteId + "&t=PRODUCT");
				
				Integer width = Integer.parseInt(imgPic.getTag().getAttribute("width"));
				Integer height = Integer.parseInt(imgPic.getTag().getAttribute("height"));
				IPictureService fileUploadService = (IPictureService) getBean("pictureService");
//				String pic = fileUploadService.getResizePic(getPage().getRequest(), websiteId, "PRODUCT", picture.getPic(), width, height);
//				imgPic.setImageURL("./../x.Pic?id=" + pic + "&w=" + websiteId + "&t=PRODUCT");
//				linkImg.setTagAttribute("title", picture.getPicDesc() == null ? "" : picture.getPicDesc());
				btnOrderProduct.setCallbackParameter("id", String.valueOf(picture.getId()));
			}

			@Override
			public void afterEachRender(PrintWriter arg0, Item picture, int index) {

			}
		});
		// linkImg.setTagAttributeModifier("title", true);

		// /

	}

	@Override
	protected void beforeRender() {
		super.beforeRender();

		IItemService itemService = (IItemService)getBean("itemService");
		
		Integer catalogId = getPage().getParameterAsInteger("catalogId");

		if (catalogId == null) {
			catalogId = 0;
		}
		Integer pageCode = getPage().getParameterAsInteger("pageCode");
		if (pageCode == null) {
			pageCode = 1;
		}
		Long totalRowCount = itemService.getItemCountByCategory( catalogId);
		// msgService.getPictureCount(getPage().getRequest(), websiteId,
		// catalogId, null);

//		topPageCode.useTiles();
//		downPageCode.useTiles();
		topPageCode.setPageSize(8);
		downPageCode.setPageSize(8);
		topPageCode.setValue(pageCode);
		downPageCode.setValue(pageCode);
		topPageCode.setTotalRowCount(totalRowCount);
		downPageCode.setTotalRowCount(totalRowCount);
		downPageCode.setType("down");
		if (catalogId != 0) {
			topPageCode.addArg("catalogId", String.valueOf(catalogId));
			downPageCode.addArg("catalogId", String.valueOf(catalogId));
			// 撈分類名
			Catalog catalog = itemService.getCategory( catalogId);
			if (catalog != null) {
				lblSelectCatalogName.setValue(catalog.getName());
			} else {
				lblSelectCatalogName.setValue("全部產品");
			}
		} else {
			lblSelectCatalogName.setValue("全部產品");
		}

		int pageSize = downPageCode.getPageSize();
		List<Item> list = itemService.getProductList( catalogId, pageCode, pageSize);
		// msgService.getList(websiteId, catalogId, pageCode, pageSize);
		listPic.setCollection(list);

		// TODO 先關閉
		btnOrderProduct.setVisible(false);
	}

}
