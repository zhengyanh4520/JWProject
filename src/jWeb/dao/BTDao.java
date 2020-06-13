package jWeb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jWeb.jdbcContent.jdbcConnect;
import jWeb.pojo.BrowseTime;
import jWeb.rand.randNo;

public class BTDao {

	//记录用户浏览类别及时间
	public int insertTime(String uId, String type, String time) {
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
			String sql="update time_browse set tTime=tTime+? where uId=? and tType=?";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			int t=Integer.parseInt(time);
			ps.setInt(1, t);
			ps.setString(2, uId);
			ps.setString(3, type);
			//执行
			flag=ps.executeUpdate();
			if(flag==0) {
				//创建sql命令
				sql="insert into time_browse(tId,uId,tType,tTime) values(?,?,?,?)";
				//创建sql命令对象
				ps.close();
				ps=conn.prepareStatement(sql);
				//获得随机主键
				randNo r=new randNo();
				String tId=r.createOtherNo();
				//给占位符赋值
				ps.setString(1, tId);
				ps.setString(2, uId);
				ps.setString(3, type);
				ps.setInt(4, t);
				//执行
				flag=ps.executeUpdate();
			}
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return flag;
	}

	//根据浏览时间的用户画像
	public List<BrowseTime> loadByTime(String ip, String city) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		List<BrowseTime> list=new ArrayList<BrowseTime>();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			
			//创建sql命令
			String sql="select t1.uid,t1.tType,t1.tTime " + 
					"from time_browse t1 " + 
					"where t1.tTime = (select max(tTime) from time_browse where uId=t1.uId) " + 
					"order by t1.tType;";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
			while(rs.next()) 
			{
				BrowseTime b=new BrowseTime();
				b.settType(rs.getString("tType"));
				b.settTime(rs.getString("tTime")+" 秒");
				b.setuId(rs.getString("uId"));
				list.add(b);
			}
			
			//插入操作日志
			String concent="管理员查询用户画像";
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

}
