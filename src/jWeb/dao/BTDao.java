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

	//��¼�û�������ʱ��
	public int insertTime(String uId, String type, String time) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		int flag=0;
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			
			//����sql����
			String sql="update time_browse set tTime=tTime+? where uId=? and tType=?";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			int t=Integer.parseInt(time);
			ps.setInt(1, t);
			ps.setString(2, uId);
			ps.setString(3, type);
			//ִ��
			flag=ps.executeUpdate();
			if(flag==0) {
				//����sql����
				sql="insert into time_browse(tId,uId,tType,tTime) values(?,?,?,?)";
				//����sql�������
				ps.close();
				ps=conn.prepareStatement(sql);
				//����������
				randNo r=new randNo();
				String tId=r.createOtherNo();
				//��ռλ����ֵ
				ps.setString(1, tId);
				ps.setString(2, uId);
				ps.setString(3, type);
				ps.setInt(4, t);
				//ִ��
				flag=ps.executeUpdate();
			}
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return flag;
	}

	//�������ʱ����û�����
	public List<BrowseTime> loadByTime(String ip, String city) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		List<BrowseTime> list=new ArrayList<BrowseTime>();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			
			//����sql����
			String sql="select t1.uid,t1.tType,t1.tTime " + 
					"from time_browse t1 " + 
					"where t1.tTime = (select max(tTime) from time_browse where uId=t1.uId) " + 
					"order by t1.tType;";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
			while(rs.next()) 
			{
				BrowseTime b=new BrowseTime();
				b.settType(rs.getString("tType"));
				b.settTime(rs.getString("tTime")+" ��");
				b.setuId(rs.getString("uId"));
				list.add(b);
			}
			
			//���������־
			String concent="����Ա��ѯ�û�����";
			sql="insert into operationLog(oId,uId,oContent,oIp,oCity,oTime) values(?,?,?,?,?,?)";
			//����sql�������
			ps.close();
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
			ps.setString(2, "admin");
			ps.setString(3, concent);
			ps.setString(4, ip);
			ps.setString(5, city);
			ps.setString(6, time);
			//ִ��
			ps.executeUpdate();
			System.out.println("������־��"+concent);
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return list;
	}

}
