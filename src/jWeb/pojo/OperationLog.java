package jWeb.pojo;

public class OperationLog {
	private String oId=" ";
	private String uId=" ";
	private String oIp=" ";
	private String oCity=" ";
	private String oContent=" ";
	private String oTime=" ";
	public String getoId() {
		return oId;
	}
	public void setoId(String oId) {
		this.oId = oId;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getoIp() {
		return oIp;
	}
	public void setoIp(String oIp) {
		this.oIp = oIp;
	}
	public String getoCity() {
		return oCity;
	}
	public void setoCity(String oCity) {
		this.oCity = oCity;
	}
	public String getoContent() {
		return oContent;
	}
	public void setoContent(String oContent) {
		this.oContent = oContent;
	}
	public String getoTime() {
		return oTime;
	}
	public void setoTime(String oTime) {
		this.oTime = oTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oCity == null) ? 0 : oCity.hashCode());
		result = prime * result + ((oContent == null) ? 0 : oContent.hashCode());
		result = prime * result + ((oId == null) ? 0 : oId.hashCode());
		result = prime * result + ((oIp == null) ? 0 : oIp.hashCode());
		result = prime * result + ((oTime == null) ? 0 : oTime.hashCode());
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
		OperationLog other = (OperationLog) obj;
		if (oCity == null) {
			if (other.oCity != null)
				return false;
		} else if (!oCity.equals(other.oCity))
			return false;
		if (oContent == null) {
			if (other.oContent != null)
				return false;
		} else if (!oContent.equals(other.oContent))
			return false;
		if (oId == null) {
			if (other.oId != null)
				return false;
		} else if (!oId.equals(other.oId))
			return false;
		if (oIp == null) {
			if (other.oIp != null)
				return false;
		} else if (!oIp.equals(other.oIp))
			return false;
		if (oTime == null) {
			if (other.oTime != null)
				return false;
		} else if (!oTime.equals(other.oTime))
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
		return "OperationLog [oId=" + oId + ", uId=" + uId + ", oIp=" + oIp + ", oCity=" + oCity + ", oContent="
				+ oContent + ", oTime=" + oTime + "]";
	}
	public OperationLog(String oId, String uId, String oIp, String oCity, String oContent, String oTime) {
		super();
		this.oId = oId;
		this.uId = uId;
		this.oIp = oIp;
		this.oCity = oCity;
		this.oContent = oContent;
		this.oTime = oTime;
	}
	public OperationLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
