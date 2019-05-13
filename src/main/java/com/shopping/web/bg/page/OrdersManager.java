package com.shopping.web.bg.page;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import os.rabbit.component.PageNavigator;
import os.rabbit.component.test.YmPicker;
import os.rabbit.components.Component;
import os.rabbit.components.ComponentList;
import os.rabbit.components.IAuthorizationValidator;
import os.rabbit.components.IComponentListener;
import os.rabbit.components.IListListener;
import os.rabbit.components.Label;
import os.rabbit.components.Link;
import os.rabbit.components.SpringBeanSupportComponent;
import os.rabbit.components.WebPage;
import os.rabbit.components.ajax.AjaxButton;
import os.rabbit.components.form.TextBox;
import os.rabbit.parser.Tag;

import com.kuroshio.web.module.login.service.ILoginService;
import com.kuroshio.web.module.website.service.IWebsiteService;
import com.kuroshio.web.module.xxx.bean.Inventory;
import com.kuroshio.web.module.xxx.bean.Member;
import com.kuroshio.web.module.xxx.bean.SalesOrders;
import com.kuroshio.web.module.xxx.bean.SalesOrdersItem;
import com.kuroshio.web.module.xxx.service.ISssService;
import com.kuroshio.web.module.xxx.service.IWwwService;
import com.kuroshio.web.module.xxx.service.IXxxService;

/**
 * @author homegowhat
 * 
 */
public class OrdersManager extends SpringBeanSupportComponent {
	private DecimalFormat dFormat = new DecimalFormat("###,###,###");
	private DecimalFormat mFormat = new DecimalFormat("#########");
	private SimpleDateFormat sFormat = new SimpleDateFormat("yyyy/MM/dd");
	// 訂單列表
	private YmPicker txtYmDate;
	private TextBox txtEditOrderDate;
	private AjaxButton btnShowAdd;
	private Component cmpOrders;
	private ComponentList<SalesOrders> listOrders;
//	private AjaxButton btnShowDetail;
	private Label lblNo;
	private Label lblCustomer;
	private Label lblMoney;
	private Label lblPayWay;
	private Label lblTel;
	private Label lblCellphone;
	private Label lblAddress;
//	private Label lblSales;
	private Label lblOrderDate;
	private ComponentList<SalesOrdersItem> listOrderItem;
	private Label lblOrderItemName;
	private Label lblOrderItemCount;
//	private Label lblPayDate;
//	private AjaxButton btnPayStatus;
//	private Label lblWorkStatus;
//	private Label lblPayStatus;
//	private AjaxButton btnWorkPrint;
//	private Label lblTax;
//	private AjaxButton btnTax;
	private Link btnShowEdit;
	private PageNavigator downPageCode;

	// 變更狀態
	
	

	public OrdersManager(Tag tag) {
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

		cmpOrders.addComponentListener(new IComponentListener() {

			@Override
			public void afterBuild() {

			}

			@Override
			public void afterRender() {

			}

			@Override
			public void beforeRender() {

				HttpServletRequest req = getPage().getRequest();
//				String websiteId = OrdersManager.this.getTag().getAttribute("websiteId");
				IWebsiteService websiteService = (IWebsiteService)getBean("websiteService");
				String websiteId = websiteService.getWebsiteId(getPage().getRequest());
				Integer pageCode = getPage().getParameterAsInteger("pageCode");
				if (pageCode == null) {
					pageCode = 1;
				}
				Integer year = getPage().getParameterAsInteger("year");
				Integer month = getPage().getParameterAsInteger("month");
				
				
				
				Calendar c = Calendar.getInstance();
				if (year == null)
					year = c.get(Calendar.YEAR);
				else
					c.set(Calendar.YEAR, year);
				if (month == null)
					month = (c.get(Calendar.MONTH));
				else
					c.set(Calendar.MONTH, month - 1);
				
				
				if(txtEditOrderDate.getValue()!= null){
					try {
						c.setTime(sFormat.parse(txtEditOrderDate.getValue()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
				Date d = c.getTime();
				ISssService salesService = (ISssService) getBean("sssService");
				long totalRowCount = salesService.getOrdersCount(websiteId, req, d);
//				downPageCode.useTiles();
				downPageCode.setValue(pageCode);
				downPageCode.setTotalRowCount(totalRowCount);
				downPageCode.setType("down");
				List<SalesOrders> list = salesService.getSalesOrdersList(websiteId, req, d, pageCode, downPageCode.getPageSize());
				listOrders.setCollection(list);

			}

			@Override
			public void initial() {
			    // TODO
			    
			}

		});

		listOrders.setListListener(new IListListener<SalesOrders>() {

			@Override
			public void afterEachRender(PrintWriter writer, SalesOrders so, int index) {

			}

			@Override
			public void beforeEachRender(PrintWriter writer, SalesOrders so, int index) {
				lblNo.setValue(so.getNo());
				lblOrderDate.setValue(sFormat.format(so.getOrderDate()));
				IXxxService memberService = (IXxxService) getBean("xxxService");
				Member m = memberService.getMember(so.getCustomerId());
				//TODO 這裡不該會有是null的問題
				if(m != null){
				lblCustomer.setValue(m.getName());
				lblMoney.setValue(dFormat.format(so.getMoney()));
				lblPayWay.setValue(so.getPayWay());
				lblTel.setValue(m.getTel());
				lblCellphone.setValue(m.getCellphone());
				lblAddress.setValue(m.getAddress());
				}
				btnShowEdit.setRedirectURL(btnShowEdit.getTag().getAttribute("href")+"?id="+so.getId());
				ISssService salesService = (ISssService)getBean("sssService");
				 List<SalesOrdersItem> list = salesService.getSalesOrdersItemList(getPage().getRequest(), so.getId());
				 listOrderItem.setCollection(list);
				   
				 
			}

			@Override
			public void emptyRender(PrintWriter writer) {
			    writer.write("<tr class=\"listRow\"><td  colspan=\"10\" align=\"center\" >目前尚無資料</td></tr>");
				   
			}

		});

		listOrderItem.setListListener(new IListListener<SalesOrdersItem>() {

			    @Override
			    public void afterEachRender(PrintWriter arg0, SalesOrdersItem arg1, int arg2) {
				// TODO
				
			    }

			    @Override
			    public void beforeEachRender(PrintWriter arg0, SalesOrdersItem item, int arg2) {
				IWwwService whs = (IWwwService)getBean("wwwService");
				Inventory inventory = whs.getInventory(getPage().getRequest(), item.getProductId());
				lblOrderItemName.setValue(inventory.getName());
				lblOrderItemCount.setValue(String.valueOf(item.getProductCount()));
			    }

			    @Override
			    public void emptyRender(PrintWriter arg0) {
				// TODO
				
			    }
		});



	}

	@Override
	protected void beforeRender() {
		super.beforeRender();

		Calendar c = Calendar.getInstance();

		Integer year = getPage().getParameterAsInteger("year");
		Integer month = getPage().getParameterAsInteger("month");
		if (year == null)
			year = c.get(Calendar.YEAR);
		if (month == null)
			month = (c.get(Calendar.MONTH) + 1);

		txtYmDate.setValue(year + "/" + month);
		txtYmDate.setYearLabel("年");
		txtYmDate.setMonthLabel("月");
		txtYmDate.setYearPickerLabel("選擇年");
		txtYmDate.setMonthPickerLabel("選擇月");


		String url = getPage().getRequestURI();
		String[] ar = url.split("/");
		url = ar[ar.length - 1];
//		url = url.replace("rbt", "tiles");
		StringBuffer buf = new StringBuffer();
		buf.append("var value = $(\"#txtYmDate\").val();");
		buf.append("var ar = value.split(\"/\");");
		buf.append("var year = ar[0];");
		buf.append("var month = ar[1];");
		buf.append("window.location.href=\"" + url + "?year=\" + year+\"&month=\"+month;");
		txtYmDate.setMonthOverScript(buf.toString());
		
		
		
	}




	

}
