package vn.shopttcn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.shopttcn.dao.LocationDistrictDAO;
import vn.shopttcn.model.LocationDistrict;

@Service
public class LocationDistrictService implements CRUDService<LocationDistrict> {

	@Autowired
	private LocationDistrictDAO locationDistrictDAO;

	@Override
	public List<LocationDistrict> getAll() {
		return locationDistrictDAO.getAll();
	}

	@Override
	public int save(LocationDistrict t) {
		return 0;
	}

	@Override
	public int update(LocationDistrict t) {
		return 0;
	}

	@Override
	public int del(int id) {
		return 0;
	}

	@Override
	public LocationDistrict findById(int id) {
		return locationDistrictDAO.findById(id);
	}

	public List<LocationDistrict> findByProvinceId(int provinceId) {
		return locationDistrictDAO.findByProvinceId(provinceId);
	}

}
