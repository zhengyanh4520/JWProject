package jWeb.service;

import java.util.List;

import jWeb.dao.IpDao;
import jWeb.pojo.Ip;

public class IpService {
	private IpDao i=new IpDao();

	//查找当前uId的ip表记录,即查询登录/退出记录
	public List<Ip> searchIp(String uId, String ip, String city, int uType) {
		// TODO Auto-generated method stub
		return i.searchIp(uId,ip,city,uType);
	}

	//插入退出记录
	public int insertOutIp(String uId, String ip, String city) {
		return i.insertOutIp(uId,ip,city);
	}
	
	
}
