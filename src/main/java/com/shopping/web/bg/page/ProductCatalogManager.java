package com.shopping.web.bg.page;

import java.io.PrintWriter;
import java.util.List;

import com.shopping.web.db.dto.Catalog;
import com.shopping.web.bg.service.IItemService;
import com.shopping.web.bg.service.ILoginService;

import os.rabbit.components.Component;
import os.rabbit.components.DivAlone;
import os.rabbit.components.IAuthorizationValidator;
import os.rabbit.components.IButtonListener;
import os.rabbit.components.IComponentListener;
import os.rabbit.components.ITreeDataProvider;
import os.rabbit.components.ITreeListener;
import os.rabbit.components.Label;
import os.rabbit.components.Link;
import os.rabbit.components.SpringBeanSupportComponent;
import os.rabbit.components.Tree;
import os.rabbit.components.WebPage;
import os.rabbit.components.ajax.AjaxButton;
import os.rabbit.components.form.IntBox;
import os.rabbit.components.form.TextBox;
import os.rabbit.parser.Tag;


public class ProductCatalogManager extends SpringBeanSupportComponent {
	private Component cmpCatalogTree;
	private Label lblSelectCatalog;
	private AjaxButton btnShowAddFolder;
	private Tree<Catalog> treeCatalog;
	private Link linkSelectCatalog;
	private Label lblTreeCatalogName;
	private AjaxButton btnShowEditFolder;
	private AjaxButton btnShowDelFolder;

	private DivAlone cmpAddFolder;
	private IntBox txtFolderId;
	private TextBox txtFolderName;
	private AjaxButton btnAddCatalog;
	private AjaxButton btnCloseAddFolder;

	private DivAlone cmpEditFolder;
	private IntBox txtCatalogId;
	private TextBox txtCatalogName;
	private AjaxButton btnEditCatalog;
	private AjaxButton btnCloseEditFolder;

	public ProductCatalogManager(Tag tag) {
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

		btnEditCatalog.addButtonListener(new IButtonListener() {
			@Override
			public void click() {
				Integer id = txtCatalogId.getValue();
				String name = txtCatalogName.getValue();
				if (id != null && name != null) {
					IItemService itemService = (IItemService)getBean("itemService");
					Catalog catalog = itemService.getCategory(id);
					catalog.setName(name);
					String msg = null;
					try {
						itemService.updateCategory(catalog);
						cmpCatalogTree.repaint();
						msg = "修改成功";
					} catch (Exception e) {
						e.printStackTrace();
						msg = "修改失敗";
					} finally {
						cmpEditFolder.hide();
						getPage().executeScript("alert(\"" + msg + "!!\")");
					}

				}

			}
		});

		btnShowEditFolder.addButtonListener(new IButtonListener() {
			@Override
			public void click() {
				String idStr = getPage().getParameter("id");
				if (idStr != null) {
					int id = Integer.parseInt(idStr);
					txtCatalogId.setValue(id);
					txtCatalogId.repaint();
					IItemService itemService = (IItemService)getBean("itemService");

					Catalog catalog = itemService.getCategory(id);
					if (catalog != null)
						txtCatalogName.setValue(catalog.getName());
					txtCatalogName.repaint();
					cmpEditFolder.show();
					getPage().executeScript("$(\"#txtCatalogName\").focus();");
				}
			}
		});

		btnShowDelFolder.addButtonListener(new IButtonListener() {
			@Override
			public void click() {
				String idStr = getPage().getParameter("id");
				if (idStr != null) {
					int id = Integer.parseInt(idStr);
					IItemService itemService = (IItemService)getBean("itemService");

					try {
						itemService.deleteCategory( id);
						cmpCatalogTree.repaint();
						getPage().executeScript("alert('刪除成功!!')");
					} catch (Exception e) {
						getPage().executeScript("alert('" + e.getMessage() + "');");
					}
				}
			}
		});
		btnShowDelFolder.setConfirm("您確定是否刪除該分類??(請注意!!若該分類下有產品將無法刪除)");

		btnCloseEditFolder.addButtonListener(new IButtonListener() {
			@Override
			public void click() {
				cmpEditFolder.hide();
			}
		});

		btnShowAddFolder.addButtonListener(new IButtonListener() {
			@Override
			public void click() {
				cmpAddFolder.show();
				getPage().executeScript("$(\"#txtFolderName\").focus();");
			}
		});

		btnCloseAddFolder.addButtonListener(new IButtonListener() {
			@Override
			public void click() {
				cmpAddFolder.hide();
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

				Integer catalogId = getPage().getParameterAsInteger("catalogId");
				if (catalogId == null) {
					catalogId = txtFolderId.getValue();
				}

				if (catalogId != null && catalogId != 0) {
					IItemService itemService = (IItemService)getBean("itemService");
					Catalog catalog = itemService.getCategory( catalogId);
					if (catalog != null) {
						lblSelectCatalog.setValue(catalog.getName());
						txtFolderId.setValue(catalogId);
					} else {
						lblSelectCatalog.setValue("根目錄");
						txtFolderId.setValue(0);
					}
				} else {
					lblSelectCatalog.setValue("根目錄");
					txtFolderId.setValue(0);
				}

			}

			@Override
			public void initial() {
				// TODO

			}

		});

		treeCatalog.setTreeListener(new ITreeListener<Catalog>() {

			@Override
			public void beforeNodeRender(PrintWriter writer, Catalog node) {
				linkSelectCatalog.setRedirectURL("?catalogId=" + node.getId());
				lblTreeCatalogName.setValue(node.getName());
				if (node.getName().equals("根目錄")) {
					btnShowEditFolder.setVisible(false);
					btnShowDelFolder.setVisible(false);
				} else {
					btnShowEditFolder.setVisible(true);
					btnShowEditFolder.setCallbackParameter("id", String.valueOf(node.getId()));
					btnShowDelFolder.setVisible(true);
					btnShowDelFolder.setCallbackParameter("id", String.valueOf(node.getId()));
				}
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
				Long count = itemService.getProductCatalogCountByParentId(parent == null ? 0 : parent.getId());
				return count > 0;

			}

			@Override
			public Catalog getNode(String id) {
				if (id.equals("0")) {
					Catalog root = new Catalog(0);
					root.setName("根目錄");
					return root;
				}
				IItemService itemService = (IItemService)getBean("itemService");
				Catalog catalog = itemService.getCategory(Integer.parseInt(id));
				return catalog;
			}

			@Override
			public String getId(Catalog object) {
				return String.valueOf(object.getId());
			}

			@Override
			public List<Catalog> getChildrens(Catalog parent) {
				IItemService itemService = (IItemService)getBean("itemService");
				List<Catalog> list = itemService.getCatalogProductList(parent == null ? 0 : parent.getId());
				return list;

			}
		});


		btnAddCatalog.addButtonListener(new IButtonListener() {
			@Override
			public void click() {
				Integer id = txtFolderId.getValue();
				String name = txtFolderName.getValue();
				IItemService itemService = (IItemService)getBean("itemService");
				itemService.addCatalogProduct(  id, name, null);
				// 這邊要加入可以連續新增
				cmpCatalogTree.repaint();

			}
		});
	}

	@Override
	protected void beforeRender() {
		super.beforeRender();
		// IPswRoleService pswRoleService = (IPswRoleService)
		// getBean("pswRoleService");
		// HttpServletRequest req = getPage().getRequest();
		// boolean isAdmin = pswRoleService.isAdmin(req);
		// boolean hasListRole = pswRoleService.isContain(req,
		// IPswRoleService.PRODUCT_CATALOG_LIST);

		// if (!isAdmin && !hasListRole) {
		// String url = ProductCatalogManager.this.getTag().getAttribute("url");
		// if (url == null)
		// url = "bg.tiles";
		// try {
		// getPage().getResponse().sendRedirect(url);
		// throw new RenderInterruptedException();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }

		btnAddCatalog.addUpdateComponent(txtFolderId);
		btnAddCatalog.addUpdateComponent(txtFolderName);

		btnEditCatalog.addUpdateComponent(txtCatalogId);
		btnEditCatalog.addUpdateComponent(txtCatalogName);
	}

}
