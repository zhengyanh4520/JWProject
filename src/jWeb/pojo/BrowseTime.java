package jWeb.pojo;

public class BrowseTime {
	private String tId=" ";
	private String uId=" ";
	private String tType=" ";
	private String tTime=" ";
	public String gettId() {
		return tId;
	}
	public void settId(String tId) {
		this.tId = tId;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String gettType() {
		return tType;
	}
	public void settType(String tType) {
		this.tType = tType;
	}
	public String gettTime() {
		return tTime;
	}
	public void settTime(String tTime) {
		this.tTime = tTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tId == null) ? 0 : tId.hashCode());
		result = prime * result + ((tTime == null) ? 0 : tTime.hashCode());
		result = prime * result + ((tType == null) ? 0 : tType.hashCode());
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
		BrowseTime other = (BrowseTime) obj;
		if (tId == null) {
			if (other.tId != null)
				return false;
		} else if (!tId.equals(other.tId))
			return false;
		if (tTime == null) {
			if (other.tTime != null)
				return false;
		} else if (!tTime.equals(other.tTime))
			return false;
		if (tType == null) {
			if (other.tType != null)
				return false;
		} else if (!tType.equals(other.tType))
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
		return "BrowseTime [tId=" + tId + ", uId=" + uId + ", tType=" + tType + ", tTime=" + tTime + "]";
	}
	public BrowseTime(String tId, String uId, String tType, String tTime) {
		super();
		this.tId = tId;
		this.uId = uId;
		this.tType = tType;
		this.tTime = tTime;
	}
	public BrowseTime() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
