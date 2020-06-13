package jWeb.servlet;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import jWeb.sendMail.MailUtils;
import jWeb.service.UserService;

/**
 * 用户注册
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
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
			String uName=req.getParameter("uName");
			String uIntroduce=req.getParameter("uIntroduce");
			String uPsw=req.getParameter("uPsw");
			String uSex="";
			if("man".equals(req.getParameter("uSex"))) {
				uSex="男";
			}else {
				uSex="女";
			}
			String uPhone=req.getParameter("uPhone");
			String uEmail=req.getParameter("uEmail");
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
		//处理请求信息
			String ip=(String) req.getSession().getAttribute("ip");
			String city=(String) req.getSession().getAttribute("city");
			UserService u=new UserService();
			int flag=u.checkRegisterUser(uId,uName,uIntroduce,uPsw,uType,uSex,uPhone,uEmail,ip,city);
		//响应处理结果
			if(flag==1)
			{
				String msg="欢迎注册百货商店，现在你可以登录并浏览网页了！";
				try {
					MailUtils.sendMail(uEmail, msg);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
			resp.getWriter().write(new Gson().toJson(flag));
	}
       


}
