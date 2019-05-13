package com.shopping.web.bg.page;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import os.rabbit.component.PageNavigator;
import os.rabbit.components.Button;
import os.rabbit.components.Component;
import os.rabbit.components.ComponentList;
import os.rabbit.components.DivAlone;
import os.rabbit.components.IAuthorizationValidator;
import os.rabbit.components.IButtonListener;
import os.rabbit.components.IComponentListener;
import os.rabbit.components.IListListener;
import os.rabbit.components.Label;
import os.rabbit.components.SpringBeanSupportComponent;
import os.rabbit.components.WebPage;
import os.rabbit.components.ajax.AjaxButton;
import os.rabbit.components.form.TextBox;
import os.rabbit.parser.Tag;

import com.kuroshio.web.module.login.service.ILoginService;
import com.kuroshio.web.module.website.service.IWebsiteService;
import com.kuroshio.web.module.xxx.bean.Member;
import com.kuroshio.web.module.xxx.bean.MemberDesc;
import com.kuroshio.web.module.xxx.service.IXxxService;

/**
 * @author homegowhat
 * 
 */
public class CustomerManager extends SpringBeanSupportComponent {

    private Component cmpMember;
//    private PageNavigator topPageCode;
    private PageNavigator downPageCode;
    private ComponentList<Member> listMember;
    private Label lblNum;
    private AjaxButton btnMemberShow;
    private Label lblName;
    private Label lblCellphone;
    private Label lblTel;
    private Label lblEmail;
    private Label lblAddress;
    private AjaxButton btnShowEdit;
    private TextBox txtSearch;
    private Button btnSearch;

    
    private DivAlone cmpDesc;
    private Label lblNameDesc;
    private Label lblCellphoneDesc;
    private Label lblTelDesc;
    private Label lblFaxDesc;
    private Label lblMailDesc;
    private Label lblAddressDesc;
    private Component cmpMemberDescList;
    private ComponentList<MemberDesc> listMemberDesc;
    private Label lblMemberDescTitle;
    private Label lblMemberDescContent;
    private TextBox txtSelectMemberId;
    private AjaxButton btnCloseMemberDesc;

    public CustomerManager(Tag tag) {
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

	btnSearch.addButtonListener(new IButtonListener() {
	    @Override
	    public void click() {
		StringBuffer buf = new StringBuffer();

		String url = getPage().getRequestURI();
		String[] ar = url.split("/");
		url = ar[ar.length - 1];
//		String useTiles = (String) CustomerManager.this.getTag().getAttribute("useTiles");
//		if (useTiles != null) {
//		    url = url.replace("rbt", "tiles");
//		}
		buf.append(url);

		String arg = txtSearch.getValue();
		if (arg != null && !arg.equals("")) {
		    buf.append("?arg=");
		    buf.append(arg);
		}

		getPage().setRedirect(buf.toString());
	    }

	});

	cmpMember.addComponentListener(new IComponentListener() {
	    @Override
	    public void afterBuild() {

	    }

	    @Override
	    public void afterRender() {

	    }

	    @Override
	    public void beforeRender() {
//		IPswRoleService pswRoleService = (IPswRoleService) getBean("pswRoleService");
//		HttpServletRequest req = getPage().getRequest();
//		boolean isAdmin = pswRoleService.isAdmin(req);
//		boolean RoleOfStaffAdd = pswRoleService.isContain(req, IPswRoleService.CUSTOMER_ADD);
//		boolean roleOfStaffEdit = pswRoleService.isContain(req, IPswRoleService.CUSTOMER_EDIT);
//		boolean roleOfStaffDel = pswRoleService.isContain(req, IPswRoleService.CUSTOMER_DEL);
//		cmpEffectTitle.setVisible(isAdmin || roleOfStaffEdit || roleOfStaffDel);
//		cmpAddMember.setVisible(isAdmin || RoleOfStaffAdd|| roleOfStaffEdit);
//		btnShowAdd.setVisible(isAdmin || RoleOfStaffAdd);
		
		
//		String websiteId = CustomerManager.this.getTag().getAttribute("websiteId");
	    	IWebsiteService websiteService = (IWebsiteService)getBean("websiteService");
			String websiteId = websiteService.getWebsiteId(getPage().getRequest());

		Integer pageCode = getPage().getParameterAsInteger("pageCode");
		if (pageCode == null) {
		    pageCode = 1;
		}
		String arg = txtSearch.getValue();
		if (getPage().getParameter("arg") != null) {
		    arg = getPage().getRequest().getParameter("arg");
		    txtSearch.setValue(arg);
		}

		IXxxService xxxService = (IXxxService) getBean("xxxService");
		Long totalRowCount = xxxService.getCustomerCount(websiteId, getPage().getRequest(), null, arg);
//		topPageCode.useTiles();
//		downPageCode.useTiles();
//		topPageCode.setValue(pageCode);
		downPageCode.setValue(pageCode);
//		topPageCode.setTotalRowCount(totalRowCount);
		downPageCode.setTotalRowCount(totalRowCount);
		downPageCode.setType("down");
		if (arg != null) {
//		    topPageCode.addArg("arg", arg);
		    downPageCode.addArg("arg", arg);
		}
		List<Member> list = xxxService.getCustomerList(websiteId, getPage().getRequest(), null, arg, pageCode, downPageCode.getPageSize());
		listMember.setCollection(list);

//		listMember.setTagAttribute("class", "listRow");

	    }

	    @Override
	    public void initial() {
		// TODO
		
	    }
	});

	listMember.setListListener(new IListListener<Member>() {
	    @Override
	    public void afterEachRender(PrintWriter arg0, Member arg1, int arg2) {

	    }

	    @Override
	    public void beforeEachRender(PrintWriter arg0, Member m, int index) {
		int count = (downPageCode.getValue() - 1) * downPageCode.getPageSize();
		lblNum.setValue(String.valueOf((index + 1 + count)));
		btnMemberShow.setCallbackParameter("m_id", String.valueOf(m.getId()));
		lblName.setValue(m.getName());
		lblCellphone.setValue(m.getCellphone());
		lblTel.setValue(m.getTel());
		lblEmail.setValue(m.getMail());
		lblAddress.setValue(m.getAddress());
		btnShowEdit.setCallbackParameter("member_id", String.valueOf(m.getId()));

//		IPswRoleService pswRoleService = (IPswRoleService) getBean("pswRoleService");
//		HttpServletRequest req = getPage().getRequest();
//		boolean isAdmin = pswRoleService.isAdmin(req);
//		boolean roleOfStaffEdit = pswRoleService.isContain(req, IPswRoleService.CUSTOMER_EDIT);
//		boolean roleOfStaffDel = pswRoleService.isContain(req, IPswRoleService.CUSTOMER_DEL);
//
//		cmpEffect.setVisible(isAdmin || roleOfStaffEdit || roleOfStaffDel);
//		btnShowEdit.setVisible(isAdmin || roleOfStaffEdit);
//		btnMemberDel.setVisible(isAdmin || roleOfStaffDel);
	    }

	    @Override
	    public void emptyRender(PrintWriter writer) {
		  writer.write("<tr class=\"listRow\"><td  colspan=\"10\" align=\"center\" >目前尚無資料</td></tr>");
			
	    }

	});


	
	btnMemberShow.addButtonListener(new IButtonListener() {

	    @Override
	    public void click() {
		cmpDesc.show();
		String id = getPage().getParameter("m_id");
		txtSelectMemberId.setValue(id);
		txtSelectMemberId.repaint();
		IXxxService memberService = (IXxxService) getBean("xxxService");
		Member m = memberService.getMember(Integer.parseInt(id));

		lblNameDesc.setValue(m.getName());
		lblNameDesc.repaint();
		lblCellphoneDesc.setValue(m.getCellphone() == null ? "" : m.getCellphone());
		lblCellphoneDesc.repaint();
		lblTelDesc.setValue(m.getTel() == null ? "" : m.getTel());
		lblTelDesc.repaint();
		lblFaxDesc.setValue(m.getFax()==null?"":m.getFax());
		lblFaxDesc.repaint();
		lblMailDesc.setValue(m.getMail()==null?"":m.getMail());
		lblMailDesc.repaint();
		lblAddressDesc.setValue(m.getAddress() == null ? "" : m.getAddress());
		lblAddressDesc.repaint();
		 cmpMemberDescList.repaint();
	    }
	});
	
	cmpMemberDescList.addComponentListener(new IComponentListener() {

	    @Override
	    public void beforeRender() {
		String id = txtSelectMemberId.getValue();
		if (id != null) {

		    IXxxService memberService = (IXxxService) getBean("xxxService");

		    List<MemberDesc> datas = memberService.getMemberDescList(Integer.parseInt(id));

		    listMemberDesc.setCollection(datas);
		} else {

		    listMemberDesc.setCollection(Collections.EMPTY_LIST);
		}
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

	listMemberDesc.setListListener(new IListListener<MemberDesc>() {

	    @Override
	    public void afterEachRender(PrintWriter arg0, MemberDesc desc, int index) {

	    }

	    @Override
	    public void beforeEachRender(PrintWriter arg0, MemberDesc desc, int index) {
		lblMemberDescTitle.setValue(desc.getDescTitle());
		lblMemberDescContent.setValue(desc.getDescContent());
	    }

	    @Override
	    public void emptyRender(PrintWriter arg0) {

	    }

	});

	btnCloseMemberDesc.addButtonListener(new IButtonListener() {

	    @Override
	    public void click() {
		cmpDesc.hide();
	    }
	});
	
    }

    @Override
    protected void beforeRender() {
	super.beforeRender();
//	IPswRoleService pswRoleService = (IPswRoleService) getBean("pswRoleService");
//	HttpServletRequest req = getPage().getRequest();
//	boolean isAdmin = pswRoleService.isAdmin(req);
//	boolean hasStaffListRole = pswRoleService.isContain(req, IPswRoleService.CUSTOMER_LIST);
//
//	if (!isAdmin && !hasStaffListRole) {
//	    String url = CustomerManager.this.getTag().getAttribute("url");
//	    if (url == null)
//		url = "bg.tiles";
//	    try {
//		getPage().getResponse().sendRedirect(url);
//		throw new RenderInterruptedException();
//	    } catch (IOException e) {
//		e.printStackTrace();
//	    }
//
//	}

	btnSearch.addUpdateComponent(txtSearch);
	btnMemberShow.addUpdateComponent(txtSelectMemberId);
	
    }

}
