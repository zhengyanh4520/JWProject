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
 * 推荐系统,获取用户的id,由此在浏览时间表中
 * 找到和该用户相似的人,获取他的浏览记录和购买记录
 * 得到推荐给用户的商品
 */
@WebServlet("/RecommendServlet")
public class RecommendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//设置请求编码格式
		req.setCharacterEncoding("utf-8");
		//设置响应编码格式
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		//获取请求信息
		User u=(User) req.getSession().getAttribute("user");
		//处理请求信息
			//加载商城所有商品
			List<Goods> goods=new ArrayList<Goods>();
			GoodsService g=new GoodsService();
			goods=g.LoadRecommengGoods(u.getuId());
		//响应处理结果
			resp.getWriter().write(new Gson().toJson(goods));
	}
}
