package jWeb.service;

import java.util.List;

import jWeb.dao.OLDao;
import jWeb.pojo.OperationLog;

public class OLService {
	private OLDao o=new OLDao();

	//��ѯ������־
	public List<OperationLog> loadOperationLog(String uId, String ip, String city, String uType) {
		// TODO Auto-generated method stub
		return o.loadOperationLog(uId,ip,city,uType);
	}
	
}
