package vn.shopttcn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.shopttcn.dao.OrderDAO;
import vn.shopttcn.model.Order;

@Service
public class OrderService implements CRUDService<Order> {

	@Autowired
	private OrderDAO orderDAO;

	@Override
	public List<Order> getAll() {
		return null;
	}

	@Override
	public int save(Order order) {
		return orderDAO.save(order);
	}

	@Override
	public int update(Order order) {
		return 0;
	}

	public int updateStatus(Order order) {
		return orderDAO.updateStatus(order);
	}

	public int updatePayment(Order order) {
		return orderDAO.updatePayment(order);
	}

	public int updateMod(Order order) {
		return orderDAO.updateMod(order);
	}

	@Override
	public int del(int id) {
		return orderDAO.del(id);
	}

	@Override
	public Order findById(int id) {
		return orderDAO.findById(id);
	}

	public List<Order> getList(int offset, int rowCount) {
		return orderDAO.getList(offset, rowCount);
	}

	public int totalRow() {
		return orderDAO.totalRow();
	}

	public Order getNewOrder() {
		return orderDAO.getNewOrder();
	}

	public List<Order> findByUser(int userId, int offset, int rowCount) {
		return orderDAO.findByUser(userId, offset, rowCount);
	}

	public int totalRowByUser(int userId) {
		return orderDAO.totalRowByUser(userId);
	}

	public Order findByMod(int orderId, int userId) {
		return orderDAO.findByMod(orderId, userId);
	}

	public long sumPriceInMonth(String dateBegin, String dateEnd) {
		return orderDAO.sumPriceInMonth(dateBegin, dateEnd);
	}

	public int countOrderByMod(int modId, String dateBegin, String dateEnd) {
		return orderDAO.countOrderByMod(modId, dateBegin, dateEnd);
	}

	public int countOrderProductByMod(int modId, String dateBegin, String dateEnd) {
		return orderDAO.countOrderProductByMod(modId, dateBegin, dateEnd);
	}

	public long sumOrderPriceByMod(int modId, String dateBegin, String dateEnd) {
		return orderDAO.sumOrderPriceByMod(modId, dateBegin, dateEnd);
	}

	public List<Order> search(String orderName, String dateCreate, int orderStatus, int modId, int offset,
			int rowCount) {
		return orderDAO.search(orderName, dateCreate, orderStatus, modId, offset, rowCount);
	}

	public int totalRowSearch(String orderName, String dateCreate, int orderStatus, int modId) {
		return orderDAO.totalRowSearch(orderName, dateCreate, orderStatus, modId);
	}

}
