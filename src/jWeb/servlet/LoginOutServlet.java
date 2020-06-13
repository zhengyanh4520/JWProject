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
 * �û��˳������˳���¼���뵽ip��
 */
@WebServlet("/LoginOutServlet")
public class LoginOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//������������ʽ
		req.setCharacterEncoding("utf-8");
		//������Ӧ�����ʽ
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("text/html;charset=utf-8");
		//��ȡ������Ϣ
		//����������Ϣ
			String uId=req.getParameter("uId");
			String ip=req.getParameter("ip");
			String city=req.getParameter("city");
			IpService i=new IpService();
			int flag=i.insertOutIp(uId,ip,city);
		//��Ӧ������
			resp.getWriter().write(new Gson().toJson(flag));
	}

}
