package vn.shopttcn.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import vn.shopttcn.model.Address;
import vn.shopttcn.model.LocationDistrict;
import vn.shopttcn.model.LocationProvince;
import vn.shopttcn.model.LocationWard;

@Repository
public class AddressDAO extends AbstractDAO<Address> {

	@Override
	public int save(Address address) {
		String sql = "INSERT INTO address(provinceId,districtId,wardId,addressDetail) VALUES (?,?,?,?)";
		return jdbcTemplate.update(sql, address.getProvince().getProvinceId(), address.getDistrict().getDistrictId(),
				address.getWard().getWardId(), address.getAddressDetail());
	}

	@Override
	public int update(Address address) {
		String sql = "UPDATE address SET provinceId = ?, districtId = ?, wardId = ?, addressDetail = ? WHERE addressId = ?";
		return jdbcTemplate.update(sql, address.getProvince().getProvinceId(), address.getDistrict().getDistrictId(),
				address.getWard().getWardId(), address.getAddressDetail(), address.getAddressId());
	}

	@Override
	public int del(int id) {
		String sql = "DELETE FROM address WHERE addressId = ?";
		return jdbcTemplate.update(sql, id);
	}

	public Address getNewAddress() {
		try {
			String sql = "SELECT * FROM address a INNER JOIN location_province p ON a.provinceId = p.provinceId"
					+ " INNER JOIN location_district d ON a.districtId = d.districtId"
					+ " INNER JOIN location_ward w ON a.wardId = w.wardId ORDER BY addressId DESC LIMIT 1";
			return jdbcTemplate.query(sql, new ResultSetExtractor<Address>() {

				@Override
				public Address extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return new Address(rs.getInt("addressId"),
								new LocationProvince(rs.getInt("p.provinceId"), rs.getString("provinceName")),
								new LocationDistrict(rs.getInt("d.districtId"), rs.getString("districtName")),
								new LocationWard(rs.getInt("w.wardId"), rs.getString("wardName")),
								rs.getString("addressDetail"));
					}
					return null;
				}
			});
		} catch (Exception e) {
			System.err.println("Error: getNewAddress AddressDAO");
		}
		return null;
	}

}
