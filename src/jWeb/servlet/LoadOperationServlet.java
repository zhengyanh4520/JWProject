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

import jWeb.pojo.OperationLog;
import jWeb.service.OLService;

/**
 * 加载操作日志,当用户为管理员时,uType=2
 * 此时可以看到所有销售人员及管理员的日志
 * 否则只能看到销售人员自己的操作日志
 */
@WebServlet("/LoadOperationServlet")
public class LoadOperationServlet extends HttpServlet {
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
			String ip=(String) req.getSession().getAttribute("ip");
			String city=(String) req.getSession().getAttribute("city");
			String uId=(String) req.getParameter("uId");
			String uType=(String) req.getParameter("uType");
			List<OperationLog> list=new ArrayList<OperationLog>();
			OLService o=new OLService();
			list=o.loadOperationLog(uId,ip,city,uType);
		//响应处理结果
			resp.getWriter().write(new Gson().toJson(list));
	}

}
