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
import jWeb.service.GoodsService;

/**
 * 加载所有商品
 */
@WebServlet("/LoadGoodsServlet")
public class LoadGoodsServlet extends HttpServlet {
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
			List<Goods> goods=new ArrayList<Goods>();
			if(req.getParameter("uId")!=null) {
				//加载某一销售用户名下的商品
				String uId=req.getParameter("uId");
				String ip=(String) req.getSession().getAttribute("ip");
				String city=(String) req.getSession().getAttribute("city");
				GoodsService g=new GoodsService();
				goods=g.uidLoadGoods(uId,ip,city);
			}else {
				//加载商城所有商品
				GoodsService g=new GoodsService();
				goods=g.LoadGoods();
			}
		//响应处理结果
			System.out.println(goods);
			resp.getWriter().write(new Gson().toJson(goods));
	}

}
