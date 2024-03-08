package vn.shopttcn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.shopttcn.dao.ProductPictureDAO;
import vn.shopttcn.model.ProductPicture;

@Service
public class ProductPictureService implements CRUDService<ProductPicture> {

	@Autowired
	private ProductPictureDAO productPictureDAO;

	@Override
	public List<ProductPicture> getAll() {
		return null;
	}

	@Override
	public int save(ProductPicture picture) {
		return productPictureDAO.save(picture);
	}

	@Override
	public int update(ProductPicture picture) {
		return productPictureDAO.update(picture);
	}

	@Override
	public int del(int id) {
		return productPictureDAO.del(id);
	}

	@Override
	public ProductPicture findById(int id) {
		return productPictureDAO.findById(id);
	}

	public int rowCountByProductId(int productId) {
		return productPictureDAO.rowCountByProductId(productId);
	}

	public int delByProductId(int productId) {
		return productPictureDAO.delByProductId(productId);
	}

	public List<ProductPicture> findByProductId(int productId) {
		return productPictureDAO.findByProductId(productId);
	}

}
