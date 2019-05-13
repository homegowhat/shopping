package com.shopping.web.bg.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.shopping.web.bg.service.IItemService;
import com.shopping.web.bg.service.ILoginService;
import com.shopping.web.db.dto.User;

import os.rabbit.IKeyValueProvider;

public class Upload extends HttpServlet {

	// 加此method就可以使用 spring @Autowired 等的 annotation
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	public static Object getBean(String name, ServletContext context) {
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		return ctx.getBean(name);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Enumeration<String> ll = request.getParameterNames();
//		while (ll.hasMoreElements()) {
//			System.out.println(ll.nextElement());
//		}

		// WebApplicationContext ctx =
		// WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		// IUserService userService = (IUserService) ctx.getBean("userService");

		
		ILoginService userService = (ILoginService) getBean("loginService", request.getServletContext());
		// 要驗證

Integer id = userService.getLoginId(request);
		User user = userService.getLoginUser(request);
//		System.out.println("servlet get login id = " + id);

		String foodMenuPic = request.getServletContext().getRealPath("/WEB-INF/ITEM_PIC");
		
		File d = new File(foodMenuPic);
		if(!d.exists()){
			d.mkdirs();
		}

		Enumeration<String> ee = request.getParameterNames();
		String key = null;
		IKeyValueProvider provider = (IKeyValueProvider)request.getAttribute("KEY_VALUE_PROVIDER");
//		while (ee.hasMoreElements()) {
//			key = (String) ee.nextElement();
//			System.out.println("key = " + key + ", value = " + request.getParameter(key));
//			System.out.println("key = " + key + ", value = " + provider.get(key));
//		}
		
//		System.out.println("here............");

		String gotoUrl = request.getParameter("goto");
//		System.out.println("goto, value = " + request.getParameter("goto"));
		String type = request.getParameter("type");
//		System.out.println("type, value = " + request.getParameter("type"));

		String newsId = request.getParameter("newsId");
//		System.out.println("newsId = " + newsId);
		Integer messageId = null;
		if (newsId != null)
			messageId = Integer.parseInt(newsId);

		// System.out.println("path = " + request.getRealPath("/"));
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024 * 1024 * 20);
		factory.setRepository(new File(request.getRealPath("/")));
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(1024 * 1024 * 20);
		List<FileItem> items = null;
		String theFileName = null;
		List<String> list = new ArrayList<String>();
		try {
			items = upload.parseRequest(request);
			// System.out.println("iiiitem = " + items.size());
			String newFileName = null;
			String filePath = null;
			for (Iterator<FileItem> it = items.iterator(); it.hasNext();) {
				FileItem item = (FileItem) it.next();
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString("GBK");
				} else {
					String filedName = item.getFieldName();
					String fileName = item.getName();
					theFileName = fileName;
					String contentType = item.getContentType();

					if(type != null && type.equals("item") && messageId != null){
						newFileName = UUID.randomUUID().toString().replace("-", "");
						filePath = foodMenuPic;
						IItemService itemService = (IItemService)getBean("itemService", request.getServletContext());
						itemService.addItemPic( messageId, newFileName);
					
					
					} else {
						newFileName = System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."), fileName.length());
						filePath = foodMenuPic;
					}
					list.add(newFileName);
					FileOutputStream fos = new FileOutputStream(filePath + "/" + newFileName);
					if (item.isInMemory()) {
						fos.write(item.get());
						fos.close();
					} else {
						InputStream is = item.getInputStream();
						byte[] buffer = new byte[1024];
						int len;
						while ((len = is.read(buffer)) > 0) {
							fos.write(buffer, 0, len);
						}
						is.close();
						fos.close();
					}

				}

			}
			if (gotoUrl != null) {
				response.sendRedirect(gotoUrl);
			} else {
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				// out.println("{\"result\":{\"files:\"[{\"name\":\"aa.jpg\"}]}}");
				StringBuffer buf = new StringBuffer();
				buf.append("{\"files\":[");
				for (String fid : list) {
					buf.append("{\"url\":\"" + fid + "\",\"name\":\"" + fid + "\"}");
				}
				buf.append("]}");
				// out.println("{\"files\":[{\"id\":\"" + theFileName +
				// "\",\"name\":\"" + theFileName + "\"}]}");
				out.println(buf.toString());
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
