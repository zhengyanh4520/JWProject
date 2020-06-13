package jWeb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jWeb.jdbcContent.jdbcConnect;
import jWeb.pojo.OperationLog;
import jWeb.rand.randNo;

public class OLDao {

	//查询操作日志
	public List<OperationLog> loadOperationLog(String uId, String ip, String city, String uType) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		List<OperationLog> list=new ArrayList<OperationLog>();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//先插入操作日志
			String concent="查询操作日志";
			String sql="insert into operationLog(oId,uId,oContent,oIp,oCity,oTime) values(?,?,?,?,?,?)";
			//创建sql命令对象
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
			
			//再查询操作日志内容
			sql="select * from operationlog where uId='"+uId+"' order by oTime desc";
			if(uType.equals("2")) {
				sql="select * from operationlog order by oTime desc";
			}
			//创建sql命令对象
			ps.close();
			ps=conn.prepareStatement(sql);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
			while(rs.next()) 
			{
				OperationLog o=new OperationLog();
				o.setoId(rs.getString("oId"));
				o.setuId(rs.getString("uId"));
				o.setoCity(rs.getString("oCity"));
				o.setoIp(rs.getString("oIp"));
				o.setoTime(rs.getString("oTime"));
				o.setoContent(rs.getString("oContent"));
				list.add(o);
			}
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return list;
	}

}
