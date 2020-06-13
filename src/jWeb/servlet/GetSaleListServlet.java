package jWeb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import jWeb.pojo.User;
import jWeb.service.UserService;

/**
 * 获取所有销售人员列表
 */
@WebServlet("/GetSaleListServlet")
public class GetSaleListServlet extends HttpServlet {
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
			String ip=(String) req.getSession().getAttribute("ip");
			String city=(String) req.getSession().getAttribute("city");
			List<User> list=new ArrayList<User>();
			UserService u=new UserService();
			list=u.getSaleList(ip,city);
		//响应处理结果
			resp.getWriter().write(new Gson().toJson(list));
	}

}
