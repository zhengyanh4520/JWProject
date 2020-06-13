package jWeb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jWeb.jdbcContent.jdbcConnect;
import jWeb.pojo.User;
import jWeb.rand.randNo;

public class UserDao {

	 //注册用户uType=0，管理员注册的销售人员uType=1，若可注册返回1，否则为0
	public int checkRegisterUser(String uId, String uName, String uIntroduce, String uPsw, int uType, String uSex, String uPhone, String uEmail, String ip, String city) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		int judge=0;
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="select uId from user";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			//执行
			rs=ps.executeQuery();
			//遍历执行结果,是否有重复uId
			while(rs.next()) 
			{
				if(uId.equals(rs.getString("uId"))) 
				{
					judge=0;
					jdbcConnect.delete(rs, ps, conn);
					return judge;
				}
			}
			//无重复，uId可用
			sql="insert into user(uId,uName,uPsw,uIntroduce,uType,uSex,uEmail,uPhone,uMoney) values(?,?,?,?,?,?,?,?,?)";
			ps.close();
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, uId);
			ps.setString(2, uName);
			ps.setString(3, uPsw);
			ps.setString(4, uIntroduce);
			ps.setInt(5, uType);
			ps.setString(6, uSex);
			ps.setString(7, uEmail);
			ps.setString(8, uPhone);
			ps.setFloat(9, 0);
			//执行
			judge=ps.executeUpdate();
			
			if(uType!=0) {
				//插入操作日志
				String concent="添加销售人员，主要信息：账户:"+uId+"，用户名:"+uName+"，性别:"+uSex+"，电话:"+uPhone+"，邮箱:"+uEmail;
				sql="insert into operationLog(oId,uId,oContent,oIp,oCity,oTime) values(?,?,?,?,?,?)";
				//创建sql命令对象
				ps.close();
				ps=conn.prepareStatement(sql);
				//获得随机主键
				randNo r=new randNo();
				String oId=r.createOtherNo();
				//获取当前时间
		        Date date = new Date();
		    	SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    	String time=dateFormat.format(date);
				//给占位符赋值
				ps.setString(1, oId);
				ps.setString(2, "admin");
				ps.setString(3, concent);
				ps.setString(4, ip);
				ps.setString(5, city);
				ps.setString(6, time);
				//执行
				ps.executeUpdate();
				System.out.println("插入日志："+concent);
			}
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return judge;
	}

	/*用户/销售人员/管理员登录，无错返回0，无此用户名返回1，密码错误返回2
	 *并在此时将登录记录插入ip表
	 */
	public int checkLoginUser(String uId, String uPsw, int uType, String ip, String city) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		int judge=1;
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="select * from user where uId=? and uType=?";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, uId);
			ps.setInt(2, uType);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
			while(rs.next()) 
			{
				if(uPsw.equals(rs.getString("uPsw"))){
					judge=0;
					sql="insert into ipTable(iId,uId,ip,city,iType,iTime) values(?,?,?,?,?,?)";
					//创建sql命令对象
					ps=conn.prepareStatement(sql);
					//获得随机主键
					randNo r=new randNo();
					String iId=r.createOtherNo();
					//获取当前时间
			        Date date = new Date();
			    	SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    	String time=dateFormat.format(date);
					//给占位符赋值
					ps.setString(1, iId);
					ps.setString(2, uId);
					ps.setString(3, ip);
					ps.setString(4, city);
					ps.setString(5, "登录");
					ps.setString(6, time);
					//执行
					ps.executeUpdate();
					//关闭资源
					jdbcConnect.delete(rs, ps, conn);
					return judge;
				}else {
					judge=2;
				}
			}
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return judge;
	}

	//返回某一用户所有信息
	public User findUser(String uId) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		User user=new User();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="select * from user where uId=?";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, uId);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
			while(rs.next()) 
			{
				user.setuId(rs.getString("uId"));
				user.setuName(rs.getString("uName"));
				user.setuIntroduce(rs.getString("uIntroduce"));
				user.setuSex(rs.getString("uSex"));
				user.setuPhone(rs.getString("uPhone"));
				user.setuEmail(rs.getString("uEmail"));
				user.setuPsw(rs.getString("uPsw"));
				user.setuType(rs.getInt("uType"));
				user.setuMoney(rs.getInt("uMoney"));
			}
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return user;
	}

	//修改某一用户信息,i=1时代表管理员在操作，0时是账户自己操作
	public int changeInfo(User u2, String ip, String city, int i) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		//声明数据存储对象
		int judge=0;
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="update user set uName=?,uPsw=?,uIntroduce=?,uType=?,uSex=?,uEmail=?,uPhone=? where uId=?";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, u2.getuName());
			ps.setString(2, u2.getuPsw());
			ps.setString(3, u2.getuIntroduce());
			ps.setInt(4, u2.getuType());
			ps.setString(5, u2.getuSex());
			ps.setString(6, u2.getuEmail());
			ps.setString(7, u2.getuPhone());
			ps.setString(8, u2.getuId());
			//执行
			judge=ps.executeUpdate();
			
			if(u2.getuType()!=0) {
				//插入操作日志
				String concent="修改个人信息，主要信息：用户名:"+u2.getuName()+"，性别:"+u2.getuSex()+"，电话:"+u2.getuPhone()+"，邮箱:"+u2.getuEmail();
				sql="insert into operationLog(oId,uId,oContent,oIp,oCity,oTime) values(?,?,?,?,?,?)";
				//创建sql命令对象
				ps.close();
				ps=conn.prepareStatement(sql);
				//获得随机主键
				randNo r=new randNo();
				String oId=r.createOtherNo();
				//获取当前时间
		        Date date = new Date();
		    	SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    	String time=dateFormat.format(date);
				//给占位符赋值
				ps.setString(1, oId);
				String id="";
				if(i==1) {
					//此时是管理员操作的修改
					id="admin";
				}else {
					id=u2.getuId();
				}
				ps.setString(2, id);
				ps.setString(3, concent);
				ps.setString(4, ip);
				ps.setString(5, city);
				ps.setString(6, time);
				//执行
				ps.executeUpdate();
				System.out.println("插入日志："+concent);
			}
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(ps, conn);
		}
		return judge;
	}

	//用户充值
	public User recharge(String uId, int money) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		User u=new User();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="update user set uMoney=uMoney+? where uId=?";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setInt(1, money);
			ps.setString(2, uId);
			//执行
			ps.executeUpdate();
			//重新取除
			sql="select * from user where uId=?";
			ps.close();
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, uId);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
			while(rs.next()) 
			{
				u.setuId(rs.getString("uId"));
				u.setuName(rs.getString("uName"));
				u.setuIntroduce(rs.getString("uIntroduce"));
				u.setuSex(rs.getString("uSex"));
				u.setuPhone(rs.getString("uPhone"));
				u.setuEmail(rs.getString("uEmail"));
				u.setuPsw(rs.getString("uPsw"));
				u.setuType(rs.getInt("uType"));
				u.setuMoney(rs.getInt("uMoney"));
			}
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(ps, conn);
		}
		return u;
	}

	//购买减少用户余额
	public User reduceMoney(String uId, int bp) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		User u=new User();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="update user set uMoney=uMoney-? where uId=?";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setInt(1, bp);
			ps.setString(2, uId);
			//执行
			ps.executeUpdate();
			//重新取除
			sql="select * from user where uId=?";
			ps.close();
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, uId);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
			while(rs.next()) 
			{
				u.setuId(rs.getString("uId"));
				u.setuName(rs.getString("uName"));
				u.setuIntroduce(rs.getString("uIntroduce"));
				u.setuSex(rs.getString("uSex"));
				u.setuPhone(rs.getString("uPhone"));
				u.setuEmail(rs.getString("uEmail"));
				u.setuPsw(rs.getString("uPsw"));
				u.setuType(rs.getInt("uType"));
				u.setuMoney(rs.getInt("uMoney"));
			}
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(ps, conn);
		}
		return u;
	}

	//获取销售人员列表
	public List<User> getSaleList(String ip, String city) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		List<User> list=new ArrayList<User>();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="select * from user where uType=1";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
			while(rs.next()) 
			{
				User user=new User();
				user.setuId(rs.getString("uId"));
				user.setuName(rs.getString("uName"));
				user.setuIntroduce(rs.getString("uIntroduce"));
				user.setuSex(rs.getString("uSex"));
				user.setuPhone(rs.getString("uPhone"));
				user.setuEmail(rs.getString("uEmail"));
				user.setuPsw(rs.getString("uPsw"));
				user.setuType(rs.getInt("uType"));
				user.setuMoney(rs.getInt("uMoney"));
				list.add(user);
			}
			//插入操作日志
			String concent="管理员查询销售人员列表";
			sql="insert into operationLog(oId,uId,oContent,oIp,oCity,oTime) values(?,?,?,?,?,?)";
			//创建sql命令对象
			ps.close();
			ps=conn.prepareStatement(sql);
			//获得随机主键
			randNo r=new randNo();
			String oId=r.createOtherNo();
			//获取当前时间
	        Date date = new Date();
	    	SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	String time=dateFormat.format(date);
			//给占位符赋值
			ps.setString(1, oId);
			ps.setString(2, "admin");
			ps.setString(3, concent);
			ps.setString(4, ip);
			ps.setString(5, city);
			ps.setString(6, time);
			//执行
			ps.executeUpdate();
			System.out.println("插入日志："+concent);
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return list;
	}

	//删除某一账户
	public int deleteSaler(String uId, String ip, String city) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		//声明数据存储对象
		int flag=0;
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="delete from user where uId=?";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, uId);
			//执行
			flag=ps.executeUpdate();
			
			//插入操作日志
			String concent="删除销售人员，主要信息：账户:"+uId;
			sql="insert into operationLog(oId,uId,oContent,oIp,oCity,oTime) values(?,?,?,?,?,?)";
			//创建sql命令对象
			ps.close();
			ps=conn.prepareStatement(sql);
			//获得随机主键
			randNo r=new randNo();
			String oId=r.createOtherNo();
			//获取当前时间
	        Date date = new Date();
	    	SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	String time=dateFormat.format(date);
			//给占位符赋值
			ps.setString(1, oId);
			ps.setString(2, "admin");
			ps.setString(3, concent);
			ps.setString(4, ip);
			ps.setString(5, city);
			ps.setString(6, time);
			//执行
			ps.executeUpdate();
			System.out.println("插入日志："+concent);
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(ps, conn);
		}
		return flag;
	}
	
}
