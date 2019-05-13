package com.shopping.web.bg.page;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

import com.shopping.web.bg.service.IItemService;
import com.shopping.web.bg.service.ILoginService;
import com.shopping.web.db.dto.Catalog;
import com.shopping.web.db.dto.Item;
import com.shopping.web.db.dto.Manufacturer;

import os.rabbit.IRender;
import os.rabbit.ITrigger;
import os.rabbit.callbacks.AjaxInvokeCallback;
import os.rabbit.component.JQueryUiDialog;
import os.rabbit.components.Component;
import os.rabbit.components.Form;
import os.rabbit.components.IAuthorizationValidator;
import os.rabbit.components.IButtonListener;
import os.rabbit.components.IComponentListener;
import os.rabbit.components.IFormListener;
import os.rabbit.components.ITreeDataProvider;
import os.rabbit.components.ITreeListener;
import os.rabbit.components.Image;
import os.rabbit.components.Label;
import os.rabbit.components.ListBuffer;
import os.rabbit.components.SpringBeanSupportComponent;
import os.rabbit.components.Tree;
import os.rabbit.components.WebPage;
import os.rabbit.components.ajax.AjaxButton;
import os.rabbit.components.form.CKEditor;
import os.rabbit.components.form.ComboBox;
import os.rabbit.components.form.TextBox;
import os.rabbit.components.form.UploadField;
import os.rabbit.parser.Tag;

public class ItemAdd extends SpringBeanSupportComponent {
	private DecimalFormat dFormat = new DecimalFormat("#######");
	private AjaxButton btnPopForm;
	private AjaxButton btnCloseForm;
	private Form uploadForm;
	private Label lblActType;
	private Label lblCatalogName;
	private TextBox txtCatalogId;
	private AjaxButton btnSelectCatalog;
	private TextBox txtProductNum;
	private TextBox txtProductName;
	private TextBox txtPicDesc;
	private TextBox txtPrice;
	private TextBox txtMoney;
	private CKEditor txtContent;
	private TextBox txtProductId;
	private UploadField uploadFile;

	private JQueryUiDialog cmpCatalogTree1;
	private AjaxButton btnCloseTree1;
	private Tree<Catalog> treeCatalog1;
	private AjaxButton linkSelectCatalog1;
	private Label lblTreeCatalogName1;

	// private AjaxButton btnShowPic;
	private JQueryUiDialog divPic;

	private Component cmpPic;
	private AjaxButton btnClosePicDiv;
	// private ComponentList<Picture> listPic;
	private ListBuffer listPic;
	private AjaxButton linkImg;
	private Image imgPic;

	private AjaxButton btnFirstProductPage;
	private AjaxButton btnPreProductPage;
	private AjaxButton btnNextProductPage;
	private AjaxButton btnLastProductPage;

	private Label lblSelectCatalogName;
	private AjaxButton btnShowCatalogList;

	// private DivAlone1 cmpCatalogTree;
	private JQueryUiDialog cmpCatalogTree;
	private AjaxButton btnCloseTree;
	private Tree<Catalog> treeCatalog;
	private AjaxButton linkSelectCatalog;
	private Label lblTreeCatalogName;

	private final String type = "PRODUCT";

	private AjaxInvokeCallback callback;

	private ComboBox sltManufacturer;

	public ItemAdd(Tag tag) {
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

		btnSelectCatalog.addButtonListener(new IButtonListener() {

			@Override
			public void click() {
				cmpCatalogTree1.show();
			}
		});
		btnCloseTree1.addButtonListener(new IButtonListener() {

			@Override
			public void click() {
				cmpCatalogTree1.hide();
			}
		});

		// 圖片上傳的設定------------------------------------------------------
		uploadForm.addFormListener(new IFormListener() {

			@Override
			public void submit() {

				// if (data != null)
				// System.out.println("data = " + data.length);
				int catalogId = txtCatalogId.getValue() == null ? 0 : Integer.parseInt(txtCatalogId.getValue());
				// System.out.println("catalogId = " + catalogId);
				String productname = txtProductName.getValue();
				String produectNum = txtProductNum.getValue();
				// String picDesc = txtPicDesc.getValue();

				String m = sltManufacturer.getValue();
				Integer manufacturer = null;
				try {
					if (m != null)
						manufacturer = Integer.parseInt(m);
				} catch (Exception e) {

				}
				int price = 0;
				int money = 0;
				try {
					price = Integer.parseInt(txtPrice.getValue());
				} catch (NumberFormatException e) {
					// System.out.println("price不是數字");
				}
				try {
					money = Integer.parseInt(txtMoney.getValue());
				} catch (NumberFormatException e) {
					// System.out.println("money不是數字");
				}

				String content = txtContent.getValue();
				// System.out.println("name = " + productname + ", price = " +
				// price + ", content = " + content);

				IItemService itemService = (IItemService) getBean("itemService");
				String idStr = txtProductId.getValue();
				// System.out.println("idStr = " + idStr);
				try {
					if (idStr == null || idStr.equals("0")) {
						itemService.addItem(catalogId, productname, price, money, manufacturer, content, null, produectNum);
					} else {
						int id = Integer.parseInt(idStr);
						Item inventory = itemService.getItem(id);
						inventory.setTitle(productname);
						inventory.setUnitPrice(price);
						inventory.setCategory(catalogId);
						inventory.setDescrip(content);
						itemService.updateItem(inventory);
					}
				} catch (Exception e) {
					// System.out.println("e = " + e);
					e.printStackTrace();
				}

				String url = uploadForm.getTag().getAttribute("goto");
				// String[] ar = url.split("/");
				// url = ar[ar.length - 1];
				// url = url.replace("rbt", "tiles");
				if (url != null)
					getPage().setRedirect(url + "?catalogId=" + catalogId);
			}
		});
		cmpCatalogTree1.addComponentListener(new IComponentListener() {

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
				treeCatalog1.setRoot(root);
			}

			@Override
			public void initial() {
				// TODO

			}

		});

		// lblTreeCatalogName1.setTagAttributeModifier("style", true);
		treeCatalog1.setTreeListener(new ITreeListener<Catalog>() {

			@Override
			public void beforeNodeRender(PrintWriter writer, Catalog node) {
				linkSelectCatalog1.setCallbackParameter("id", String.valueOf(node.getId()));
				lblTreeCatalogName1.setValue(node.getName());

				Integer id = getPage().getParameterAsInteger("id");
				if (id == null) {
					id = 0;
				}
				if (node.getId() == id) {
					lblTreeCatalogName1.setTagAttribute("style", "background-color:#000; color:#FFF");
				} else {
					lblTreeCatalogName1.setTagAttribute("style", "background-color:#FFF; color:#000");

				}
			}

			@Override
			public void afterNodeRender(PrintWriter writer, Catalog node) {

			}

		});

		treeCatalog1.setDataProvider(new ITreeDataProvider<Catalog>() {

			@Override
			public boolean isExpand(Catalog object) {
				return true;
			}

			@Override
			public boolean hasChildren(Catalog parent) {
				IItemService itemService = (IItemService) getBean("itemService");
				Long count = itemService.getProductCatalogCountByParentId(parent == null ? 0 : parent.getId());
				return count > 0;

			}

			@Override
			public Catalog getNode(String id) {
				if (id.equals("0")) {
					Catalog root = new Catalog(0);
					root.setName("全部產品");
					return root;
				}
				IItemService itemService = (IItemService) getBean("itemService");
				Catalog catalog = itemService.getCategory(Integer.parseInt(id));
				return catalog;
			}

			@Override
			public String getId(Catalog object) {
				return String.valueOf(object.getId());
			}

			@Override
			public List<Catalog> getChildrens(Catalog parent) {
				IItemService itemService = (IItemService) getBean("itemService");
				List<Catalog> list = itemService.getCatalogProductList(parent == null ? 0 : parent.getId());
				return list;

			}
		});

		linkSelectCatalog1.addButtonListener(new IButtonListener() {
			@Override
			public void click() {
				String id = getPage().getParameter("id");
				txtCatalogId.setValue(id);
				txtCatalogId.repaint();
				IItemService itemService = (IItemService) getBean("itemService");
				Catalog c = itemService.getCategory(Integer.parseInt(id));
				lblCatalogName.setValue(c.getName());
				lblCatalogName.repaint();
				cmpCatalogTree1.hide();
				// uploadBlock.show();
			}
		});

		// btnShowPic.addButtonListener(new IButtonListener() {
		// @Override
		// public void click() {
		// divPic.show();
		// }
		// });

		btnClosePicDiv.addButtonListener(new IButtonListener() {

			@Override
			public void click() {
				divPic.hide();
			}
		});

		cmpPic.addComponentListener(new IComponentListener() {

			@Override
			public void beforeRender() {
//				IPictureService fileService = (IPictureService) getBean("pictureService");
				Integer catalogId = getPage().getParameterAsInteger("catalogId");

				if (catalogId == null) {
					catalogId = 0;
				}
				Integer pageCode = getPage().getParameterAsInteger("pageCode");
				if (pageCode == null) {
					pageCode = 1;
				}
				Long totalRowCount = 0l;// fileService.getPictureCount(getPage().getRequest(),
										// websiteId,type, catalogId);

				if (catalogId != 0) {
					// 撈分類名
					IItemService itemService = (IItemService) getBean("itemService");
					Catalog catalog = itemService.getCategory(catalogId);
					if (catalog != null)
						lblSelectCatalogName.setValue(catalog.getName());
					else
						lblSelectCatalogName.setValue("無");
				} else {
					lblSelectCatalogName.setValue("無");
				}

				int pageSize = 0;
				if (listPic.getTag().getAttribute("pageSize") != null) {
					pageSize = Integer.parseInt(listPic.getTag().getAttribute("pageSize"));
				}
//				List<Picture> list = Collections.emptyList();// fileService.getList(websiteId,
//																// type,
//																// catalogId,
//																// pageCode,
//																// pageSize);
//
//				for (Picture picture : list) {
//					linkImg.setCallbackParameter("id", picture.getId());
//					// linkImg.setRedirectURL("./"+buf.toString()+"uploadImage?id="
//					// + picture.getId()+"&web="+websiteId);
//					// imgPic.setImageURL("./" + websiteId + ".uploadImage?id="
//					// + picture.getId() + "&web=" + websiteId);
//					listPic.flush();
//				}
			}

			@Override
			public void afterRender() {

			}

			@Override
			public void afterBuild() {

			}

			@Override
			public void initial() {
				// TODO

			}
		});

		linkImg.addButtonListener(new IButtonListener() {

			@Override
			public void click() {
				// String websiteId =
				// AddProduct.this.getTag().getAttribute("websiteId");
//				IWebsiteService websiteService = (IWebsiteService) getBean("websiteService");
//				String websiteId = websiteService.getWebsiteId(getPage().getRequest());
//				String id = getPage().getParameter("id");
//				getPage().executeScript("var myckeditor = CKEDITOR.instances.txtContent;myckeditor.insertHtml(\"<img src='./" + websiteId + ".uploadImage?id=" + id + "&web=" + websiteId + "' />\");");

				divPic.hide();
			}
		});

		// tree
		btnShowCatalogList.addButtonListener(new IButtonListener() {

			@Override
			public void click() {
				divPic.hide();
				cmpCatalogTree.show();

			}

		});
		btnCloseTree.addButtonListener(new IButtonListener() {
			@Override
			public void click() {
				cmpCatalogTree.hide();
				divPic.show();
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
				root.setName("根目錄");
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
				// linkSelectCatalog.setRedirectURL("?catalogId=" +
				// node.getId());
				linkSelectCatalog.setCallbackParameter("catalogId", String.valueOf(node.getId()));
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
				// String websiteId =
				// AddProduct.this.getTag().getAttribute("websiteId");
//				IWebsiteService websiteService = (IWebsiteService) getBean("websiteService");
//				String websiteId = websiteService.getWebsiteId(getPage().getRequest());
//				IPictureService fileService = (IPictureService) getBean("pictureService");

//				Long count = fileService.getCatalogCountByParentId(getPage().getRequest(), websiteId, type, parent == null ? 0 : parent.getId());
//				return count > 0;
return false;
			}

			@Override
			public Catalog getNode(String id) {
				if (id.equals("0")) {
					Catalog root = new Catalog(0);
					root.setName("根目錄");
					return root;
				}
				IItemService itemService = (IItemService) getBean("itemService");
				Catalog catalog = itemService.getCategory(Integer.parseInt(id));
				return catalog;
			}

			@Override
			public String getId(Catalog object) {
				return String.valueOf(object.getId());
			}

			@Override
			public List<Catalog> getChildrens(Catalog parent) {
				IItemService itemService = (IItemService) getBean("itemService");
				List<Catalog> list = itemService.getCatalogProductList(parent == null ? 0 : parent.getId());
				return list;

			}
		});

		linkSelectCatalog.addButtonListener(new IButtonListener() {
			@Override
			public void click() {
				// Integer id = getPage().getParameterAsInteger("catalogId");
				cmpCatalogTree.hide();
				divPic.show();
				cmpPic.repaint();
			}
		});

		callback = new AjaxInvokeCallback(getPage(), new ITrigger() {

			@Override
			public void invoke() {
				divPic.show();
			}
		});
		getPage().addScript("popPicDiv_", new IRender() {

			@Override
			public void render(PrintWriter writer) {

				// writer.println("<script>");
				writer.println("CKEDITOR.plugins.registered['image'] =");
				writer.println("{");
				writer.println("    init : function( editor )");
				writer.println("    {");
				writer.println("        var command = editor.addCommand( 'image', {");
				writer.println("            exec : function( editor ) {");
				callback.render(writer);
				writer.println("            }");
				writer.println("        });");
				writer.println("        editor.ui.addButton( 'Image',{label : '選擇圖片',command : 'image'});");
				writer.println("    }");
				writer.println("}");
				writer.println("CKEDITOR.plugins.registered['save'] ={};");
				writer.println("CKEDITOR.plugins.registered['flash'] ={};");
				writer.println("CKEDITOR.plugins.registered['link'] ={};");
				// writer.println("</script>");
			}
		});
	}

	@Override
	protected void beforeRender() {
		super.beforeRender();

		String idStr = getPage().getParameter("id");
		IItemService itemService = (IItemService) getBean("itemService");
		if (idStr != null) {
			int id = Integer.parseInt(idStr);
			
			Item item = itemService.getItem(id);
			Catalog catalog = itemService.getCategory(item.getCategory());
			txtCatalogId.setValue(String.valueOf(item.getCategory()));
			if (catalog != null) {
				lblCatalogName.setValue(catalog.getName());
			} else {
				lblCatalogName.setValue("未選擇");
			}
			// lblCatalogName.repaint();

			sltManufacturer.setValue(item.getManufacturerId() + "");
			

			txtProductId.setValue(idStr);
			// txtProductId.repaint();
			// txtProductNum.setValue(inventory.getInCode());

			txtProductName.setValue(item.getTitle());
			// txtProductName.repaint();
			txtPrice.setValue(dFormat.format(item.getUnitPrice()));

			// txtMoney.setValue(dFormat.format(inventory.getMoney()));
			// txtPrice.repaint();
			txtContent.setValue(item.getDescrip());
			// txtContent.repaint();
			// txtPicDesc.setValue(inventory.getPicDesc());
			// txtPicDesc.repaint();

			lblActType.setValue("修改");
			// lblActType.repaint();
			// uploadBlock.show();
		} else {
			lblActType.setValue("新增");
			// lblActType.repaint();
		}
		
		List<Manufacturer> mlist = itemService.getManufacturerList(1, 10000000, null);
		System.out.println("mlist.size ====="+mlist.size());
		for (Manufacturer m : mlist) {
			sltManufacturer.addOptions(m.getName(), m.getId());
		}
	}

}
