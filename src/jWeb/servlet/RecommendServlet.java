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
import jWeb.pojo.User;
import jWeb.service.GoodsService;

/**
 * �Ƽ�ϵͳ,��ȡ�û���id,�ɴ������ʱ�����
 * �ҵ��͸��û����Ƶ���,��ȡ���������¼�͹����¼
 * �õ��Ƽ����û�����Ʒ
 */
@WebServlet("/RecommendServlet")
public class RecommendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//������������ʽ
		req.setCharacterEncoding("utf-8");
		//������Ӧ�����ʽ
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		//��ȡ������Ϣ
		User u=(User) req.getSession().getAttribute("user");
		//����������Ϣ
			//�����̳�������Ʒ
			List<Goods> goods=new ArrayList<Goods>();
			GoodsService g=new GoodsService();
			goods=g.LoadRecommengGoods(u.getuId());
		//��Ӧ������
			resp.getWriter().write(new Gson().toJson(goods));
	}
}
