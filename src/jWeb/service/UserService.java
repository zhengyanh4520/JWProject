package jWeb.service;

import java.util.List;

import jWeb.dao.UserDao;
import jWeb.pojo.User;

public class UserService {
	
	private UserDao u=new UserDao();
	
	//�û�ע��
	public int checkRegisterUser(String uId, String uName, String uIntroduce,
			String uPsw, int uType, String uSex, String uPhone, String uEmail, String ip, String city) {
		return u.checkRegisterUser(uId, uName,uIntroduce,uPsw,uType,uSex,uPhone,uEmail,ip,city);
	}
	
	//�û���¼
	public int checkLoginUser(String uId, String uPsw, int uType, String ip, String city) {
		return u.checkLoginUser(uId,uPsw,uType,ip,city);
	}

	//����ĳһ�û�������Ϣ
	public User findUser(String uId) {
		return u.findUser(uId);
	}

	//�޸���Ϣ,i=1ʱ�������Ա�ڲ�����0ʱ���˻��Լ�����
	public int changeInfo(User u2, String ip, String city, int i) {
		return u.changeInfo(u2,ip,city,i);
	}

	//�û���ֵ
	public User recharge(String uId, int money) {
		return u.recharge(uId,money);
	}

	//��������û����
	public User reduceMoney(String uId, int bp) {
		return u.reduceMoney(uId,bp);
	}

	//��ȡ������Ա�б�
	public List<User> getSaleList(String ip, String city) {
		return u.getSaleList(ip,city);
	}

	//ɾ��ĳһ�˻�
	public int deleteSaler(String uId, String ip, String city) {
		return u.deleteSaler(uId,ip,city);
	}

}
