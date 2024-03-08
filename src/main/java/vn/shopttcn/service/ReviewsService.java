package vn.shopttcn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.shopttcn.dao.ReviewsDAO;
import vn.shopttcn.model.Reviews;

@Service
public class ReviewsService implements CRUDService<Reviews> {

	@Autowired
	private ReviewsDAO reviewsDAO;

	@Override
	public List<Reviews> getAll() {
		return null;
	}

	@Override
	public int save(Reviews reviews) {
		return reviewsDAO.save(reviews);
	}

	@Override
	public int update(Reviews reviews) {
		return reviewsDAO.update(reviews);
	}

	public int updateStatus(Reviews reviews) {
		return reviewsDAO.updateStatus(reviews);
	}

	@Override
	public int del(int id) {
		return reviewsDAO.del(id);
	}

	public int delByProductId(int productId) {
		return reviewsDAO.delByProductId(productId);
	}

	@Override
	public Reviews findById(int id) {
		return reviewsDAO.findById(id);
	}

	public List<Reviews> getList(int offset, int rowCount) {
		return reviewsDAO.getList(offset, rowCount);
	}

	public int totalRow() {
		return reviewsDAO.totalRow();
	}

	public List<Reviews> findByProductId(int productId, int offset, int rowCount) {
		return reviewsDAO.findByProductId(productId, offset, rowCount);
	}

	public int totalRowByProductId(int productId) {
		return reviewsDAO.totalRowByProductId(productId);
	}

	public float getRatingAverageByProductId(int productId) {
		return reviewsDAO.getRatingAverageByProductId(productId);
	}

	public int ratingCount(int rating, int productId) {
		return reviewsDAO.ratingCount(rating, productId);
	}

	// list rating count (1 2 3 4 5 sao) theo sản phẩm
	public List<Integer> listRatingCount(int productId) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 5; i >= 1; i--) {
			list.add(ratingCount(i, productId));
		}
		return list;
	}

	public List<Reviews> search(String productName, int rating, int status, int offset, int rowCount) {
		return reviewsDAO.search(productName, rating, status, offset, rowCount);
	}

	public int totalRowSearch(String productName, int rating, int status) {
		return reviewsDAO.totalRowSearch(productName, rating, status);
	}

}
