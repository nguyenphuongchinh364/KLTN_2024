package vn.shopttcn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.shopttcn.dao.LocationWardDAO;
import vn.shopttcn.model.LocationWard;

@Service
public class LocationWardService implements CRUDService<LocationWard> {

	@Autowired
	private LocationWardDAO locationWardDAO;

	@Override
	public List<LocationWard> getAll() {
		return locationWardDAO.getAll();
	}

	@Override
	public int save(LocationWard t) {
		return 0;
	}

	@Override
	public int update(LocationWard t) {
		return 0;
	}

	@Override
	public int del(int id) {
		return 0;
	}

	@Override
	public LocationWard findById(int id) {
		return locationWardDAO.findById(id);
	}

	public List<LocationWard> findByDistrictId(int districtId) {
		return locationWardDAO.findByDistrictId(districtId);
	}

}
