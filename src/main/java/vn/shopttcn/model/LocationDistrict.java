package vn.shopttcn.model;

public class LocationDistrict {
	private int districtId;
	private String districtName;
	private int provinceId;

	public LocationDistrict(int districtId, String districtName) {
		super();
		this.districtId = districtId;
		this.districtName = districtName;
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public LocationDistrict(int districtId, String districtName, int provinceId) {
		super();
		this.districtId = districtId;
		this.districtName = districtName;
		this.provinceId = provinceId;
	}

	public LocationDistrict() {
	}

}
