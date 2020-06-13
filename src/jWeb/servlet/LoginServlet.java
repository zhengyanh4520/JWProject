package jWeb.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import jWeb.pojo.User;
import jWeb.service.UserService;

/**
 * 用户登录
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//设置请求编码格式
		req.setCharacterEncoding("utf-8");
		//设置响应编码格式
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("text/html;charset=utf-8");
		//获取请求信息
			String uId=req.getParameter("uId");
			String uPsw=req.getParameter("uPsw");
			int uType=0;
			if("user".equals(req.getParameter("uType"))){
				uType=0;
			}else if("sale".equals(req.getParameter("uType"))){
				//销售人员
				uType=1;
			}else {
				//管理员
				uType=2;
			}
			String ip=req.getParameter("ip");
			String city=req.getParameter("city");
		//处理请求信息
			UserService u=new UserService();
			int flag=u.checkLoginUser(uId,uPsw,uType,ip,city);
		//响应处理结果
			if(flag==0) {
				User user=u.findUser(uId);
				System.out.println("登录成功");
				req.getSession().setAttribute("user", user);
				req.getSession().setAttribute("ip", ip);
				req.getSession().setAttribute("city", city);
			}else {
				System.out.println("账号/密码错误 "+flag);
				resp.getWriter().write(new Gson().toJson(flag));
			}		
	}



}
