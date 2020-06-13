package jWeb.pojo;

public class Goods {
	private String gId="--";
	private String gName="--";
	private String gBelong="--";
	private String gClass="--";
	private String gDescribe="--";
	private int gPrice=0;
	private int gNumber=0;
	private String gUrl=" ";
	private int saleNumber=0;
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
	public String getgBelong() {
		return gBelong;
	}
	public void setgBelong(String gBelong) {
		this.gBelong = gBelong;
	}
	public String getgClass() {
		return gClass;
	}
	public void setgClass(String gClass) {
		this.gClass = gClass;
	}
	public String getgDescribe() {
		return gDescribe;
	}
	public void setgDescribe(String gDescribe) {
		this.gDescribe = gDescribe;
	}
	public int getgPrice() {
		return gPrice;
	}
	public void setgPrice(int gPrice) {
		this.gPrice = gPrice;
	}
	public int getgNumber() {
		return gNumber;
	}
	public void setgNumber(int gNumber) {
		this.gNumber = gNumber;
	}
	public String getgUrl() {
		return gUrl;
	}
	public void setgUrl(String gUrl) {
		this.gUrl = gUrl;
	}
	public int getSaleNumber() {
		return saleNumber;
	}
	public void setSaleNumber(int saleNumber) {
		this.saleNumber = saleNumber;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gBelong == null) ? 0 : gBelong.hashCode());
		result = prime * result + ((gClass == null) ? 0 : gClass.hashCode());
		result = prime * result + ((gDescribe == null) ? 0 : gDescribe.hashCode());
		result = prime * result + ((gId == null) ? 0 : gId.hashCode());
		result = prime * result + ((gName == null) ? 0 : gName.hashCode());
		result = prime * result + gNumber;
		result = prime * result + gPrice;
		result = prime * result + ((gUrl == null) ? 0 : gUrl.hashCode());
		result = prime * result + saleNumber;
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
		Goods other = (Goods) obj;
		if (gBelong == null) {
			if (other.gBelong != null)
				return false;
		} else if (!gBelong.equals(other.gBelong))
			return false;
		if (gClass == null) {
			if (other.gClass != null)
				return false;
		} else if (!gClass.equals(other.gClass))
			return false;
		if (gDescribe == null) {
			if (other.gDescribe != null)
				return false;
		} else if (!gDescribe.equals(other.gDescribe))
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
		if (gNumber != other.gNumber)
			return false;
		if (gPrice != other.gPrice)
			return false;
		if (gUrl == null) {
			if (other.gUrl != null)
				return false;
		} else if (!gUrl.equals(other.gUrl))
			return false;
		if (saleNumber != other.saleNumber)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Goods [gId=" + gId + ", gName=" + gName + ", gBelong=" + gBelong + ", gClass=" + gClass + ", gDescribe="
				+ gDescribe + ", gPrice=" + gPrice + ", gNumber=" + gNumber + ", gUrl=" + gUrl + ", saleNumber="
				+ saleNumber + "]";
	}
	public Goods(String gId, String gName, String gBelong, String gClass, String gDescribe, int gPrice, int gNumber,
			String gUrl, int saleNumber) {
		super();
		this.gId = gId;
		this.gName = gName;
		this.gBelong = gBelong;
		this.gClass = gClass;
		this.gDescribe = gDescribe;
		this.gPrice = gPrice;
		this.gNumber = gNumber;
		this.gUrl = gUrl;
		this.saleNumber = saleNumber;
	}
	public Goods() {
		super();
		// TODO Auto-generated constructor stub
	}
}
