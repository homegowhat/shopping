package com.shopping.web.bg.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.kuroshio.web.module.website.service.IWebsiteService;

public class ImageServlet extends HttpServlet {
	public static Object getBean(String name, ServletContext context) {
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		return ctx.getBean(name);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		System.out.println("ImageServlet webiteId = " + websiteId);
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);

		String picId = req.getParameter("id");
		String type = req.getParameter("t");
		resp.setContentType("image/jpg");
		OutputStream out = resp.getOutputStream();
		File file = null;
		// String foodMenuPic =
		// request.getServletContext().getRealPath("/WEB-INF/PIC/"+websiteId+"/FOOD_MENU_PIC");
		// String userPicPath =
		// request.getServletContext().getRealPath("/WEB-INF/PIC/"+websiteId+"/USER_PIC");
		// String newsPic =
		// request.getServletContext().getRealPath("/WEB-INF/PIC/"+websiteId+"/DOC_PIC");
		// if (type == null) {
		// file = new
		// File(getServletContext().getRealPath("/WEB-INF/PIC/"+websiteId+"/FOOD_MENU_PIC/"
		// + picId));
		// }else if(type.equalsIgnoreCase("news")){
		// file = new
		// File(getServletContext().getRealPath("/WEB-INF/PIC/"+websiteId+"/DOC_PIC/"
		// + picId));
		// }else if(type.equalsIgnoreCase("slide")){
		// file = new
		// File(getServletContext().getRealPath("/WEB-INF/PIC/"+websiteId+"/SLIDE_PIC/"
		// + picId));
		// }

		file = new File(getServletContext().getRealPath("/WEB-INF/ITEM_PIC/"  + picId));

		if (!file.exists()) {

			// XXX 這個位置還要再想過放哪邊才是最好
			if (!file.exists()) 
			file = new File(getServletContext().getRealPath("/WEB-INF/PIC/COMMON/0"));
		}
		FileInputStream in = new FileInputStream(file);

		byte[] buffer = new byte[1024];
		while (true) {
			int len = in.read(buffer);
			if (len == -1)
				break;
			out.write(buffer, 0, len);
		}
		in.close();

	}
//照理是要抽另一個servlet
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {}

}
