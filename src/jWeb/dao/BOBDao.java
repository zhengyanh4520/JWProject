package jWeb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jWeb.jdbcContent.jdbcConnect;
import jWeb.pojo.BuyOrBrowse;
import jWeb.pojo.Goods;
import jWeb.rand.randNo;

public class BOBDao {

	//���빺�ﳵ/�����¼����bTypeΪ1ʱ���빺�ﳵ����bTypeΪ2ʱ����
	/*
	 * ��bTypeΪ0ʱ�����ݱ�ʾΪ�����¼���ڸ���ID����ĳһ��Ʒʱ�Ѳ��룬
	 * ��˲��ڴ˴�������GoodsDao��findIdGoods()��
	 */
	public int AddRecoding(BuyOrBrowse b) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		int flag=0;
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			System.out.println("bType:"+b.getbType());
			if(b.getbType()==2) {
				//����SQL����
				String sql="insert into buybrowse(bId,uId,gId,bTime,bType,bPrice,bNumber) values(?,?,?,?,?,?,?)";
				//����sql�������
				ps=conn.prepareStatement(sql);
				//��ռλ����ֵ
				ps.setString(1, b.getbId());
				ps.setString(2, b.getuId());
				ps.setString(3, b.getgId());
				ps.setString(4, b.getbTime());
				ps.setInt(5, b.getbType());
				ps.setInt(6, b.getbPrice());
				ps.setInt(7, b.getbNumber());
				//ִ��
				flag=ps.executeUpdate();
			}else {
				//����SQL����
				String sql="select * from buybrowse where gId=? and uId=? and bType=1";
				//����sql�������
				ps=conn.prepareStatement(sql);
				//��ռλ����ֵ
				ps.setString(1, b.getgId());
				ps.setString(2, b.getuId());
				//ִ��
				rs=ps.executeQuery();
				int j=0;
				//����ִ�н��
				while(rs.next()) 
				{
					//���û����ﳵ���д���Ʒ
					if(rs.getString("bId")!=null) {
						j=1;
					}
				}
				System.out.println("j:"+j);
				if(j==1) {
					sql="update buybrowse set bTime=?,bPrice=bPrice+?,bNumber=bNumber+? where gId=? and uId=? and bType=1";
					//����sql�������
					ps.close();
					ps=conn.prepareStatement(sql);
					//��ռλ����ֵ
					ps.setString(1, b.getbTime());
					ps.setInt(2, b.getbPrice());
					ps.setInt(3, b.getbNumber());
					ps.setString(4, b.getgId());
					ps.setString(5, b.getuId());
					//ִ��
					flag=ps.executeUpdate();
				}else {
					//����SQL����
					sql="insert into buybrowse(bId,uId,gId,bTime,bType,bPrice,bNumber) values(?,?,?,?,?,?,?)";
					//����sql�������
					ps.close();
					ps=conn.prepareStatement(sql);
					//��ռλ����ֵ
					ps.setString(1, b.getbId());
					ps.setString(2, b.getuId());
					ps.setString(3, b.getgId());
					ps.setString(4, b.getbTime());
					ps.setInt(5, b.getbType());
					ps.setInt(6, b.getbPrice());
					ps.setInt(7, b.getbNumber());
					//ִ��
					flag=ps.executeUpdate();
				}
			}
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return flag;
	}

	//��ѯ�����¼,uIdΪadminʱΪ����Ա��ѯȫ��
	public List<BuyOrBrowse> searchBuy(String uId, String ip, String city, String uType) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		List<BuyOrBrowse> list=new ArrayList<BuyOrBrowse>();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="";
			if("2".equals(uType)) {
				sql="select bId,bTime,gName,g.gBelong,gClass,gPrice,uId,bPrice,bNumber,bType"
						+ " from buybrowse b,goods g"
						+ " where bType=2 and b.gId=g.gId"
						+ " order by bTime Desc";
				//����sql�������
				ps=conn.prepareStatement(sql);
			}else if("1".equals(uType)){
				sql="select bId,bTime,gName,g.gBelong,gClass,gPrice,uId,bPrice,bNumber,bType"
						+ " from buybrowse b,goods g"
						+ " where bType=2 and b.gId=g.gId and g.gBelong=?"
						+ " order by bTime Desc";
				//����sql�������
				ps=conn.prepareStatement(sql);
				//��ռλ����ֵ
				ps.setString(1, uId);
			}else{
				sql="select bId,bTime,gName,g.gBelong,gClass,gPrice,uId,bPrice,bNumber,bType"
						+ " from buybrowse b,goods g"
						+ " where b.uId=? and bType=2 and b.gId=g.gId"
						+ " order by bTime Desc";
				//����sql�������
				ps=conn.prepareStatement(sql);
				//��ռλ����ֵ
				ps.setString(1, uId);
			}
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
			while(rs.next()) 
			{
				BuyOrBrowse b=new BuyOrBrowse();
				b.setbId(rs.getString("bId"));
				b.setbTime(rs.getString("bTime"));
				b.setgName(rs.getString("gName"));
				b.setgClass(rs.getString("gClass"));
				b.setgPrice(rs.getInt("gPrice"));
				b.setuId(rs.getString("uId"));
				b.setbPrice(rs.getInt("bPrice"));
				b.setbNumber(rs.getInt("bNumber"));
				b.setbType(rs.getInt("bType"));
				b.setgId(rs.getString("gBelong"));
				list.add(b);
			}
			
			if(!"0".equals(uType)) {
				//���������־
				String concent="��ѯ�����¼";
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

	//��ѯ�����¼,uIdΪadminʱΪ����Ա��ѯȫ��
	public List<BuyOrBrowse> searchBrowse(String uId, String ip, String city, String uType) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		List<BuyOrBrowse> list=new ArrayList<BuyOrBrowse>();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="";
			if("admin".equals(uId)) {
				sql="select bId,bTime,gName,g.gBelong,gClass,gPrice,uId,bPrice,bNumber,bType"
						+ " from buybrowse b,goods g"
						+ " where bType=0 and b.gId=g.gId"
						+ " order by bTime Desc";
				//����sql�������
				ps=conn.prepareStatement(sql);
			}else if("1".equals(uType)){
				sql="select bId,bTime,gName,g.gBelong,gClass,gPrice,uId,bPrice,bNumber,bType"
						+ " from buybrowse b,goods g"
						+ " where bType=0 and b.gId=g.gId and g.gBelong=?"
						+ " order by bTime Desc";
				//����sql�������
				ps=conn.prepareStatement(sql);
				//��ռλ����ֵ
				ps.setString(1, uId);
			}else {
				sql="select bId,bTime,gName,gClass,g.gBelong,gPrice,uId,bPrice,bNumber,bType"
						+ " from buybrowse b,goods g"
						+ " where b.uId=? and bType=0 and b.gId=g.gId"
						+ " order by bTime Desc";
				//����sql�������
				ps=conn.prepareStatement(sql);
				//��ռλ����ֵ
				ps.setString(1, uId);
			}
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
			while(rs.next()) 
			{
				BuyOrBrowse b=new BuyOrBrowse();
				b.setbId(rs.getString("bId"));
				b.setbTime(rs.getString("bTime"));
				b.setgName(rs.getString("gName"));
				b.setgClass(rs.getString("gClass"));
				b.setgPrice(rs.getInt("gPrice"));
				b.setuId(rs.getString("uId"));
				b.setbPrice(rs.getInt("bPrice"));
				b.setbNumber(rs.getInt("bNumber"));
				b.setbType(rs.getInt("bType"));
				b.setgId(rs.getString("gBelong"));
				list.add(b);
			}
			
			if(!"0".equals(uType)) {
				//���������־
				String concent="��ѯ�����¼";
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

	//���ҹ��ﳵ��¼
	public List<BuyOrBrowse> searchCart(String uId) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		List<BuyOrBrowse> list=new ArrayList<BuyOrBrowse>();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="select bId,bTime,gName,gClass,gPrice,uId,bPrice,bNumber,bType"
					+ " from buybrowse b,goods g"
					+ " where b.uId=? and bType=1 and b.gId=g.gId"
					+ " order by bTime Desc";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1, uId);
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
			while(rs.next()) 
			{
				BuyOrBrowse b=new BuyOrBrowse();
				b.setbId(rs.getString("bId"));
				b.setbTime(rs.getString("bTime"));
				b.setgName(rs.getString("gName"));
				b.setgClass(rs.getString("gClass"));
				b.setgPrice(rs.getInt("gPrice"));
				b.setuId(rs.getString("uId"));
				b.setbPrice(rs.getInt("bPrice"));
				b.setbNumber(rs.getInt("bNumber"));
				b.setbType(rs.getInt("bType"));
				list.add(b);
			}
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return list;
	}

	////��������ĳһ��¼
	public BuyOrBrowse findRecoding(String bId) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		BuyOrBrowse b=new BuyOrBrowse();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="select * from buybrowse where bId=?";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1, bId);
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
			while(rs.next()) 
			{
				b.setbId(rs.getString("bId"));
				b.setbTime(rs.getString("bTime"));
				b.setgId(rs.getString("gId"));
				b.setuId(rs.getString("uId"));
				b.setbPrice(rs.getInt("bPrice"));
				b.setbNumber(rs.getInt("bNumber"));
				b.setbType(rs.getInt("bType"));
			}
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return b;
	}


	//ɾ����¼
	public int deleteRecoding(String bId) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		//�������ݴ洢����
		int flag=0;
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="delete from buybrowse where bId=?";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1, bId);
			//ִ��
			flag=ps.executeUpdate();
			//�ر���Դ
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(ps, conn);
		}
		return flag;
	}

	
	//���س����۱���ͳ�Ƴ�����������Ʒ����Ϣ
	public List<Goods> searchSaleTable(String ip, String city) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		List<Goods> list=new ArrayList<Goods>();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="select b.gId id,g.gName n,g.gBelong belong,g.gDescribe de," + 
					"g.gClass class,g.gNumber,sum(b.bPrice) price,sum(b.bNumber) salenumber " + 
					"from buybrowse b left join goods g on  b.gId=g.gId " + 
					"where b.bType=2 " + 
					"group by b.gId " + 
					"order by class desc;";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
			while(rs.next()) 
			{
				Goods g=new Goods();
				g.setgId(rs.getString("id"));
				g.setgName(rs.getString("n"));
				g.setgBelong(rs.getString("belong"));
				g.setgClass(rs.getString("class"));
				g.setgDescribe(rs.getString("de"));
				g.setgPrice(rs.getInt("price"));
				g.setgNumber(rs.getInt("gNumber"));
				g.setSaleNumber(rs.getInt("salenumber"));
				list.add(g);
			}
			
			//���������־
			String concent="����Ա��ѯ���۱���";
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

	//�����������ҵ��
	public List<Goods> LoadClassTable(String ip, String city) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		List<Goods> list=new ArrayList<Goods>();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL����
			String sql="select g.gClass class,sum(b.bPrice) price,sum(b.bNumber) number " + 
					"from buybrowse b left join goods g on  b.gId=g.gId " + 
					"where b.bType=2 " + 
					"group by g.gClass " + 
					"order by price desc;";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//ִ��
			rs=ps.executeQuery();
			//����ִ�н��
			while(rs.next()) 
			{
				Goods g=new Goods();
				g.setgClass(rs.getString("class"));
				g.setgPrice(rs.getInt("price"));
				g.setSaleNumber(rs.getInt("number"));
				list.add(g);
			}
			
			//���������־
			String concent="����Ա��ѯ�������ҵ��";
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

	//�����쳣���۱�
	public List<Goods> LoadAbnormalTable(String ip, String city) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		List<Goods> list=new ArrayList<Goods>();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL�����ѯ���ʽ����쳣
			String sql="select g.gName,g.gClass,g.gBelong,r.uId,r.gId,r.bPrice,r.bNumber " + 
					"from (select uId,gid,bPrice,bNumber " + 
					"from buybrowse " + 
					"where bNumber>=100 or bPrice>100000) as r,goods g " + 
					"where r.gid=g.gid";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//ִ��
			rs=ps.executeQuery();
			//˵��
			Goods good1=new Goods();
			good1.setgDescribe("ĳ�û����ʽ��׵��쳣��������ʽ�����������100�������100000");
			list.add(good1);
			//����ִ�н��
			while(rs.next()) 
			{
				Goods g=new Goods();
				g.setgId(rs.getString("gId"));
				g.setgDescribe(rs.getString("uId"));
				g.setgName(rs.getString("gName"));
				g.setgClass(rs.getString("gClass"));
				g.setgBelong(rs.getString("gBelong"));
				g.setgPrice(rs.getInt("bPrice"));
				g.setSaleNumber(rs.getInt("bNumber"));
				list.add(g);
			}
			
			//˵��
			Goods good2=new Goods();
			good2.setgDescribe("ĳ�û����彻�׵��쳣�������ĳһ��Ʒ�Ͻ�������������500���ܽ�����500000");
			list.add(good2);
			//����SQL�����ѯ��ν����ܺ��쳣
			sql="select g.gName,g.gClass,g.gBelong,r.uId,r.gId,r.sumPrice,r.sumNumber " + 
					"from goods g, " + 
					"(select uId,gId,sum(bPrice) sumPrice,sum(bNumber) sumNumber " + 
					"from buybrowse " + 
					"where bType=2 " + 
					"group by uId,gId) as r " + 
					"where r.gid=g.gid and (sumPrice>500000 or sumNumber>500)";
			//����sql�������
			ps.close();
			ps=conn.prepareStatement(sql);
			//ִ��
			rs.close();
			rs=ps.executeQuery();
			//����ִ�н��
			while(rs.next()) 
			{
				Goods g=new Goods();
				g.setgId(rs.getString("gId"));
				g.setgDescribe(rs.getString("uId"));
				g.setgName(rs.getString("gName"));
				g.setgClass(rs.getString("gClass"));
				g.setgBelong(rs.getString("gBelong"));
				g.setgPrice(rs.getInt("sumPrice"));
				g.setSaleNumber(rs.getInt("sumNumber"));
				list.add(g);
			}
			
			//���������־
			String concent="����Ա��ѯ�����쳣״��";
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

	//����Ԥ�����Ʒ�б�
	public List<Goods> LoadForecastTable(String ip, String city) {
		//����JDBC����
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//�������ݴ洢����
		List<Goods> list=new ArrayList<Goods>();
		try {
			//�������ݿ�
			conn=jdbcConnect.getConnection();
			//����SQL�����ѯ���۽��ǰʮ
			String sql="select b.gId,g.gName,g.gBelong,g.gDescribe," + 
					"g.gClass,g.gNumber,sum(b.bPrice) price,sum(b.bNumber) salenumber " + 
					"from buybrowse b left join goods g on  b.gId=g.gId " + 
					"where b.bType=2 " + 
					"group by b.gId " + 
					"order by price desc " + 
					"LIMIT 10";
			//����sql�������
			ps=conn.prepareStatement(sql);
			//ִ��
			rs=ps.executeQuery();
			//˵��
			Goods good1=new Goods();
			good1.setgId("���۽��ǰʮ��");
			good1.setgDescribe("������Щ��Ʒ����ܻ��и�������������ͨ");
			list.add(good1);
			//����ִ�н��
			while(rs.next()) 
			{
				Goods g=new Goods();
				g.setgId(rs.getString("gId"));
				g.setgDescribe(rs.getString("gDescribe"));
				g.setgName(rs.getString("gName"));
				g.setgClass(rs.getString("gClass"));
				g.setgNumber(rs.getInt("gNumber"));
				g.setgBelong(rs.getString("gBelong"));
				g.setgPrice(rs.getInt("price"));
				g.setSaleNumber(rs.getInt("salenumber"));
				list.add(g);
			}
			
			//˵��
			list.add(new Goods());
			Goods good2=new Goods();
			good2.setgId("��������ǰʮ��");
			good2.setgDescribe("������Щ��Ʒ����ܻ��и���������������ͨ");
			list.add(good2);
			//����SQL�����ѯ��������ǰʮ
			sql="select b.gId,g.gName,g.gBelong,g.gDescribe," + 
					"g.gClass,g.gNumber,sum(b.bPrice) price,sum(b.bNumber) salenumber " + 
					"from buybrowse b left join goods g on  b.gId=g.gId " + 
					"where b.bType=2 " + 
					"group by b.gId " + 
					"order by salenumber desc " + 
					"LIMIT 10";
			//����sql�������
			ps.close();
			ps=conn.prepareStatement(sql);
			//ִ��
			rs.close();
			rs=ps.executeQuery();
			//����ִ�н��
			while(rs.next()) 
			{
				Goods g=new Goods();
				g.setgId(rs.getString("gId"));
				g.setgDescribe(rs.getString("gDescribe"));
				g.setgName(rs.getString("gName"));
				g.setgClass(rs.getString("gClass"));
				g.setgNumber(rs.getInt("gNumber"));
				g.setgBelong(rs.getString("gBelong"));
				g.setgPrice(rs.getInt("price"));
				g.setSaleNumber(rs.getInt("salenumber"));
				list.add(g);
			}
			
			//���������־
			String concent="����Ա��ѯ��ƷԤ��������״��";
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
