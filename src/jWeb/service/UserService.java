package jWeb.service;

import java.util.List;

import jWeb.dao.UserDao;
import jWeb.pojo.User;

public class UserService {
	
	private UserDao u=new UserDao();
	
	//用户注册
	public int checkRegisterUser(String uId, String uName, String uIntroduce,
			String uPsw, int uType, String uSex, String uPhone, String uEmail, String ip, String city) {
		return u.checkRegisterUser(uId, uName,uIntroduce,uPsw,uType,uSex,uPhone,uEmail,ip,city);
	}
	
	//用户登录
	public int checkLoginUser(String uId, String uPsw, int uType, String ip, String city) {
		return u.checkLoginUser(uId,uPsw,uType,ip,city);
	}

	//返回某一用户所有信息
	public User findUser(String uId) {
		return u.findUser(uId);
	}

	//修改信息,i=1时代表管理员在操作，0时是账户自己操作
	public int changeInfo(User u2, String ip, String city, int i) {
		return u.changeInfo(u2,ip,city,i);
	}

	//用户充值
	public User recharge(String uId, int money) {
		return u.recharge(uId,money);
	}

	//购买减少用户余额
	public User reduceMoney(String uId, int bp) {
		return u.reduceMoney(uId,bp);
	}

	//获取销售人员列表
	public List<User> getSaleList(String ip, String city) {
		return u.getSaleList(ip,city);
	}

	//删除某一账户
	public int deleteSaler(String uId, String ip, String city) {
		return u.deleteSaler(uId,ip,city);
	}

}
