package jWeb.pojo;

public class BuyOrBrowse {
	private String bId=" ";
	private String uId=" ";
	private String gId=" ";
	private String gName=" ";
	private String gClass=" ";
	private String bTime=" ";
	private int bPrice=0;
	private int bNumber=0;
	private int bType=0;
	private int gPrice=0;
	public String getbId() {
		return bId;
	}
	public void setbId(String bId) {
		this.bId = bId;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getgId() {
		return gId;
	}
	public void setgId(String gId) {
		this.gId = gId;
	}
	public String getgName() {
		return gName;
	}
	public void setgName(String gName) {
		this.gName = gName;
	}
	public String getgClass() {
		return gClass;
	}
	public void setgClass(String gClass) {
		this.gClass = gClass;
	}
	public String getbTime() {
		return bTime;
	}
	public void setbTime(String bTime) {
		this.bTime = bTime;
	}
	public int getbPrice() {
		return bPrice;
	}
	public void setbPrice(int bPrice) {
		this.bPrice = bPrice;
	}
	public int getbNumber() {
		return bNumber;
	}
	public void setbNumber(int bNumber) {
		this.bNumber = bNumber;
	}
	public int getbType() {
		return bType;
	}
	public void setbType(int bType) {
		this.bType = bType;
	}
	public int getgPrice() {
		return gPrice;
	}
	public void setgPrice(int gPrice) {
		this.gPrice = gPrice;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bId == null) ? 0 : bId.hashCode());
		result = prime * result + bNumber;
		result = prime * result + bPrice;
		result = prime * result + ((bTime == null) ? 0 : bTime.hashCode());
		result = prime * result + bType;
		result = prime * result + ((gClass == null) ? 0 : gClass.hashCode());
		result = prime * result + ((gId == null) ? 0 : gId.hashCode());
		result = prime * result + ((gName == null) ? 0 : gName.hashCode());
		result = prime * result + gPrice;
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
		BuyOrBrowse other = (BuyOrBrowse) obj;
		if (bId == null) {
			if (other.bId != null)
				return false;
		} else if (!bId.equals(other.bId))
			return false;
		if (bNumber != other.bNumber)
			return false;
		if (bPrice != other.bPrice)
			return false;
		if (bTime == null) {
			if (other.bTime != null)
				return false;
		} else if (!bTime.equals(other.bTime))
			return false;
		if (bType != other.bType)
			return false;
		if (gClass == null) {
			if (other.gClass != null)
				return false;
		} else if (!gClass.equals(other.gClass))
			return false;
		if (gId == null) {
			if (other.gId != null)
				return false;
		} else if (!gId.equals(other.gId))
			return false;
		if (gName == null) {
			if (other.gName != null)
				return false;
		} else if (!gName.equals(other.gName))
			return false;
		if (gPrice != other.gPrice)
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
		return "BuyOrBrowse [bId=" + bId + ", uId=" + uId + ", gId=" + gId + ", gName=" + gName + ", gClass=" + gClass
				+ ", bTime=" + bTime + ", bPrice=" + bPrice + ", bNumber=" + bNumber + ", bType=" + bType + ", gPrice="
				+ gPrice + "]";
	}
	public BuyOrBrowse(String bId, String uId, String gId, String gName, String gClass, String bTime, int bPrice,
			int bNumber, int bType, int gPrice) {
		super();
		this.bId = bId;
		this.uId = uId;
		this.gId = gId;
		this.gName = gName;
		this.gClass = gClass;
		this.bTime = bTime;
		this.bPrice = bPrice;
		this.bNumber = bNumber;
		this.bType = bType;
		this.gPrice = gPrice;
	}
	public BuyOrBrowse() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
