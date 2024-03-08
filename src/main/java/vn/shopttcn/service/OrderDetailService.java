package vn.shopttcn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.shopttcn.dao.OrderDetailDAO;
import vn.shopttcn.model.OrderDetail;

@Service
public class OrderDetailService implements CRUDService<OrderDetail> {

	@Autowired
	private OrderDetailDAO orderDetailDAO;

	@Override
	public List<OrderDetail> getAll() {
		return null;
	}

	@Override
	public int save(OrderDetail orderDetail) {
		return orderDetailDAO.save(orderDetail);
	}

	@Override
	public int update(OrderDetail t) {
		return 0;
	}

	@Override
	public int del(int id) {
		return 0;
	}

	@Override
	public OrderDetail findById(int id) {
		return null;
	}

	public List<OrderDetail> findByOrderId(int orderId) {
		return orderDetailDAO.findByOrderId(orderId);
	}

	public List<OrderDetail> findByProductId(int productId) {
		return orderDetailDAO.findByProductId(productId);
	}

	public boolean checkProductInOrder(int orderId, int productId, int userId) {
		return orderDetailDAO.checkProductInOrder(orderId, productId, userId);
	}

}
