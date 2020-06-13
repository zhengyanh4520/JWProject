package jWeb.service;

import java.util.List;

import jWeb.dao.BTDao;
import jWeb.pojo.BrowseTime;

public class BTService {
	private BTDao bt=new BTDao();

	//��¼�û�������ʱ��
	public int insertTime(String uId, String type, String time) {
		return bt.insertTime(uId,type,time);
	}

	//�������ʱ����û�����
	public List<BrowseTime> loadByTime(String ip, String city) {
		return bt.loadByTime(ip,city);
	}
	
}
