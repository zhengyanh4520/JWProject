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

	//插入购物车/购买记录，当bType为1时加入购物车，当bType为2时购买
	/*
	 * 当bType为0时该数据表示为浏览记录，在根据ID查找某一商品时已插入，
	 * 因此不在此处，处于GoodsDao的findIdGoods()中
	 */
	public int AddRecoding(BuyOrBrowse b) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		int flag=0;
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			System.out.println("bType:"+b.getbType());
			if(b.getbType()==2) {
				//创建SQL命令
				String sql="insert into buybrowse(bId,uId,gId,bTime,bType,bPrice,bNumber) values(?,?,?,?,?,?,?)";
				//创建sql命令对象
				ps=conn.prepareStatement(sql);
				//给占位符赋值
				ps.setString(1, b.getbId());
				ps.setString(2, b.getuId());
				ps.setString(3, b.getgId());
				ps.setString(4, b.getbTime());
				ps.setInt(5, b.getbType());
				ps.setInt(6, b.getbPrice());
				ps.setInt(7, b.getbNumber());
				//执行
				flag=ps.executeUpdate();
			}else {
				//创建SQL命令
				String sql="select * from buybrowse where gId=? and uId=? and bType=1";
				//创建sql命令对象
				ps=conn.prepareStatement(sql);
				//给占位符赋值
				ps.setString(1, b.getgId());
				ps.setString(2, b.getuId());
				//执行
				rs=ps.executeQuery();
				int j=0;
				//遍历执行结果
				while(rs.next()) 
				{
					//该用户购物车已有此商品
					if(rs.getString("bId")!=null) {
						j=1;
					}
				}
				System.out.println("j:"+j);
				if(j==1) {
					sql="update buybrowse set bTime=?,bPrice=bPrice+?,bNumber=bNumber+? where gId=? and uId=? and bType=1";
					//创建sql命令对象
					ps.close();
					ps=conn.prepareStatement(sql);
					//给占位符赋值
					ps.setString(1, b.getbTime());
					ps.setInt(2, b.getbPrice());
					ps.setInt(3, b.getbNumber());
					ps.setString(4, b.getgId());
					ps.setString(5, b.getuId());
					//执行
					flag=ps.executeUpdate();
				}else {
					//创建SQL命令
					sql="insert into buybrowse(bId,uId,gId,bTime,bType,bPrice,bNumber) values(?,?,?,?,?,?,?)";
					//创建sql命令对象
					ps.close();
					ps=conn.prepareStatement(sql);
					//给占位符赋值
					ps.setString(1, b.getbId());
					ps.setString(2, b.getuId());
					ps.setString(3, b.getgId());
					ps.setString(4, b.getbTime());
					ps.setInt(5, b.getbType());
					ps.setInt(6, b.getbPrice());
					ps.setInt(7, b.getbNumber());
					//执行
					flag=ps.executeUpdate();
				}
			}
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return flag;
	}

	//查询购买记录,uId为admin时为管理员查询全部
	public List<BuyOrBrowse> searchBuy(String uId, String ip, String city, String uType) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		List<BuyOrBrowse> list=new ArrayList<BuyOrBrowse>();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="";
			if("2".equals(uType)) {
				sql="select bId,bTime,gName,g.gBelong,gClass,gPrice,uId,bPrice,bNumber,bType"
						+ " from buybrowse b,goods g"
						+ " where bType=2 and b.gId=g.gId"
						+ " order by bTime Desc";
				//创建sql命令对象
				ps=conn.prepareStatement(sql);
			}else if("1".equals(uType)){
				sql="select bId,bTime,gName,g.gBelong,gClass,gPrice,uId,bPrice,bNumber,bType"
						+ " from buybrowse b,goods g"
						+ " where bType=2 and b.gId=g.gId and g.gBelong=?"
						+ " order by bTime Desc";
				//创建sql命令对象
				ps=conn.prepareStatement(sql);
				//给占位符赋值
				ps.setString(1, uId);
			}else{
				sql="select bId,bTime,gName,g.gBelong,gClass,gPrice,uId,bPrice,bNumber,bType"
						+ " from buybrowse b,goods g"
						+ " where b.uId=? and bType=2 and b.gId=g.gId"
						+ " order by bTime Desc";
				//创建sql命令对象
				ps=conn.prepareStatement(sql);
				//给占位符赋值
				ps.setString(1, uId);
			}
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
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
				//插入操作日志
				String concent="查询购买记录";
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

	//查询浏览记录,uId为admin时为管理员查询全部
	public List<BuyOrBrowse> searchBrowse(String uId, String ip, String city, String uType) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		List<BuyOrBrowse> list=new ArrayList<BuyOrBrowse>();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="";
			if("admin".equals(uId)) {
				sql="select bId,bTime,gName,g.gBelong,gClass,gPrice,uId,bPrice,bNumber,bType"
						+ " from buybrowse b,goods g"
						+ " where bType=0 and b.gId=g.gId"
						+ " order by bTime Desc";
				//创建sql命令对象
				ps=conn.prepareStatement(sql);
			}else if("1".equals(uType)){
				sql="select bId,bTime,gName,g.gBelong,gClass,gPrice,uId,bPrice,bNumber,bType"
						+ " from buybrowse b,goods g"
						+ " where bType=0 and b.gId=g.gId and g.gBelong=?"
						+ " order by bTime Desc";
				//创建sql命令对象
				ps=conn.prepareStatement(sql);
				//给占位符赋值
				ps.setString(1, uId);
			}else {
				sql="select bId,bTime,gName,gClass,g.gBelong,gPrice,uId,bPrice,bNumber,bType"
						+ " from buybrowse b,goods g"
						+ " where b.uId=? and bType=0 and b.gId=g.gId"
						+ " order by bTime Desc";
				//创建sql命令对象
				ps=conn.prepareStatement(sql);
				//给占位符赋值
				ps.setString(1, uId);
			}
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
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
				//插入操作日志
				String concent="查询购买记录";
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

	//查找购物车记录
	public List<BuyOrBrowse> searchCart(String uId) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		List<BuyOrBrowse> list=new ArrayList<BuyOrBrowse>();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="select bId,bTime,gName,gClass,gPrice,uId,bPrice,bNumber,bType"
					+ " from buybrowse b,goods g"
					+ " where b.uId=? and bType=1 and b.gId=g.gId"
					+ " order by bTime Desc";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, uId);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
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
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return list;
	}

	////主键查找某一记录
	public BuyOrBrowse findRecoding(String bId) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		BuyOrBrowse b=new BuyOrBrowse();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="select * from buybrowse where bId=?";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, bId);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
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
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(rs, ps, conn);
		}
		return b;
	}


	//删除记录
	public int deleteRecoding(String bId) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		//声明数据存储对象
		int flag=0;
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="delete from buybrowse where bId=?";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, bId);
			//执行
			flag=ps.executeUpdate();
			//关闭资源
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			jdbcConnect.delete(ps, conn);
		}
		return flag;
	}

	
	//加载出销售报表，统计出已售卖的商品的信息
	public List<Goods> searchSaleTable(String ip, String city) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		List<Goods> list=new ArrayList<Goods>();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="select b.gId id,g.gName n,g.gBelong belong,g.gDescribe de," + 
					"g.gClass class,g.gNumber,sum(b.bPrice) price,sum(b.bNumber) salenumber " + 
					"from buybrowse b left join goods g on  b.gId=g.gId " + 
					"where b.bType=2 " + 
					"group by b.gId " + 
					"order by class desc;";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
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
			
			//插入操作日志
			String concent="管理员查询销售报表";
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

	//加载销售类别业绩
	public List<Goods> LoadClassTable(String ip, String city) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		List<Goods> list=new ArrayList<Goods>();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令
			String sql="select g.gClass class,sum(b.bPrice) price,sum(b.bNumber) number " + 
					"from buybrowse b left join goods g on  b.gId=g.gId " + 
					"where b.bType=2 " + 
					"group by g.gClass " + 
					"order by price desc;";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//执行
			rs=ps.executeQuery();
			//遍历执行结果
			while(rs.next()) 
			{
				Goods g=new Goods();
				g.setgClass(rs.getString("class"));
				g.setgPrice(rs.getInt("price"));
				g.setSaleNumber(rs.getInt("number"));
				list.add(g);
			}
			
			//插入操作日志
			String concent="管理员查询销售类别业绩";
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

	//加载异常销售表
	public List<Goods> LoadAbnormalTable(String ip, String city) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		List<Goods> list=new ArrayList<Goods>();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令，查询单笔交易异常
			String sql="select g.gName,g.gClass,g.gBelong,r.uId,r.gId,r.bPrice,r.bNumber " + 
					"from (select uId,gid,bPrice,bNumber " + 
					"from buybrowse " + 
					"where bNumber>=100 or bPrice>100000) as r,goods g " + 
					"where r.gid=g.gid";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//执行
			rs=ps.executeQuery();
			//说明
			Goods good1=new Goods();
			good1.setgDescribe("某用户单笔交易的异常情况，单笔交易数量大于100或金额大于100000");
			list.add(good1);
			//遍历执行结果
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
			
			//说明
			Goods good2=new Goods();
			good2.setgDescribe("某用户总体交易的异常情况，在某一商品上交易总数量大于500或总金额大于500000");
			list.add(good2);
			//创建SQL命令，查询多次交易总和异常
			sql="select g.gName,g.gClass,g.gBelong,r.uId,r.gId,r.sumPrice,r.sumNumber " + 
					"from goods g, " + 
					"(select uId,gId,sum(bPrice) sumPrice,sum(bNumber) sumNumber " + 
					"from buybrowse " + 
					"where bType=2 " + 
					"group by uId,gId) as r " + 
					"where r.gid=g.gid and (sumPrice>500000 or sumNumber>500)";
			//创建sql命令对象
			ps.close();
			ps=conn.prepareStatement(sql);
			//执行
			rs.close();
			rs=ps.executeQuery();
			//遍历执行结果
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
			
			//插入操作日志
			String concent="管理员查询销售异常状况";
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

	//加载预测的商品列表
	public List<Goods> LoadForecastTable(String ip, String city) {
		//声明JDBC对象
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//声明数据存储对象
		List<Goods> list=new ArrayList<Goods>();
		try {
			//连接数据库
			conn=jdbcConnect.getConnection();
			//创建SQL命令，查询销售金额前十
			String sql="select b.gId,g.gName,g.gBelong,g.gDescribe," + 
					"g.gClass,g.gNumber,sum(b.bPrice) price,sum(b.bNumber) salenumber " + 
					"from buybrowse b left join goods g on  b.gId=g.gId " + 
					"where b.bType=2 " + 
					"group by b.gId " + 
					"order by price desc " + 
					"LIMIT 10";
			//创建sql命令对象
			ps=conn.prepareStatement(sql);
			//执行
			rs=ps.executeQuery();
			//说明
			Goods good1=new Goods();
			good1.setgId("销售金额前十名");
			good1.setgDescribe("销售这些商品会可能会有更多的利润或金额流通");
			list.add(good1);
			//遍历执行结果
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
			
			//说明
			list.add(new Goods());
			Goods good2=new Goods();
			good2.setgId("销售数量前十名");
			good2.setgDescribe("销售这些商品会可能会有更多的利润或数量流通");
			list.add(good2);
			//创建SQL命令，查询销售数量前十
			sql="select b.gId,g.gName,g.gBelong,g.gDescribe," + 
					"g.gClass,g.gNumber,sum(b.bPrice) price,sum(b.bNumber) salenumber " + 
					"from buybrowse b left join goods g on  b.gId=g.gId " + 
					"where b.bType=2 " + 
					"group by b.gId " + 
					"order by salenumber desc " + 
					"LIMIT 10";
			//创建sql命令对象
			ps.close();
			ps=conn.prepareStatement(sql);
			//执行
			rs.close();
			rs=ps.executeQuery();
			//遍历执行结果
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
			
			//插入操作日志
			String concent="管理员查询商品预测与评估状况";
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
