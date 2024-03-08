package vn.shopttcn.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import vn.shopttcn.constant.GlobalConstant;
import vn.shopttcn.model.Category;
import vn.shopttcn.model.Product;

@Repository
public class ProductDAO extends AbstractDAO<Product> {

	// Lấy dữ liệu có phân trang
	public List<Product> getList(int offset, int rowCount, int deleteStatus) {
		String sql = "SELECT * FROM product p INNER JOIN product_cat c ON p.catId = c.catId WHERE p.deleteStatus = ? ORDER BY productId DESC LIMIT ?,?";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Product>>() {
			List<Product> list = new ArrayList<Product>();

			@Override
			public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new Product(rs.getInt("productId"),
							new Category(rs.getInt("c.catId"), rs.getString("catName"), rs.getString("catSlug"),
									rs.getInt("catParentId"), rs.getTimestamp("c.createAt"),
									rs.getTimestamp("c.updateAt")),
							rs.getString("productName"), rs.getString("productSlug"), rs.getString("productImage"),
							rs.getString("productDesc"), rs.getString("productDetail"), rs.getInt("productPrice"),
							rs.getInt("productQuantity"), rs.getInt("productSold"), rs.getInt("productView"),
							rs.getInt("p.deleteStatus"), rs.getTimestamp("p.createAt"), rs.getTimestamp("p.updateAt")));
				}
				return list;
			}
		}, deleteStatus, offset, rowCount);
	}

	// tổng record trong db
	public int totalRow(int deleteStatus) {
		String sql = "SELECT COUNT(*) FROM product p INNER JOIN product_cat c ON p.catId = c.catId WHERE p.deleteStatus = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, deleteStatus);
	}

	// lấy dữ liệu theo danh sách danh mục sản phẩm (đa cấp)
	public List<Product> findByCatId(List<Integer> catIdList, int offset, int rowCount, int deleteStatus) {
		List<Object> list = new ArrayList<Object>();
		String sql = "SELECT * FROM product p INNER JOIN product_cat c ON p.catId = c.catId WHERE p.deleteStatus = ? AND (0";
		list.add(deleteStatus);
		if (catIdList.size() > 0) {
			for (Integer catId : catIdList) {
				sql += " OR p.catId = ?";
				list.add(catId);
			}
		}
		sql += ") ORDER BY productId DESC LIMIT ?,?";
		list.add(offset);
		list.add(rowCount);
		Object[] arrObj = new Object[list.size()];
		list.toArray(arrObj);
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Product>>() {
			List<Product> list = new ArrayList<Product>();

			@Override
			public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new Product(rs.getInt("productId"),
							new Category(rs.getInt("c.catId"), rs.getString("catName"), rs.getString("catSlug"),
									rs.getInt("catParentId"), rs.getTimestamp("c.createAt"),
									rs.getTimestamp("c.updateAt")),
							rs.getString("productName"), rs.getString("productSlug"), rs.getString("productImage"),
							rs.getString("productDesc"), rs.getString("productDetail"), rs.getInt("productPrice"),
							rs.getInt("productQuantity"), rs.getInt("productSold"), rs.getInt("productView"),
							rs.getInt("p.deleteStatus"), rs.getTimestamp("p.createAt"), rs.getTimestamp("p.updateAt")));
				}
				return list;
			}
		}, arrObj);
	}

	public int totalRowByCatId(List<Integer> catIdList, int deleteStatus) {
		List<Object> list = new ArrayList<Object>();
		String sql = "SELECT COUNT(*) FROM product p INNER JOIN product_cat c ON p.catId = c.catId WHERE p.deleteStatus = ? AND (0";
		list.add(deleteStatus);
		if (catIdList.size() > 0) {
			for (Integer catId : catIdList) {
				sql += " OR p.catId = ?";
				list.add(catId);
			}
		}
		sql += ")";
		Object[] arrObj = new Object[list.size()];
		list.toArray(arrObj);
		return jdbcTemplate.queryForObject(sql, Integer.class, arrObj);
	}

	public Product findById(int id, int deleteStatus) {
		try {
			String sql = "SELECT * FROM product p INNER JOIN product_cat c ON p.catId = c.catId WHERE productId = ? AND p.deleteStatus = ?";
			return jdbcTemplate.query(sql, new ResultSetExtractor<Product>() {

				@Override
				public Product extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return new Product(rs.getInt("productId"),
								new Category(rs.getInt("c.catId"), rs.getString("catName"), rs.getString("catSlug"),
										rs.getInt("catParentId"), rs.getTimestamp("c.createAt"),
										rs.getTimestamp("c.updateAt")),
								rs.getString("productName"), rs.getString("productSlug"), rs.getString("productImage"),
								rs.getString("productDesc"), rs.getString("productDetail"), rs.getInt("productPrice"),
								rs.getInt("productQuantity"), rs.getInt("productSold"), rs.getInt("productView"),
								rs.getInt("p.deleteStatus"), rs.getTimestamp("p.createAt"),
								rs.getTimestamp("p.updateAt"));
					}
					return null;
				}
			}, id, deleteStatus);
		} catch (Exception e) {
			System.err.println("Error: findById ProductDAO");
		}
		return null;
	}

	@Override
	public int save(Product product) {
		String sql = "INSERT INTO product(catId,productName,productSlug,productImage,productDesc,productDetail,productPrice,productQuantity) VALUES (?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, product.getCat().getCatId(), product.getProductName(), product.getProductSlug(),
				product.getProductImage(), product.getProductDesc(), product.getProductDetail(),
				product.getProductPrice(), product.getProductQuantity());
	}

	@Override
	public int update(Product product) {
		String sql = "UPDATE product SET catId = ?, productName = ?, productSlug = ?, productDesc = ?, productDetail = ?, productPrice = ?, productQuantity = ? WHERE productId = ?";
		return jdbcTemplate.update(sql, product.getCat().getCatId(), product.getProductName(), product.getProductSlug(),
				product.getProductDesc(), product.getProductDetail(), product.getProductPrice(),
				product.getProductQuantity(), product.getProductId());
	}

	public int updateView(Product product) {
		String sql = "UPDATE product SET productView = productView + 1 WHERE productId = ?";
		return jdbcTemplate.update(sql, product.getProductId());
	}

	public int updateQuantity(Product product) {
		String sql = "UPDATE product SET productQuantity = ? WHERE productId = ?";
		return jdbcTemplate.update(sql, product.getProductQuantity(), product.getProductId());
	}

	public int updateSold(Product product) {
		String sql = "UPDATE product SET productSold = ? WHERE productId = ?";
		return jdbcTemplate.update(sql, product.getProductSold(), product.getProductId());
	}

	public int updateImage(Product product) {
		String sql = "UPDATE product SET productImage = ? WHERE productId = ?";
		return jdbcTemplate.update(sql, product.getProductImage(), product.getProductId());
	}

	// cập nhật trạng thái delete => xoá mềm
	public int updateDeleteStatus(Product product) {
		String sql = "UPDATE product SET deleteStatus = ? WHERE productId = ?";
		return jdbcTemplate.update(sql, product.getDeleteStatus(), product.getProductId());
	}

	@Override
	public int del(int id) {
		String sql = "DELETE FROM product WHERE productId = ?";
		return jdbcTemplate.update(sql, id);
	}

	// findByName => check data trùng
	public Product findByName(String productName) {
		try {
			String sql = "SELECT * FROM product p INNER JOIN product_cat c ON p.catId = c.catId WHERE productName = ?";
			return jdbcTemplate.query(sql, new ResultSetExtractor<Product>() {

				@Override
				public Product extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return new Product(rs.getInt("productId"),
								new Category(rs.getInt("c.catId"), rs.getString("catName"), rs.getString("catSlug"),
										rs.getInt("catParentId"), rs.getTimestamp("c.createAt"),
										rs.getTimestamp("c.updateAt")),
								rs.getString("productName"), rs.getString("productSlug"), rs.getString("productImage"),
								rs.getString("productDesc"), rs.getString("productDetail"), rs.getInt("productPrice"),
								rs.getInt("productQuantity"), rs.getInt("productSold"), rs.getInt("productView"),
								rs.getInt("p.deleteStatus"), rs.getTimestamp("p.createAt"),
								rs.getTimestamp("p.updateAt"));
					}
					return null;
				}
			}, productName);
		} catch (Exception e) {
			System.err.println("Error: findByName ProductDAO");
		}
		return null;
	}

	// select product mới nhất
	public Product getNewProduct() {
		try {
			String sql = "SELECT * FROM product p INNER JOIN product_cat c ON p.catId = c.catId ORDER BY productId DESC LIMIT 1";
			return jdbcTemplate.query(sql, new ResultSetExtractor<Product>() {

				@Override
				public Product extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return new Product(rs.getInt("productId"),
								new Category(rs.getInt("c.catId"), rs.getString("catName"), rs.getString("catSlug"),
										rs.getInt("catParentId"), rs.getTimestamp("c.createAt"),
										rs.getTimestamp("c.updateAt")),
								rs.getString("productName"), rs.getString("productSlug"), rs.getString("productImage"),
								rs.getString("productDesc"), rs.getString("productDetail"), rs.getInt("productPrice"),
								rs.getInt("productQuantity"), rs.getInt("productSold"), rs.getInt("productView"),
								rs.getInt("p.deleteStatus"), rs.getTimestamp("p.createAt"),
								rs.getTimestamp("p.updateAt"));
					}
					return null;
				}
			});
		} catch (Exception e) {
			System.err.println("Error: getNewProduct ProductDAO");
		}
		return null;
	}

	// đã bán nhiều nhất
	public List<Product> getBestSellProduct(int deleteStatus) {
		String sql = "SELECT * FROM product p INNER JOIN product_cat c ON p.catId = c.catId WHERE p.deleteStatus = ? ORDER BY productSold DESC LIMIT 8";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Product>>() {
			List<Product> list = new ArrayList<Product>();

			@Override
			public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new Product(rs.getInt("productId"),
							new Category(rs.getInt("c.catId"), rs.getString("catName"), rs.getString("catSlug"),
									rs.getInt("catParentId"), rs.getTimestamp("c.createAt"),
									rs.getTimestamp("c.updateAt")),
							rs.getString("productName"), rs.getString("productSlug"), rs.getString("productImage"),
							rs.getString("productDesc"), rs.getString("productDetail"), rs.getInt("productPrice"),
							rs.getInt("productQuantity"), rs.getInt("productSold"), rs.getInt("productView"),
							rs.getInt("p.deleteStatus"), rs.getTimestamp("p.createAt"), rs.getTimestamp("p.updateAt")));
				}
				return list;
			}
		}, deleteStatus);
	}

	// product relate (cùng category)
	public List<Product> getProductRelate(List<Integer> catIdList, int productId, int deleteStatus) {
		List<Object> list = new ArrayList<Object>();
		list.add(deleteStatus);
		String sql = "SELECT * FROM product p INNER JOIN product_cat c ON p.catId = c.catId WHERE p.deleteStatus = ? AND (0";
		if (catIdList.size() > 0) {
			for (Integer catId : catIdList) {
				sql += " OR p.catId = ?";
				list.add(catId);
			}
		}
		sql += " ) AND p.productId != ? ORDER BY RAND() LIMIT 4";
		list.add(productId);
		Object[] arrObj = new Object[list.size()];
		list.toArray(arrObj);
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Product>>() {
			List<Product> list = new ArrayList<Product>();

			@Override
			public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new Product(rs.getInt("productId"),
							new Category(rs.getInt("c.catId"), rs.getString("catName"), rs.getString("catSlug"),
									rs.getInt("catParentId"), rs.getTimestamp("c.createAt"),
									rs.getTimestamp("c.updateAt")),
							rs.getString("productName"), rs.getString("productSlug"), rs.getString("productImage"),
							rs.getString("productDesc"), rs.getString("productDetail"), rs.getInt("productPrice"),
							rs.getInt("productQuantity"), rs.getInt("productSold"), rs.getInt("productView"),
							rs.getInt("p.deleteStatus"), rs.getTimestamp("p.createAt"), rs.getTimestamp("p.updateAt")));
				}
				return list;
			}
		}, arrObj);
	}

	// search
	public List<Product> search(String productName, List<Integer> listCatId, int offset, int rowCount,
			int deleteStatus) {
		List<Object> list = new ArrayList<Object>();
		String sql = "SELECT * FROM product p INNER JOIN product_cat c ON p.catId = c.catId WHERE p.deleteStatus = ?";
		list.add(deleteStatus);
		if (!productName.equals(GlobalConstant.EMPTY)) {
			sql += " AND productName LIKE ?";
			list.add("%" + productName + "%");
		}
		if (listCatId != null) {
			sql += " AND (0";
			for (int catId : listCatId) {
				sql += " OR p.catId = ?";
				list.add(catId);
			}
			sql += ")";
		}
		sql += " ORDER BY productId DESC LIMIT ?,?";
		list.add(offset);
		list.add(rowCount);
		Object[] arrObj = new Object[list.size()];
		arrObj = list.toArray();
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Product>>() {
			List<Product> list = new ArrayList<Product>();

			@Override
			public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new Product(rs.getInt("productId"),
							new Category(rs.getInt("c.catId"), rs.getString("catName"), rs.getString("catSlug"),
									rs.getInt("catParentId"), rs.getTimestamp("c.createAt"),
									rs.getTimestamp("c.updateAt")),
							rs.getString("productName"), rs.getString("productSlug"), rs.getString("productImage"),
							rs.getString("productDesc"), rs.getString("productDetail"), rs.getInt("productPrice"),
							rs.getInt("productQuantity"), rs.getInt("productSold"), rs.getInt("productView"),
							rs.getInt("p.deleteStatus"), rs.getTimestamp("p.createAt"), rs.getTimestamp("p.updateAt")));
				}
				return list;
			}
		}, arrObj);
	}

	public int totalRowSearch(String productName, List<Integer> listCatId, int deleteStatus) {
		List<Object> list = new ArrayList<Object>();
		String sql = "SELECT COUNT(*) FROM product p INNER JOIN product_cat c ON p.catId = c.catId WHERE p.deleteStatus = ?";
		list.add(deleteStatus);
		if (!productName.equals(GlobalConstant.EMPTY)) {
			sql += " AND productName LIKE ?";
			list.add("%" + productName + "%");
		}
		if (listCatId != null) {
			sql += " AND (0";
			for (int catId : listCatId) {
				sql += " OR p.catId = ?";
				list.add(catId);
			}
			sql += ")";
		}
		Object[] arrObj = new Object[list.size()];
		arrObj = list.toArray();
		return jdbcTemplate.queryForObject(sql, Integer.class, arrObj);
	}

	// lọc sản phẩm (public)
	public List<Product> filter(String productName, List<Integer> listCatId, int minPrice, int maxPrice) {
		List<Object> list = new ArrayList<Object>();
		String sql = "SELECT * FROM product p INNER JOIN product_cat c ON p.catId = c.catId WHERE p.deleteStatus = ?";
		list.add(GlobalConstant.DELETE_STATUS_0);
		if (!productName.equals(GlobalConstant.EMPTY)) {
			sql += " AND productName LIKE ?";
			list.add("%" + productName + "%");
		}
		if (listCatId.size() > 0) {
			sql += " AND (0";
			for (int catId : listCatId) {
				sql += " OR p.catId = ?";
				list.add(catId);
			}
			sql += ")";
		}
		if (minPrice != 0 && maxPrice != 0) {
			sql += " AND (productPrice BETWEEN ? AND ?)";
			list.add(minPrice);
			list.add(maxPrice);
		} else {
			if (minPrice != 0) {
				sql += " AND productPrice >= ?";
				list.add(minPrice);
			}
			if (maxPrice != 0) {
				sql += " AND productPrice <= ?";
				list.add(maxPrice);
			}
		}
		sql += " ORDER BY productPrice ASC";
		Object[] arrObj = new Object[list.size()];
		arrObj = list.toArray();
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Product>>() {
			List<Product> list = new ArrayList<Product>();

			@Override
			public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new Product(rs.getInt("productId"),
							new Category(rs.getInt("c.catId"), rs.getString("catName"), rs.getString("catSlug"),
									rs.getInt("catParentId"), rs.getTimestamp("c.createAt"),
									rs.getTimestamp("c.updateAt")),
							rs.getString("productName"), rs.getString("productSlug"), rs.getString("productImage"),
							rs.getString("productDesc"), rs.getString("productDetail"), rs.getInt("productPrice"),
							rs.getInt("productQuantity"), rs.getInt("productSold"), rs.getInt("productView"),
							rs.getInt("p.deleteStatus"), rs.getTimestamp("p.createAt"), rs.getTimestamp("p.updateAt")));
				}
				return list;
			}
		}, arrObj);
	}

}
