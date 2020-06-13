package jWeb.pojo;

public class User {
	private String uId=" ";
	private String uName=" ";
	private String uPsw=" ";
	private String uSex=" ";
	private String uEmail=" ";
	private String uPhone=" ";
	private String uIntroduce=" ";
	private int uType=0;
	private int uMoney=0;
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getuPsw() {
		return uPsw;
	}
	public void setuPsw(String uPsw) {
		this.uPsw = uPsw;
	}
	public String getuSex() {
		return uSex;
	}
	public void setuSex(String uSex) {
		this.uSex = uSex;
	}
	public String getuEmail() {
		return uEmail;
	}
	public void setuEmail(String uEmail) {
		this.uEmail = uEmail;
	}
	public String getuPhone() {
		return uPhone;
	}
	public void setuPhone(String uPhone) {
		this.uPhone = uPhone;
	}
	public String getuIntroduce() {
		return uIntroduce;
	}
	public void setuIntroduce(String uIntroduce) {
		this.uIntroduce = uIntroduce;
	}
	public int getuType() {
		return uType;
	}
	public void setuType(int uType) {
		this.uType = uType;
	}
	public int getuMoney() {
		return uMoney;
	}
	public void setuMoney(int uMoney) {
		this.uMoney = uMoney;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uEmail == null) ? 0 : uEmail.hashCode());
		result = prime * result + ((uId == null) ? 0 : uId.hashCode());
		result = prime * result + ((uIntroduce == null) ? 0 : uIntroduce.hashCode());
		result = prime * result + uMoney;
		result = prime * result + ((uName == null) ? 0 : uName.hashCode());
		result = prime * result + ((uPhone == null) ? 0 : uPhone.hashCode());
		result = prime * result + ((uPsw == null) ? 0 : uPsw.hashCode());
		result = prime * result + ((uSex == null) ? 0 : uSex.hashCode());
		result = prime * result + uType;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (uEmail == null) {
			if (other.uEmail != null)
				return false;
		} else if (!uEmail.equals(other.uEmail))
			return false;
		if (uId == null) {
			if (other.uId != null)
				return false;
		} else if (!uId.equals(other.uId))
			return false;
		if (uIntroduce == null) {
			if (other.uIntroduce != null)
				return false;
		} else if (!uIntroduce.equals(other.uIntroduce))
			return false;
		if (uMoney != other.uMoney)
			return false;
		if (uName == null) {
			if (other.uName != null)
				return false;
		} else if (!uName.equals(other.uName))
			return false;
		if (uPhone == null) {
			if (other.uPhone != null)
				return false;
		} else if (!uPhone.equals(other.uPhone))
			return false;
		if (uPsw == null) {
			if (other.uPsw != null)
				return false;
		} else if (!uPsw.equals(other.uPsw))
			return false;
		if (uSex == null) {
			if (other.uSex != null)
				return false;
		} else if (!uSex.equals(other.uSex))
			return false;
		if (uType != other.uType)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "User [uId=" + uId + ", uName=" + uName + ", uPsw=" + uPsw + ", uSex=" + uSex + ", uEmail=" + uEmail
				+ ", uPhone=" + uPhone + ", uIntroduce=" + uIntroduce + ", uType=" + uType + ", uMoney=" + uMoney + "]";
	}
	public User(String uId, String uName, String uPsw, String uSex, String uEmail, String uPhone, String uIntroduce,
			int uType, int uMoney) {
		super();
		this.uId = uId;
		this.uName = uName;
		this.uPsw = uPsw;
		this.uSex = uSex;
		this.uEmail = uEmail;
		this.uPhone = uPhone;
		this.uIntroduce = uIntroduce;
		this.uType = uType;
		this.uMoney = uMoney;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
