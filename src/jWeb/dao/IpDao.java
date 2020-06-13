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

	//��ѯ��¼/�˳���¼
	public List<Ip> searchIp(String uId, String ip, String city, int uType) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		List<Ip> list=new ArrayList<Ip>();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="select * "
					+ " from ipTable"
					+ " where uId=?"
					+ " order by iTime Desc";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1, uId);
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
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
				//���������־
				String concent="��ѯ��¼/�˳���¼";
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
				ps.setString(2, uId);
				ps.setString(3, concent);
				ps.setString(4, ip);
				ps.setString(5, city);
				ps.setString(6, time);
				//ִ��
				ps.executeUpdate();
				System.out.println("������־��"+concent);
			}
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return list;
	}

	//�����˳���¼
	public int insertOutIp(String uId, String ip, String city) {
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
			String sql="insert into ipTable(iId,uId,ip,city,iType,iTime) values(?,?,?,?,?,?)";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//����������
			randNo r=new randNo();
			String iId=r.createOtherNo();
			//��ȡ��ǰʱ��
	        Date date = new Date();
	    	SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	String time=dateFormat.format(date);
			//��ռλ����ֵ
			ps.setString(1, iId);
			ps.setString(2, uId);
			ps.setString(3, ip);
			ps.setString(4, city);
			ps.setString(5, "�˳�");
			ps.setString(6, time);
			//ִ��
			flag=ps.executeUpdate();
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return flag;
	}

}
