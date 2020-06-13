package jWeb.service;

import java.util.List;

import jWeb.dao.GoodsDao;
import jWeb.pojo.Goods;

public class GoodsService {

	private GoodsDao gd=new GoodsDao();
	
	//���ֲ���ĳһ��Ʒ
	public List<Goods> findGoods(String gName) {
		return gd.findGoods(gName);
	}
	
	//�����Ʒ
	public int AddGoodS(Goods g, String ip, String city) {
		return gd.AddGoods(g,ip,city);
	}

	//gId������Ʒ
	public Goods findGoodsUseId(String gId) {
		return gd.findGoodsUseId(gId);
	}
	
	//ɾ����Ʒ
	public int DeleteGoodS(String gId, String uId, String ip, String city) {
		return gd.DeleteGoods(gId,uId,ip,city);
	}

	//�޸���Ʒ
	public int ChangeGoods(Goods g) {
		return gd.ChangeGoods(g);
	}
	
	//������ĳһ��Ʒ
	public List<Goods> findClassGoods(String gClass) {
		return gd.findClassGoods(gClass);
	}
	
	//����������Ʒ
	public List<Goods> LoadGoods() {
		return gd.LoadGoods();
	}

	//˫ID����ȷ����Ʒ����ʱ���������¼
	public Goods findIdGoods(String uId, String gId) {
		return gd.findIdGoods(uId,gId);
	}

	//���������Ʒ����
	public Goods reduceNumber(String gId, int n) {
		return gd.reduceNumber(gId,n);
	}

	//����ĳһ�̼ҵ�������Ʒ
	public List<Goods> uidLoadGoods(String uId, String ip, String city) {
		return gd.uidLoadGoods(uId,ip,city);
	}

	// �Ƽ�ϵͳ,��ȡ�Ƽ����û�����Ʒ
	public List<Goods> LoadRecommengGoods(String uId) {
		// TODO Auto-generated method stub
		return gd.LoadRecommendGoods(uId);
	}

}
