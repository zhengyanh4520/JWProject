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

	//��ѯ������־
	public List<OperationLog> loadOperationLog(String uId, String ip, String city, String uType) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		List<OperationLog> list=new ArrayList<OperationLog>();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//�Ȳ��������־
			String concent="��ѯ������־";
			String sql="insert into operationLog(oId,uId,oContent,oIp,oCity,oTime) values(?,?,?,?,?,?)";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//����������
			randNo r=new randNo();
			String oId=r.createOtherNo();
			//��ȡ��ǰʱ��
	        Date date = new Date();
	    	SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	String time=dateFormat.format(date);
			//��ռλ����ֵ
			ps.setString(1, oId);
			ps.setString(2, uId);
			ps.setString(3, concent);
			ps.setString(4, ip);
			ps.setString(5, city);
			ps.setString(6, time);
			//ִ��
			ps.executeUpdate();
			System.out.println("������־��"+concent);
			
			//�ٲ�ѯ������־����
			sql="select * from operationlog where uId='"+uId+"' order by oTime desc";
			if(uType.equals("2")) {
				sql="select * from operationlog order by oTime desc";
			}
			//����sql�������
			ps.close();
			ps=conn.prepareStatement(sql);
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
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
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return list;
	}

}
