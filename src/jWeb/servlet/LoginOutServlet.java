package jWeb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import jWeb.service.IpService;

/**
 * 用户退出，将退出记录加入到ip表
 */
@WebServlet("/LoginOutServlet")
public class LoginOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//设置请求编码格式
		req.setCharacterEncoding("utf-8");
		//设置响应编码格式
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("text/html;charset=utf-8");
		//获取请求信息
		//处理请求信息
			String uId=req.getParameter("uId");
			String ip=req.getParameter("ip");
			String city=req.getParameter("city");
			IpService i=new IpService();
			int flag=i.insertOutIp(uId,ip,city);
		//响应处理结果
			resp.getWriter().write(new Gson().toJson(flag));
	}

}
