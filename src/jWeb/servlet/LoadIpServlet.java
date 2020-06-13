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

import jWeb.pojo.Ip;
import jWeb.pojo.User;
import jWeb.service.IpService;

/**
 * �����û���¼/�˳�Ip
 */
@WebServlet("/LoadIpServlet")
public class LoadIpServlet extends HttpServlet {
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
			String ip=(String) req.getSession().getAttribute("ip");
			String city=(String) req.getSession().getAttribute("city");
			User u=(User) req.getSession().getAttribute("user");
			List<Ip> list=new ArrayList<Ip>();
			String uId=req.getParameter("uId");
			IpService i=new IpService();
			list=i.searchIp(uId,ip,city,u.getuType());
		//��Ӧ������
			resp.getWriter().write(new Gson().toJson(list));
	}

}
