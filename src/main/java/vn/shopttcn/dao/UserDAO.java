package vn.shopttcn.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import vn.shopttcn.constant.GlobalConstant;
import vn.shopttcn.model.Address;
import vn.shopttcn.model.LocationDistrict;
import vn.shopttcn.model.LocationProvince;
import vn.shopttcn.model.LocationWard;
import vn.shopttcn.model.Role;
import vn.shopttcn.model.User;

@Repository
public class UserDAO extends AbstractDAO<User> {

	public List<User> getList(int offset, int rowCount) {
		String sql = "SELECT * FROM users AS u INNER JOIN address AS a ON u.addressId = a.addressId"
				+ " INNER JOIN location_province AS p ON a.provinceId = p.provinceId"
				+ " INNER JOIN location_district AS d ON a.districtId = d.districtId"
				+ " INNER JOIN location_ward AS w ON a.wardId = w.wardId"
				+ " INNER JOIN roles AS r ON u.roleId = r.roleId ORDER BY userId DESC LIMIT ?,?";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<User>>() {
			List<User> list = new ArrayList<User>();

			@Override
			public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new User(rs.getInt("userId"), rs.getString("userFullname"), rs.getString("userEmail"),
							rs.getString("userPhone"),
							new Address(rs.getInt("a.addressId"),
									new LocationProvince(rs.getInt("a.provinceId"), rs.getString("provinceName")),
									new LocationDistrict(rs.getInt("a.districtId"), rs.getString("districtName")),
									new LocationWard(rs.getInt("a.wardId"), rs.getString("wardName")),
									rs.getString("addressDetail")),
							rs.getString("avatar"), rs.getString("username"), rs.getString("password"),
							new Role(rs.getInt("r.roleId"), rs.getString("roleName"), rs.getString("roleDesc")),
							rs.getInt("enabled"), rs.getTimestamp("createAt"), rs.getTimestamp("updateAt")));
				}
				return list;
			}
		}, offset, rowCount);
	}

	public int totalRow() {
		String sql = "SELECT COUNT(*) FROM users AS u INNER JOIN address AS a ON u.addressId = a.addressId"
				+ " INNER JOIN location_province AS p ON a.provinceId = p.provinceId"
				+ " INNER JOIN location_district AS d ON a.districtId = d.districtId"
				+ " INNER JOIN location_ward AS w ON a.wardId = w.wardId"
				+ " INNER JOIN roles AS r ON u.roleId = r.roleId";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@Override
	public User findById(int id) {
		try {
			String sql = "SELECT * FROM users AS u INNER JOIN address AS a ON u.addressId = a.addressId"
					+ " INNER JOIN location_province AS p ON a.provinceId = p.provinceId"
					+ " INNER JOIN location_district AS d ON a.districtId = d.districtId"
					+ " INNER JOIN location_ward AS w ON a.wardId = w.wardId"
					+ " INNER JOIN roles AS r ON u.roleId = r.roleId WHERE userId = ?";
			return jdbcTemplate.query(sql, new ResultSetExtractor<User>() {

				@Override
				public User extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return new User(rs.getInt("userId"), rs.getString("userFullname"), rs.getString("userEmail"),
								rs.getString("userPhone"),
								new Address(rs.getInt("a.addressId"),
										new LocationProvince(rs.getInt("a.provinceId"), rs.getString("provinceName")),
										new LocationDistrict(rs.getInt("a.districtId"), rs.getString("districtName")),
										new LocationWard(rs.getInt("a.wardId"), rs.getString("wardName")),
										rs.getString("addressDetail")),
								rs.getString("avatar"), rs.getString("username"), rs.getString("password"),
								new Role(rs.getInt("r.roleId"), rs.getString("roleName"), rs.getString("roleDesc")),
								rs.getInt("enabled"), rs.getTimestamp("createAt"), rs.getTimestamp("updateAt"));
					}
					return null;
				}
			}, id);
		} catch (Exception e) {
			System.err.println("Error: findById UserDAO");
		}
		return null;
	}

	@Override
	public int save(User user) {
		String sql = "INSERT INTO users(userFullname,userEmail,userPhone,addressId,avatar,username,password,roleId) VALUES (?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, user.getUserFullname(), user.getUserEmail(), user.getUserPhone(),
				user.getUserAddress().getAddressId(), user.getAvatar(), user.getUsername(), user.getPassword(),
				user.getRole().getRoleId());
	}

	@Override
	public int update(User user) {
		String sql = "UPDATE users SET userFullname = ?, userEmail = ?, userPhone = ?, avatar = ?, roleId = ? WHERE userId = ?";
		return jdbcTemplate.update(sql, user.getUserFullname(), user.getUserEmail(), user.getUserPhone(),
				user.getAvatar(), user.getRole().getRoleId(), user.getUserId());
	}

	// update enabled (1: đã kích hoạt, 0: vô hiệu hoá)
	public int updateStatus(User user) {
		String sql = "UPDATE users SET enabled = ? WHERE userId = ?";
		return jdbcTemplate.update(sql, user.getEnabled(), user.getUserId());
	}

	public int updatePassword(User user) {
		String sql = "UPDATE users SET password = ? WHERE userId = ?";
		return jdbcTemplate.update(sql, user.getPassword(), user.getUserId());
	}

	@Override
	public int del(int id) {
		String sql = "DELETE FROM users WHERE userId = ?";
		return jdbcTemplate.update(sql, id);
	}

	public User findByUsername(String username) {
		try {
			String sql = "SELECT * FROM users AS u INNER JOIN address AS a ON u.addressId = a.addressId"
					+ " INNER JOIN location_province AS p ON a.provinceId = p.provinceId"
					+ " INNER JOIN location_district AS d ON a.districtId = d.districtId"
					+ " INNER JOIN location_ward AS w ON a.wardId = w.wardId"
					+ " INNER JOIN roles AS r ON u.roleId = r.roleId WHERE BINARY username = ?";
			return jdbcTemplate.query(sql, new ResultSetExtractor<User>() {

				@Override
				public User extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return new User(rs.getInt("userId"), rs.getString("userFullname"), rs.getString("userEmail"),
								rs.getString("userPhone"),
								new Address(rs.getInt("a.addressId"),
										new LocationProvince(rs.getInt("a.provinceId"), rs.getString("provinceName")),
										new LocationDistrict(rs.getInt("a.districtId"), rs.getString("districtName")),
										new LocationWard(rs.getInt("a.wardId"), rs.getString("wardName")),
										rs.getString("addressDetail")),
								rs.getString("avatar"), rs.getString("username"), rs.getString("password"),
								new Role(rs.getInt("r.roleId"), rs.getString("roleName"), rs.getString("roleDesc")),
								rs.getInt("enabled"), rs.getTimestamp("createAt"), rs.getTimestamp("updateAt"));
					}
					return null;
				}
			}, username);
		} catch (Exception e) {
			System.err.println("Error: findByUsername UserDAO");
		}
		return null;
	}

	// check user trùng: username, email, phone (thuộc tính duy nhất)
	// properties: thuộc tính cần check (username, email, phone)
	public User findUserDuplicate(User user, User oldUser, String properties) {
		try {
			String sql = "SELECT * FROM users AS u INNER JOIN address AS a ON u.addressId = a.addressId"
					+ " INNER JOIN location_province AS p ON a.provinceId = p.provinceId"
					+ " INNER JOIN location_district AS d ON a.districtId = d.districtId"
					+ " INNER JOIN location_ward AS w ON a.wardId = w.wardId"
					+ " INNER JOIN roles AS r ON u.roleId = r.roleId";
			Object[] arr = new Object[2];
			if (properties.equals("username")) {
				sql += " WHERE BINARY username = ? AND BINARY username != ?";
				arr[0] = user.getUsername();
				arr[1] = oldUser.getUsername();
			}
			if (properties.equals("email")) {
				sql += " WHERE BINARY userEmail = ? AND BINARY userEmail != ?";
				arr[0] = user.getUserEmail();
				arr[1] = oldUser.getUserEmail();
			}
			if (properties.equals("phone")) {
				sql += " WHERE userPhone = ? AND userPhone != ?";
				arr[0] = user.getUserPhone();
				arr[1] = oldUser.getUserPhone();
			}
			return jdbcTemplate.query(sql, new ResultSetExtractor<User>() {

				@Override
				public User extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return new User(rs.getInt("userId"), rs.getString("userFullname"), rs.getString("userEmail"),
								rs.getString("userPhone"),
								new Address(rs.getInt("a.addressId"),
										new LocationProvince(rs.getInt("a.provinceId"), rs.getString("provinceName")),
										new LocationDistrict(rs.getInt("a.districtId"), rs.getString("districtName")),
										new LocationWard(rs.getInt("a.wardId"), rs.getString("wardName")),
										rs.getString("addressDetail")),
								rs.getString("avatar"), rs.getString("username"), rs.getString("password"),
								new Role(rs.getInt("r.roleId"), rs.getString("roleName"), rs.getString("roleDesc")),
								rs.getInt("enabled"), rs.getTimestamp("createAt"), rs.getTimestamp("updateAt"));
					}
					return null;
				}

			}, arr);
		} catch (Exception e) {
			System.err.println("Error: findUserDuplicate UserDAO");
		}
		return null;
	}

	// lấy danh sách nhân viên (MOD)
	public List<User> findUserByRole(int roleId) {
		String sql = "SELECT * FROM users AS u INNER JOIN address AS a ON u.addressId = a.addressId"
				+ " INNER JOIN location_province AS p ON a.provinceId = p.provinceId"
				+ " INNER JOIN location_district AS d ON a.districtId = d.districtId"
				+ " INNER JOIN location_ward AS w ON a.wardId = w.wardId"
				+ " INNER JOIN roles AS r ON u.roleId = r.roleId WHERE u.roleId = ? ORDER BY userId DESC";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<User>>() {
			List<User> list = new ArrayList<User>();

			@Override
			public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new User(rs.getInt("userId"), rs.getString("userFullname"), rs.getString("userEmail"),
							rs.getString("userPhone"),
							new Address(rs.getInt("a.addressId"),
									new LocationProvince(rs.getInt("a.provinceId"), rs.getString("provinceName")),
									new LocationDistrict(rs.getInt("a.districtId"), rs.getString("districtName")),
									new LocationWard(rs.getInt("a.wardId"), rs.getString("wardName")),
									rs.getString("addressDetail")),
							rs.getString("avatar"), rs.getString("username"), rs.getString("password"),
							new Role(rs.getInt("r.roleId"), rs.getString("roleName"), rs.getString("roleDesc")),
							rs.getInt("enabled"), rs.getTimestamp("createAt"), rs.getTimestamp("updateAt")));
				}
				return list;
			}
		}, roleId);
	}

	// search
	public List<User> search(String username, int enabled, int roleId, int offset, int rowCount) {
		List<Object> list = new ArrayList<Object>();
		String sql = "SELECT * FROM users AS u INNER JOIN address AS a ON u.addressId = a.addressId"
				+ " INNER JOIN location_province AS p ON a.provinceId = p.provinceId"
				+ " INNER JOIN location_district AS d ON a.districtId = d.districtId"
				+ " INNER JOIN location_ward AS w ON a.wardId = w.wardId"
				+ " INNER JOIN roles AS r ON u.roleId = r.roleId WHERE 1";
		if (!username.equals(GlobalConstant.EMPTY)) {
			sql += " AND username LIKE ?";
			list.add("%" + username + "%");
		}
		if (enabled == 0 || enabled == 1) {
			sql += " AND enabled = ?";
			list.add(enabled);
		}
		if (roleId != 0) {
			sql += " AND u.roleId = ?";
			list.add(roleId);
		}
		sql += " ORDER BY userId DESC LIMIT ?,?";
		list.add(offset);
		list.add(rowCount);
		Object[] arrObj = new Object[list.size()];
		arrObj = list.toArray();
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<User>>() {
			List<User> list = new ArrayList<User>();

			@Override
			public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new User(rs.getInt("userId"), rs.getString("userFullname"), rs.getString("userEmail"),
							rs.getString("userPhone"),
							new Address(rs.getInt("a.addressId"),
									new LocationProvince(rs.getInt("a.provinceId"), rs.getString("provinceName")),
									new LocationDistrict(rs.getInt("a.districtId"), rs.getString("districtName")),
									new LocationWard(rs.getInt("a.wardId"), rs.getString("wardName")),
									rs.getString("addressDetail")),
							rs.getString("avatar"), rs.getString("username"), rs.getString("password"),
							new Role(rs.getInt("r.roleId"), rs.getString("roleName"), rs.getString("roleDesc")),
							rs.getInt("enabled"), rs.getTimestamp("createAt"), rs.getTimestamp("updateAt")));
				}
				return list;
			}
		}, arrObj);
	}

	public int totalRowSearch(String username, int enabled, int roleId) {
		List<Object> list = new ArrayList<Object>();
		String sql = "SELECT COUNT(*) FROM users AS u INNER JOIN address AS a ON u.addressId = a.addressId"
				+ " INNER JOIN location_province AS p ON a.provinceId = p.provinceId"
				+ " INNER JOIN location_district AS d ON a.districtId = d.districtId"
				+ " INNER JOIN location_ward AS w ON a.wardId = w.wardId"
				+ " INNER JOIN roles AS r ON u.roleId = r.roleId WHERE 1";
		if (!username.equals(GlobalConstant.EMPTY)) {
			sql += " AND username LIKE ?";
			list.add("%" + username + "%");
		}
		if (enabled == 0 || enabled == 1) {
			sql += " AND enabled = ?";
			list.add(enabled);
		}
		if (roleId != 0) {
			sql += " AND u.roleId = ?";
			list.add(roleId);
		}
		Object[] arrObj = new Object[list.size()];
		arrObj = list.toArray();
		return jdbcTemplate.queryForObject(sql, Integer.class, arrObj);
	}

}
