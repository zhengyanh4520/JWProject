package jWeb.service;

import java.util.List;

import jWeb.dao.BOBDao;
import jWeb.pojo.BuyOrBrowse;
import jWeb.pojo.Goods;

public class BOBService {
	private BOBDao bd=new BOBDao();
	
	//�������/�����¼����bTypeΪ0ʱ�������bTypeΪ1ʱ����
	public int AddRecoding(BuyOrBrowse b) {
		return bd.AddRecoding(b);
	}

	//��ѯ�����¼
	public List<BuyOrBrowse> searchBuy(String uId, String ip, String city, String uType) {
		return bd.searchBuy(uId,ip,city,uType);
	}

	//��ѯ�����¼
	public List<BuyOrBrowse> searchBrowse(String uId, String ip, String city, String uType) {
		return bd.searchBrowse(uId,ip,city,uType);
	}

	//���ҹ��ﳵ��¼
	public List<BuyOrBrowse> searchCart(String uId) {
		return bd.searchCart(uId);
	}

	//��������ĳһ��¼
	public BuyOrBrowse findRecoding(String bId) {
		return bd.findRecoding(bId);
	}

	//ɾ����¼
	public int deleteRecoding(String bId) {
		return bd.deleteRecoding(bId);	
	}

	
	//���س����۱���ͳ�Ƴ�����������Ʒ����Ϣ
	public List<Goods> searchSaleTable(String ip, String city) {
		return bd.searchSaleTable(ip,city);
	}

	//�����������ҵ��
	public List<Goods> LoadClassTable(String ip, String city) {
		// TODO Auto-generated method stub
		return bd.LoadClassTable(ip,city);
	}

	//�����쳣���۱�
	public List<Goods> LoadAbnormalTable(String ip, String city) {
		// TODO Auto-generated method stub
		return bd.LoadAbnormalTable(ip, city);
	}

	//����Ԥ�����Ʒ�б�
	public List<Goods> LoadForecastlTable(String ip, String city) {
		// TODO Auto-generated method stub
		return bd.LoadForecastTable(ip,city);
	}

}
