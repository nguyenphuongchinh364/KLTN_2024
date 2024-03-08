package vn.shopttcn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.shopttcn.dao.LocationProvinceDAO;
import vn.shopttcn.model.LocationProvince;

@Service
public class LocationProvinceService implements CRUDService<LocationProvince> {

	@Autowired
	private LocationProvinceDAO locationProvinceDAO;

	@Override
	public List<LocationProvince> getAll() {
		return locationProvinceDAO.getAll();
	}

	@Override
	public int save(LocationProvince t) {
		return 0;
	}

	@Override
	public int update(LocationProvince t) {
		return 0;
	}

	@Override
	public int del(int id) {
		return 0;
	}

	@Override
	public LocationProvince findById(int id) {
		return locationProvinceDAO.findById(id);
	}

}
