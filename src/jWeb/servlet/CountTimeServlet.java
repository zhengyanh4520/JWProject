package jWeb.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jWeb.service.BTService;

/**
 * �û����ĳ��Ʒʱ����¼����ʱ��
 */
@WebServlet("/CountTimeServlet")
public class CountTimeServlet extends HttpServlet {
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
			String time=req.getParameter("time");
			String type=req.getParameter("gType");
			BTService bt=new BTService();
			int flag=bt.insertTime(uId,type,time);
		//��Ӧ������
			System.out.println("��¼���:"+flag);;
	}

}
