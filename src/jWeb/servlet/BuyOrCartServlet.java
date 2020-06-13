package jWeb.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import jWeb.pojo.BuyOrBrowse;
import jWeb.pojo.Goods;
import jWeb.pojo.User;
import jWeb.rand.randNo;
import jWeb.sendMail.MailUtils;
import jWeb.service.BOBService;
import jWeb.service.GoodsService;
import jWeb.service.UserService;

/**
 * 插入购物车/购买记录，当bType为1时加入购物车，当bType为2时购买
 * 当有传入bId时是在购物车中购买
 */
@WebServlet("/BuyOrCartServlet")
public class BuyOrCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//设置请求编码格式
		req.setCharacterEncoding("utf-8");
		//设置响应编码格式
			resp.setCharacterEncoding("utf-8");
			resp.setContentType("text/html;charset=utf-8");
		//获取请求信息
			int flag=0;
			//商城进行加入购物车操作或直接购买操作
			if(req.getParameter("bId")==null) {
				BuyOrBrowse b=new BuyOrBrowse();
				//获取bId主键
				randNo r=new randNo();
				//获取当前时间
		        Date date = new Date();
		    	SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    	String time=dateFormat.format(date);
		    	//需要使用的数据
		    	String uId=req.getParameter("uId");
		    	String gId=req.getParameter("gId");
		    	int bType=Integer.parseInt(req.getParameter("bType"));
		    	int p=Integer.parseInt(req.getParameter("gPrice"));
		    	//购买/加入数量
				int n=Integer.parseInt(req.getParameter("buyNumber"));
				//总价
				int bp=n*p;
				b.setbId(r.createOtherNo());
				b.setbTime(time);
				b.setuId(uId);
				b.setgId(gId);
				b.setbType(bType);
				b.setbPrice(bp);
				b.setbNumber(n);
			//处理请求信息
				BOBService bob=new BOBService();
				flag=bob.AddRecoding(b);
				if(bType==2) {
					UserService u=new UserService();
					User user=u.reduceMoney(uId,bp);
					req.getSession().setAttribute("user", user);
					GoodsService g=new GoodsService();
					Goods goods=g.reduceNumber(gId,n);
					req.getSession().setAttribute("goodsDetail", goods);

					//发送邮件
					String msg="本次您在账号为:"+uId+"，用户名为："+user.getuName()+"的账户上"
							+ "成功购买了"+n+"件商品，"+"其商品编号为："+gId+"商品名为："+goods.getgName()
							+",总共花费:"+bp+"元！";
					System.out.println(msg);
					try {
						MailUtils.sendMail(user.getuEmail(), msg);
					} catch (MessagingException e) {
						e.printStackTrace();
					}
				}else {
					System.out.println("uId:"+uId+"加入购物车"+n+"件"+"gId:"+gId+"共:"+bp+"元");
				}
			}
			//购物车中购买
			else {
				String bId=req.getParameter("bId");
				String uId=req.getParameter("uId");
				//总价
				int bp=Integer.parseInt(req.getParameter("bPrice"));
				//总数
				int n=Integer.parseInt(req.getParameter("bNumber"));
				//找到这条记录的信息
				BOBService bob=new BOBService();
				BuyOrBrowse b=bob.findRecoding(bId);
				System.out.println(b);
				
				String gId=b.getgId();
				//找到这个人
				UserService u=new UserService();
				User user=u.findUser(uId);
				System.out.println(user);
				if(user.getuMoney()<bp) {
					//钱不够，结束
					flag=2;
				}else {
					//找到这个货物
					GoodsService g=new GoodsService();
					Goods goods=g.findGoodsUseId(gId);
					System.out.println(goods);
					if(goods.getgNumber()<n) {
						//数量不足，结束
						flag=1;
					}else {
						//可以买
						//删除原购物车记录
						flag=bob.deleteRecoding(bId);
						//减去商品数量
						g.reduceNumber(gId, n);
						//化为购买记录
						b.setbType(2);
						//获取新bId主键
						randNo r=new randNo();
						b.setbId(r.createOtherNo());
						//获取当前时间
				        Date date = new Date();
				    	SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				    	String time=dateFormat.format(date);
				    	b.setbTime(time);
				    	//插入
						bob.AddRecoding(b);
						System.out.println(b);
						//结束
						flag=0;
						
						//发送邮件
						String msg="本次您在账号为:"+uId+"，用户名为："+user.getuName()+"的账户上"
								+ "成功购买了"+n+"件商品，"+"其商品编号为："+gId+"商品名为："+goods.getgName()
								+",总共花费:"+bp+"元！";
						System.out.println(msg);
						try {
							MailUtils.sendMail(user.getuEmail(), msg);
						} catch (MessagingException e) {
							e.printStackTrace();
						}
					}
				}
			}
		//响应处理结果
			resp.getWriter().write(new Gson().toJson(flag));
	}
       


}
