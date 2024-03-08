package vn.shopttcn.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vn.shopttcn.model.LocationProvince;

@Repository
public class LocationProvinceDAO extends AbstractDAO<LocationProvince> {

	@Override
	public List<LocationProvince> getAll() {
		String sql = "SELECT * FROM location_province";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(LocationProvince.class));
	}

	@Override
	public LocationProvince findById(int id) {
		try {
			String sql = "SELECT * FROM location_province WHERE provinceId = ?";
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(LocationProvince.class), id);
		} catch (Exception e) {
			System.err.println("Error: findById LocationProvinceDAO");
		}
		return null;
	}

}
