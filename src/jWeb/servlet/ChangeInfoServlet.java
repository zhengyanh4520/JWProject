package jWeb.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import jWeb.pojo.User;
import jWeb.service.UserService;

/**
 * �޸ĸ�����Ϣ
 */
@WebServlet("/ChangeInfoServlet")
public class ChangeInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//������������ʽ
		req.setCharacterEncoding("utf-8");
		//������Ӧ�����ʽ
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("text/html;charset=utf-8");
		//��ȡ������Ϣ
			String ip=(String) req.getSession().getAttribute("ip");
			String city=(String) req.getSession().getAttribute("city");
			User user=(User) req.getSession().getAttribute("user");
			String operation=req.getParameter("operation");
			
			User u=new User();
			u.setuId(req.getParameter("uId"));
			u.setuEmail(req.getParameter("uEmail"));
			u.setuName(req.getParameter("uName"));
			u.setuIntroduce(req.getParameter("uIntroduce"));
			u.setuPsw(req.getParameter("uPsw"));
			u.setuPhone(req.getParameter("uPhone"));
			u.setuType(Integer.parseInt(req.getParameter("uType")));
			u.setuMoney(user.getuMoney());
			if("man".equals(req.getParameter("uSex"))) {
				u.setuSex("��");
			}else {
				u.setuSex("Ů");
			}
			
			UserService us=new UserService();
			int flag=0;
			if(operation.equals("admin")) {
				//����������Ϣ
				flag=us.changeInfo(u,ip,city,1);
			}else {
				flag=us.changeInfo(u,ip,city,0);
				req.getSession().setAttribute("user", u);
			}
			//��Ӧ�������
			resp.getWriter().write(new Gson().toJson(flag));
	}
       


}