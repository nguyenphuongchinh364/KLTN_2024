package vn.shopttcn.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vn.shopttcn.model.OrderDetail;

@Repository
public class OrderDetailDAO extends AbstractDAO<OrderDetail> {

	public List<OrderDetail> findByOrderId(int orderId) {
		String sql = "SELECT * FROM order_detail WHERE orderId = ? ORDER BY orderDetailId DESC";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrderDetail.class), orderId);
	}

	public List<OrderDetail> findByProductId(int productId) {
		String sql = "SELECT * FROM order_detail WHERE productId = ? ORDER BY orderDetailId DESC";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrderDetail.class), productId);
	}

	@Override
	public int save(OrderDetail orderDetail) {
		String sql = "INSERT INTO order_detail(orderId,productId,orderProductName,orderProductPrice,orderProductQuantity) VALUES (?,?,?,?,?)";
		return jdbcTemplate.update(sql, orderDetail.getOrderId(), orderDetail.getProductId(),
				orderDetail.getOrderProductName(), orderDetail.getOrderProductPrice(),
				orderDetail.getOrderProductQuantity());
	}

	// kiểm tra việc người dùng mua sản phẩm => đánh giá (đã mua mới có quyền đánh
	// giá)
	public boolean checkProductInOrder(int orderId, int productId, int userId) {
		String sql = "SELECT * FROM order_detail od INNER JOIN orders o ON od.orderId = o.orderId"
				+ " WHERE od.productId = ? AND od.orderId = ? AND o.userId = ? AND o.orderStatus = 4";
		List<OrderDetail> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrderDetail.class), productId,
				orderId, userId);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

}
