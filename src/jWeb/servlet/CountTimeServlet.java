package jWeb.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jWeb.service.BTService;

/**
 * 用户浏览某商品时，记录类别和时间
 */
@WebServlet("/CountTimeServlet")
public class CountTimeServlet extends HttpServlet {
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
			String uId=req.getParameter("uId");
			String time=req.getParameter("time");
			String type=req.getParameter("gType");
			BTService bt=new BTService();
			int flag=bt.insertTime(uId,type,time);
		//响应处理结果
			System.out.println("记录结果:"+flag);;
	}

}
