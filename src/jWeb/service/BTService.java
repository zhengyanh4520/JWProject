package jWeb.service;

import java.util.List;

import jWeb.dao.BTDao;
import jWeb.pojo.BrowseTime;

public class BTService {
	private BTDao bt=new BTDao();

	//记录用户浏览类别及时间
	public int insertTime(String uId, String type, String time) {
		return bt.insertTime(uId,type,time);
	}

	//根据浏览时间的用户画像
	public List<BrowseTime> loadByTime(String ip, String city) {
		return bt.loadByTime(ip,city);
	}
	
}
