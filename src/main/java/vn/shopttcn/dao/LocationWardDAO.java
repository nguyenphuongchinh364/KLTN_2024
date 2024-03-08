package vn.shopttcn.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vn.shopttcn.model.LocationWard;

@Repository
public class LocationWardDAO extends AbstractDAO<LocationWard> {

	@Override
	public List<LocationWard> getAll() {
		String sql = "SELECT * FROM location_ward";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(LocationWard.class));
	}

	@Override
	public LocationWard findById(int id) {
		try {
			String sql = "SELECT * FROM location_ward WHERE wardId = ?";
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(LocationWard.class), id);
		} catch (Exception e) {
			System.err.println("Error: findById LocationWardDAO");
		}
		return null;
	}

	public List<LocationWard> findByDistrictId(int districtId) {
		String sql = "SELECT * FROM location_ward WHERE districtId = ?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(LocationWard.class), districtId);
	}
}
