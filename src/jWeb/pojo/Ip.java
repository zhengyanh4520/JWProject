package jWeb.pojo;

public class Ip {
	private String iId=" ";
	private String uId=" ";
	private String ip=" ";
	private String city=" ";
	private String iType=" ";
	private String iTime=" ";
	public String getiId() {
		return iId;
	}
	public void setiId(String iId) {
		this.iId = iId;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getiType() {
		return iType;
	}
	public void setiType(String iType) {
		this.iType = iType;
	}
	public String getiTime() {
		return iTime;
	}
	public void setiTime(String iTime) {
		this.iTime = iTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((iId == null) ? 0 : iId.hashCode());
		result = prime * result + ((iTime == null) ? 0 : iTime.hashCode());
		result = prime * result + ((iType == null) ? 0 : iType.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((uId == null) ? 0 : uId.hashCode());
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
		Ip other = (Ip) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (iId == null) {
			if (other.iId != null)
				return false;
		} else if (!iId.equals(other.iId))
			return false;
		if (iTime == null) {
			if (other.iTime != null)
				return false;
		} else if (!iTime.equals(other.iTime))
			return false;
		if (iType == null) {
			if (other.iType != null)
				return false;
		} else if (!iType.equals(other.iType))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (uId == null) {
			if (other.uId != null)
				return false;
		} else if (!uId.equals(other.uId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Ip [iId=" + iId + ", uId=" + uId + ", ip=" + ip + ", city=" + city + ", iType=" + iType + ", iTime="
				+ iTime + "]";
	}
	public Ip(String iId, String uId, String ip, String city, String iType, String iTime) {
		super();
		this.iId = iId;
		this.uId = uId;
		this.ip = ip;
		this.city = city;
		this.iType = iType;
		this.iTime = iTime;
	}
	public Ip() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
