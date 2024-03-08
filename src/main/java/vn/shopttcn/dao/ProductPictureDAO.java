package vn.shopttcn.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vn.shopttcn.model.ProductPicture;

@Repository
public class ProductPictureDAO extends AbstractDAO<ProductPicture> {

	@Override
	public int save(ProductPicture picture) {
		String sql = "INSERT INTO product_picture(productId,pictureName) VALUES (?,?)";
		return jdbcTemplate.update(sql, picture.getProductId(), picture.getPictureName());
	}

	@Override
	public int update(ProductPicture picture) {
		String sql = "UPDATE product_picture SET pictureName = ? WHERE pictureId = ?";
		return jdbcTemplate.update(sql, picture.getPictureName(), picture.getPictureId());
	}

	@Override
	public int del(int id) {
		String sql = "DELETE FROM product_picture WHERE pictureId = ?";
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public ProductPicture findById(int id) {
		try {
			String sql = "SELECT * FROM product_picture WHERE pictureId = ?";
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ProductPicture.class), id);
		} catch (Exception e) {
			System.err.println("Error: findById ProductPictureDAO");
		}
		return null;
	}

	// Đếm số lượng hình ảnh của 1 product
	public int rowCountByProductId(int productId) {
		String sql = "SELECT COUNT(*) FROM product_picture WHERE productId = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, productId);
	}

	public int delByProductId(int productId) {
		String sql = "DELETE FROM product_picture WHERE productId = ?";
		return jdbcTemplate.update(sql, productId);
	}

	public List<ProductPicture> findByProductId(int productId) {
		String sql = "SELECT * FROM product_picture WHERE productId = ? ORDER BY pictureId DESC";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProductPicture.class), productId);
	}

}
