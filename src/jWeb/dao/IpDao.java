package jWeb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jWeb.jdbcContent.jdbcConnect;
import jWeb.pojo.Ip;
import jWeb.rand.randNo;

public class IpDao {

	//查询登录/退出记录
	public List<Ip> searchIp(String uId, String ip, String city, int uType) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		List<Ip> list=new ArrayList<Ip>();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="select * "
					+ " from ipTable"
					+ " where uId=?"
					+ " order by iTime Desc";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, uId);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
			while(rs.next()) 
			{
				Ip i=new Ip();
				i.setiId(rs.getString("iId"));
				i.setuId(rs.getString("uId"));
				i.setIp(rs.getString("ip"));
				i.setCity(rs.getString("city"));
				i.setiType(rs.getString("iType"));
				i.setiTime(rs.getString("iTime"));
				list.add(i);
			}
			if(uType!=0) {
				//插入操作日志
				String concent="查询登录/退出记录";
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
				ps.setString(2, uId);
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
		return list;
	}

	//插入退出记录
	public int insertOutIp(String uId, String ip, String city) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		int flag=0;
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建sql命令
			String sql="insert into ipTable(iId,uId,ip,city,iType,iTime) values(?,?,?,?,?,?)";
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
			ps.setString(5, "退出");
			ps.setString(6, time);
			//执行
			flag=ps.executeUpdate();
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return flag;
	}

}
