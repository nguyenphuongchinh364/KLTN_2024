package vn.shopttcn.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import vn.shopttcn.constant.GlobalConstant;
import vn.shopttcn.model.Order;
import vn.shopttcn.model.User;

@Repository
public class OrderDAO extends AbstractDAO<Order> {

	public List<Order> getList(int offset, int rowCount) {
		String sql = "SELECT * FROM orders o LEFT JOIN users u ON o.modId = u.userId ORDER BY orderId DESC LIMIT ?,?";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Order>>() {
			List<Order> list = new ArrayList<Order>();

			@Override
			public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new Order(rs.getInt("orderId"), rs.getInt("orderTotalQuantity"),
							rs.getInt("orderTotalPrice"), rs.getInt("o.userId"), rs.getString("orderName"),
							rs.getString("orderEmail"), rs.getString("orderPhone"), rs.getString("orderAddress"),
							rs.getString("orderNote"), rs.getInt("orderStatus"), rs.getInt("orderPayment"),
							new User(rs.getInt("u.userId"), rs.getString("userFullname"), rs.getString("username")),
							rs.getTimestamp("o.createAt"), rs.getTimestamp("o.updateAt")));
				}
				return list;
			}

		}, offset, rowCount);
	}

	public int totalRow() {
		String sql = "SELECT COUNT(*) FROM orders o LEFT JOIN users u ON o.modId = u.userId";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@Override
	public Order findById(int id) {
		try {
			String sql = "SELECT * FROM orders o LEFT JOIN users u ON o.modId = u.userId WHERE orderId = ?";
			return jdbcTemplate.query(sql, new ResultSetExtractor<Order>() {

				@Override
				public Order extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return new Order(rs.getInt("orderId"), rs.getInt("orderTotalQuantity"),
								rs.getInt("orderTotalPrice"), rs.getInt("o.userId"), rs.getString("orderName"),
								rs.getString("orderEmail"), rs.getString("orderPhone"), rs.getString("orderAddress"),
								rs.getString("orderNote"), rs.getInt("orderStatus"), rs.getInt("orderPayment"),
								new User(rs.getInt("u.userId"), rs.getString("userFullname"), rs.getString("username")),
								rs.getTimestamp("o.createAt"), rs.getTimestamp("o.updateAt"));
					}
					return null;
				}
			}, id);
		} catch (Exception e) {
			System.err.println("Error: findById OrderDAO");
		}
		return null;
	}

	@Override
	public int save(Order order) {
		String sql = "INSERT INTO orders(orderTotalQuantity,orderTotalPrice,userId,orderName,orderEmail,orderPhone,orderAddress,orderNote,orderPayment) VALUES (?,?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, order.getOrderTotalQuantity(), order.getOrderTotalPrice(), order.getUserId(),
				order.getOrderName(), order.getOrderEmail(), order.getOrderPhone(), order.getOrderAddress(),
				order.getOrderNote(), order.getOrderPayment());
	}

	// Cập nhật trạng thái đơn hàng (nhân viên xử lý)
	public int updateStatus(Order order) {
		String sql = "UPDATE orders SET orderStatus = ? WHERE orderId = ?";
		return jdbcTemplate.update(sql, order.getOrderStatus(), order.getOrderId());
	}

	// Cập nhật trạng thái thanh toán (nhân viên xử lý)
	public int updatePayment(Order order) {
		String sql = "UPDATE orders SET orderPayment = ? WHERE orderId = ?";
		return jdbcTemplate.update(sql, order.getOrderPayment(), order.getOrderId());
	}

	// Cập nhật nhân viên xử lý đơn hàng (duy nhất)
	public int updateMod(Order order) {
		String sql = "UPDATE orders SET modId = ? WHERE orderId = ?";
		return jdbcTemplate.update(sql, order.getUser().getUserId(), order.getOrderId());
	}

	@Override
	public int del(int id) {
		String sql = "DELETE FROM orders WHERE orderId = ?";
		return jdbcTemplate.update(sql, id);
	}

	// Lấy đơn hàng mới nhất
	public Order getNewOrder() {
		try {
			String sql = "SELECT * FROM orders ORDER BY orderId DESC LIMIT 1";
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Order.class));
		} catch (Exception e) {
			System.err.println("Error: getNewOrder OrderDAO");
		}
		return null;
	}

	// lấy đơn hàng theo userId (khách hàng)
	public List<Order> findByUser(int userId, int offset, int rowCount) {
		String sql = "SELECT * FROM orders o LEFT JOIN users u ON o.modId = u.userId WHERE o.userId = ? ORDER BY orderId DESC LIMIT ?,?";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Order>>() {
			List<Order> list = new ArrayList<Order>();

			@Override
			public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new Order(rs.getInt("orderId"), rs.getInt("orderTotalQuantity"),
							rs.getInt("orderTotalPrice"), rs.getInt("o.userId"), rs.getString("orderName"),
							rs.getString("orderEmail"), rs.getString("orderPhone"), rs.getString("orderAddress"),
							rs.getString("orderNote"), rs.getInt("orderStatus"), rs.getInt("orderPayment"),
							new User(rs.getInt("u.userId"), rs.getString("userFullname"), rs.getString("username")),
							rs.getTimestamp("o.createAt"), rs.getTimestamp("o.updateAt")));
				}
				return list;
			}

		}, userId, offset, rowCount);
	}

	public int totalRowByUser(int userId) {
		String sql = "SELECT COUNT(*) FROM orders o LEFT JOIN users u ON o.modId = u.userId WHERE o.userId = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, userId);
	}

	// check nhân viên xử lý đơn hàng (cập nhật trạng thái đơn hàng)
	public Order findByMod(int orderId, int userId) {
		try {
			String sql = "SELECT * FROM orders o LEFT JOIN users u ON o.modId = u.userId WHERE o.modId = ? AND o.orderId = ?";
			return jdbcTemplate.query(sql, new ResultSetExtractor<Order>() {

				@Override
				public Order extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return new Order(rs.getInt("orderId"), rs.getInt("orderTotalQuantity"),
								rs.getInt("orderTotalPrice"), rs.getInt("o.userId"), rs.getString("orderName"),
								rs.getString("orderEmail"), rs.getString("orderPhone"), rs.getString("orderAddress"),
								rs.getString("orderNote"), rs.getInt("orderStatus"), rs.getInt("orderPayment"),
								new User(rs.getInt("u.userId"), rs.getString("userFullname"), rs.getString("username")),
								rs.getTimestamp("o.createAt"), rs.getTimestamp("o.updateAt"));
					}
					return null;
				}
			}, userId, orderId);
		} catch (Exception e) {
			System.err.println("Error: findByMod OrderDAO");
		}
		return null;
	}

	// Thống kê
	// thống kê doanh thu theo tháng
	public long sumPriceInMonth(String dateBegin, String dateEnd) {
		try {
			String sql = "SELECT SUM(orderTotalPrice) FROM orders WHERE orderStatus != 5"
					+ " AND (createAt BETWEEN ? AND DATE_ADD(?,INTERVAL 1 DAY))";
			return jdbcTemplate.queryForObject(sql, Long.class, dateBegin, dateEnd);
		} catch (Exception e) {
			System.err.println("Error: sumPriceInMonth OrderDAO");
		}
		return 0;
	}

	// đếm số lượng đơn hàng theo nhân viên
	public int countOrderByMod(int modId, String dateBegin, String dateEnd) {
		try {
			String sql = "SELECT COUNT(*) FROM orders WHERE orderStatus != 5 AND modId = ?";
			List<Object> list = new ArrayList<Object>();
			list.add(modId);
			if (!dateBegin.equals(GlobalConstant.EMPTY)) {
				sql += " AND (createAt BETWEEN ? AND DATE_ADD(?,INTERVAL 1 DAY))";
				list.add(dateBegin);
				list.add(dateEnd);
			}
			Object[] arrObj = new Object[list.size()];
			list.toArray(arrObj);
			return jdbcTemplate.queryForObject(sql, Integer.class, arrObj);
		} catch (Exception e) {
			System.err.println("Error: countOrderByMod OrderDAO");
		}
		return 0;
	}

	// tính tổng số lượng sản phẩm theo nhân viên
	public int countOrderProductByMod(int modId, String dateBegin, String dateEnd) {
		try {
			String sql = "SELECT SUM(orderTotalQuantity) FROM orders WHERE orderStatus != 5 AND modId = ?";
			List<Object> list = new ArrayList<Object>();
			list.add(modId);
			if (!dateBegin.equals(GlobalConstant.EMPTY)) {
				sql += " AND (createAt BETWEEN ? AND DATE_ADD(?,INTERVAL 1 DAY))";
				list.add(dateBegin);
				list.add(dateEnd);
			}
			Object[] arrObj = new Object[list.size()];
			list.toArray(arrObj);
			return jdbcTemplate.queryForObject(sql, Integer.class, arrObj);
		} catch (Exception e) {
			System.err.println("Error: countOrderProductByMod OrderDAO");
		}
		return 0;
	}

	// tính tổng doanh thu theo nhân viên
	public long sumOrderPriceByMod(int modId, String dateBegin, String dateEnd) {
		try {
			String sql = "SELECT SUM(orderTotalPrice) FROM orders WHERE orderStatus != 5 AND modId = ?";
			List<Object> list = new ArrayList<Object>();
			list.add(modId);
			if (!dateBegin.equals(GlobalConstant.EMPTY)) {
				sql += " AND (createAt BETWEEN ? AND DATE_ADD(?,INTERVAL 1 DAY))";
				list.add(dateBegin);
				list.add(dateEnd);
			}
			Object[] arrObj = new Object[list.size()];
			list.toArray(arrObj);
			return jdbcTemplate.queryForObject(sql, Long.class, arrObj);
		} catch (Exception e) {
			System.err.println("Error: sumOrderPriceByMod OrderDAO");
		}
		return 0;
	}

	// search
	public List<Order> search(String orderName, String dateCreate, int orderStatus, int modId, int offset,
			int rowCount) {
		List<Object> list = new ArrayList<Object>();
		String sql = "SELECT * FROM orders o LEFT JOIN users u ON o.modId = u.userId WHERE 1";
		if (!orderName.equals(GlobalConstant.EMPTY)) {
			sql += " AND orderName LIKE ?";
			list.add("%" + orderName + "%");
		}
		if (!dateCreate.equals(GlobalConstant.EMPTY)) {
			sql += " AND (o.createAt BETWEEN ? AND DATE_ADD(?,INTERVAL 1 DAY))";
			list.add(dateCreate);
			list.add(dateCreate);
		}
		if (orderStatus != -1) { // -1: constant (không tìm kiếm theo trạng thái)
			sql += " AND orderStatus = ?";
			list.add(orderStatus);
		}
		if (modId != -1) { // -1: constant (không tìm kiếm theo nhân viên)
			sql += " AND modId = ?";
			list.add(modId);
		}
		sql += " ORDER BY orderId DESC LIMIT ?,?";
		list.add(offset);
		list.add(rowCount);
		Object[] arrObj = new Object[list.size()];
		arrObj = list.toArray();
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Order>>() {
			List<Order> list = new ArrayList<Order>();

			@Override
			public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new Order(rs.getInt("orderId"), rs.getInt("orderTotalQuantity"),
							rs.getInt("orderTotalPrice"), rs.getInt("o.userId"), rs.getString("orderName"),
							rs.getString("orderEmail"), rs.getString("orderPhone"), rs.getString("orderAddress"),
							rs.getString("orderNote"), rs.getInt("orderStatus"), rs.getInt("orderPayment"),
							new User(rs.getInt("u.userId"), rs.getString("userFullname"), rs.getString("username")),
							rs.getTimestamp("o.createAt"), rs.getTimestamp("o.updateAt")));
				}
				return list;
			}

		}, arrObj);
	}

	public int totalRowSearch(String orderName, String dateCreate, int orderStatus, int modId) {
		List<Object> list = new ArrayList<Object>();
		String sql = "SELECT COUNT(*) FROM orders o LEFT JOIN users u ON o.modId = u.userId WHERE 1";
		if (!orderName.equals(GlobalConstant.EMPTY)) {
			sql += " AND orderName LIKE ?";
			list.add("%" + orderName + "%");
		}
		if (!dateCreate.equals(GlobalConstant.EMPTY)) {
			sql += " AND (o.createAt BETWEEN ? AND DATE_ADD(?,INTERVAL 1 DAY))";
			list.add(dateCreate);
			list.add(dateCreate);
		}
		if (orderStatus != -1) { // -1: constant (không tìm kiếm theo trạng thái)
			sql += " AND orderStatus = ?";
			list.add(orderStatus);
		}
		if (modId != -1) { // -1: constant (không tìm kiếm theo nhân viên)
			sql += " AND modId = ?";
			list.add(modId);
		}
		Object[] arrObj = new Object[list.size()];
		arrObj = list.toArray();
		return jdbcTemplate.queryForObject(sql, Integer.class, arrObj);
	}

}
