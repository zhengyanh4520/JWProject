package jWeb.service;

import java.util.List;

import jWeb.dao.GoodsDao;
import jWeb.pojo.Goods;

public class GoodsService {

	private GoodsDao gd=new GoodsDao();
	
	//名字查找某一商品
	public List<Goods> findGoods(String gName) {
		return gd.findGoods(gName);
	}
	
	//添加商品
	public int AddGoodS(Goods g, String ip, String city) {
		return gd.AddGoods(g,ip,city);
	}

	//gId查找商品
	public Goods findGoodsUseId(String gId) {
		return gd.findGoodsUseId(gId);
	}
	
	//删除商品
	public int DeleteGoodS(String gId, String uId, String ip, String city) {
		return gd.DeleteGoods(gId,uId,ip,city);
	}

	//修改商品
	public int ChangeGoods(Goods g) {
		return gd.ChangeGoods(g);
	}
	
	//类别查找某一商品
	public List<Goods> findClassGoods(String gClass) {
		return gd.findClassGoods(gClass);
	}
	
	//加载所有商品
	public List<Goods> LoadGoods() {
		return gd.LoadGoods();
	}

	//双ID查找确切商品，此时增加浏览记录
	public Goods findIdGoods(String uId, String gId) {
		return gd.findIdGoods(uId,gId);
	}

	//购买减少商品数量
	public Goods reduceNumber(String gId, int n) {
		return gd.reduceNumber(gId,n);
	}

	//加载某一商家的所有商品
	public List<Goods> uidLoadGoods(String uId, String ip, String city) {
		return gd.uidLoadGoods(uId,ip,city);
	}

	// 推荐系统,获取推荐给用户的商品
	public List<Goods> LoadRecommengGoods(String uId) {
		// TODO Auto-generated method stub
		return gd.LoadRecommendGoods(uId);
	}

}
