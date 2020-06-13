package jWeb.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import jWeb.service.BOBService;

/**
 * 移除购物车中的记录
 */
@WebServlet("/DeleteCartServlet")
public class DeleteCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//设置请求编码格式
		req.setCharacterEncoding("utf-8");
		//设置响应编码格式
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("text/html;charset=utf-8");
		//获取请求信息
			String bId=req.getParameter("bId");
		//处理请求信息
			BOBService b=new BOBService();
			int flag=b.deleteRecoding(bId);
			System.out.println("删除商品gId："+bId+"flag:"+flag+"(0失败1成功)");
		//响应处理结果
			resp.getWriter().write(new Gson().toJson(flag));
	}

}
