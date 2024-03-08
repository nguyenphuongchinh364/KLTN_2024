package vn.shopttcn.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vn.shopttcn.model.LocationDistrict;

@Repository
public class LocationDistrictDAO extends AbstractDAO<LocationDistrict> {

	@Override
	public List<LocationDistrict> getAll() {
		String sql = "SELECT * FROM location_district";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(LocationDistrict.class));
	}

	@Override
	public LocationDistrict findById(int id) {
		try {
			String sql = "SELECT * FROM location_district WHERE districtId = ?";
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(LocationDistrict.class), id);
		} catch (Exception e) {
			System.err.println("Error: findById LocationDistrictDAO");
		}
		return null;
	}

	public List<LocationDistrict> findByProvinceId(int provinceId) {
		String sql = "SELECT * FROM location_district WHERE provinceId = ?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(LocationDistrict.class), provinceId);
	}

}
