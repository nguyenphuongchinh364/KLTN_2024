package vn.shopttcn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.shopttcn.dao.ProductDAO;
import vn.shopttcn.model.Product;

@Service
public class ProductService implements CRUDService<Product> {

	@Autowired
	private ProductDAO productDAO;

	@Override
	public List<Product> getAll() {
		return null;
	}

	@Override
	public int save(Product product) {
		return productDAO.save(product);
	}

	@Override
	public int update(Product product) {
		return productDAO.update(product);
	}

	public int updateView(Product product) {
		return productDAO.updateView(product);
	}

	public int updateQuantity(Product product) {
		return productDAO.updateQuantity(product);
	}

	public int updateSold(Product product) {
		return productDAO.updateSold(product);
	}

	public int updateImage(Product product) {
		return productDAO.updateImage(product);
	}

	public int updateDeleteStatus(Product product) {
		return productDAO.updateDeleteStatus(product);
	}

	@Override
	public int del(int id) {
		return productDAO.del(id);
	}

	@Override
	public Product findById(int id) {
		return null;
	}

	public List<Product> getList(int offset, int rowCount, int deleteStatus) {
		return productDAO.getList(offset, rowCount, deleteStatus);
	}

	public int totalRow(int deleteStatus) {
		return productDAO.totalRow(deleteStatus);
	}

	public List<Product> findByCatId(List<Integer> catIdList, int offset, int rowCount, int deleteStatus) {
		return productDAO.findByCatId(catIdList, offset, rowCount, deleteStatus);
	}

	public int totalRowByCatId(List<Integer> catIdList, int deleteStatus) {
		return productDAO.totalRowByCatId(catIdList, deleteStatus);
	}

	public Product findById(int id, int deleteStatus) {
		return productDAO.findById(id, deleteStatus);
	}

	public Product findByName(String productName) {
		return productDAO.findByName(productName);
	}

	public Product getNewProduct() {
		return productDAO.getNewProduct();
	}

	public List<Product> getBestSellProduct(int deleteStatus) {
		return productDAO.getBestSellProduct(deleteStatus);
	}
	
	public List<Product> getProductRS( int deleteStatus, ArrayList<String> itemsId) {
		return productDAO.getProductRS(deleteStatus, itemsId);
		
	}

	public List<Product> getProductRelate(List<Integer> catIdList, int productId, int deleteStatus) {
		return productDAO.getProductRelate(catIdList, productId, deleteStatus);
	}

	public List<Product> search(String productName, List<Integer> listCatId, int offset, int rowCount,
			int deleteStatus) {
		return productDAO.search(productName, listCatId, offset, rowCount, deleteStatus);
	}

	public int totalRowSearch(String productName, List<Integer> listCatId, int deleteStatus) {
		return productDAO.totalRowSearch(productName, listCatId, deleteStatus);
	}

	public List<Product> filter(String keyword, List<Integer> listCatId, int minPrice, int maxPrice) {
		return productDAO.filter(keyword, listCatId, minPrice, maxPrice);
	}

	

}
