package jWeb.service;

import java.util.List;

import jWeb.dao.IpDao;
import jWeb.pojo.Ip;

public class IpService {
	private IpDao i=new IpDao();

	//���ҵ�ǰuId��ip���¼,����ѯ��¼/�˳���¼
	public List<Ip> searchIp(String uId, String ip, String city, int uType) {
		// TODO Auto-generated method stub
		return i.searchIp(uId,ip,city,uType);
	}

	//�����˳���¼
	public int insertOutIp(String uId, String ip, String city) {
		return i.insertOutIp(uId,ip,city);
	}
	
	
}
