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
 * 查找某一商品
 */
@WebServlet("/SearchGoodsServlet")
public class SearchGoodsServlet extends HttpServlet {
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
			String gName="";
			String gClass="";
			String gId="";
			Goods goods=new Goods();
			List<Goods> g=new ArrayList<Goods>();
			if(req.getParameter("gName")!=null)
			{
				gName=req.getParameter("gName");
				System.out.println("gName:"+gName);
				GoodsService u=new GoodsService();
				g=u.findGoods(gName);
			}else if(req.getParameter("gClass")!=null) {
				gClass=req.getParameter("gClass");
				System.out.println("gClass:"+gClass);
				GoodsService u=new GoodsService();
				g=u.findClassGoods(gClass);
			}else if(req.getParameter("gId")!=null) {
				gId=req.getParameter("gId");
				String uId=req.getParameter("uId");
				System.out.println("gId:"+gId +" uId:"+uId);
				GoodsService u=new GoodsService();
				goods=u.findIdGoods(uId,gId);
				req.getSession().setAttribute("goodsDetail", goods);
			}
		
		//响应处理结果
			System.out.println(g);
			resp.getWriter().write(new Gson().toJson(g));
	}
       


}
