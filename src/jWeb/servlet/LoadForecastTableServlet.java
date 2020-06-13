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

import jWeb.pojo.Goods;
import jWeb.service.BOBService;

/**
 * ����Ԥ�����Ʒ�б�
 */
@WebServlet("/LoadForecastTableServlet")
public class LoadForecastTableServlet extends HttpServlet {
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
			List<Goods> list=new ArrayList<Goods>();
			BOBService b=new BOBService();
			list=b.LoadForecastlTable(ip,city);
		//��Ӧ������
			resp.getWriter().write(new Gson().toJson(list));
	}

}
