package vn.shopttcn.model;

public class LocationWard {
	private int wardId;
	private String wardName;
	private int districtId;

	public LocationWard(int wardId, String wardName) {
		super();
		this.wardId = wardId;
		this.wardName = wardName;
	}

	public int getWardId() {
		return wardId;
	}

	public void setWardId(int wardId) {
		this.wardId = wardId;
	}

	public String getWardName() {
		return wardName;
	}

	public void setWardName(String wardName) {
		this.wardName = wardName;
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public LocationWard(int wardId, String wardName, int districtId) {
		super();
		this.wardId = wardId;
		this.wardName = wardName;
		this.districtId = districtId;
	}

	public LocationWard() {
	}

}
