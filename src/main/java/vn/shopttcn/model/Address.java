package vn.shopttcn.model;

import javax.validation.constraints.NotEmpty;

public class Address {
	private int addressId;
	private LocationProvince province;
	private LocationDistrict district;
	private LocationWard ward;

	@NotEmpty
	private String addressDetail;

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public LocationProvince getProvince() {
		return province;
	}

	public void setProvince(LocationProvince province) {
		this.province = province;
	}

	public LocationDistrict getDistrict() {
		return district;
	}

	public void setDistrict(LocationDistrict district) {
		this.district = district;
	}

	public LocationWard getWard() {
		return ward;
	}

	public void setWard(LocationWard ward) {
		this.ward = ward;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public Address(int addressId, LocationProvince province, LocationDistrict district, LocationWard ward,
			@NotEmpty String addressDetail) {
		super();
		this.addressId = addressId;
		this.province = province;
		this.district = district;
		this.ward = ward;
		this.addressDetail = addressDetail;
	}

	public Address() {
	}

}
