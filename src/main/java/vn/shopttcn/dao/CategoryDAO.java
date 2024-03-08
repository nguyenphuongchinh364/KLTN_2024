package vn.shopttcn.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vn.shopttcn.constant.GlobalConstant;
import vn.shopttcn.model.Category;

@Repository
public class CategoryDAO extends AbstractDAO<Category> {

	@Override
	public List<Category> getAll() {
		String sql = "SELECT * FROM product_cat WHERE deleteStatus = ? ORDER BY catId DESC";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class), GlobalConstant.DELETE_STATUS_0);
	}

	@Override
	public int save(Category category) {
		String sql = "INSERT INTO product_cat(catName,catSlug,catParentId) VALUES (?,?,?)";
		return jdbcTemplate.update(sql, category.getCatName(), category.getCatSlug(), category.getCatParentId());
	}

	@Override
	public int update(Category category) {
		String sql = "UPDATE product_cat SET catName = ?, catSlug = ?, catParentId = ? WHERE catId = ?";
		return jdbcTemplate.update(sql, category.getCatName(), category.getCatSlug(), category.getCatParentId(),
				category.getCatId());
	}

	// cập nhật trạng thái delete => xoá mềm
	public int updateDeleteStatus(Category category) {
		String sql = "UPDATE product_cat SET deleteStatus = ? WHERE catId = ?";
		return jdbcTemplate.update(sql, category.getDeleteStatus(), category.getCatId());
	}

	@Override
	public int del(int id) {
		String sql = "DELETE FROM product_cat WHERE catId = ?";
		return jdbcTemplate.update(sql, id);
	}

	// lấy danh mục theo id danh mục cha => hiển thị danh mục đa cấp
	public List<Category> findCatByParentId(int parentId, int deleteStatus) {
		String sql = "SELECT * FROM product_cat WHERE catParentId = ? AND deleteStatus = ?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class), parentId, deleteStatus);
	}

	public Category findById(int id, int deleteStatus) {
		try {
			String sql = "SELECT * FROM product_cat WHERE catId = ? AND deleteStatus = ?";
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Category.class), id, deleteStatus);
		} catch (Exception e) {
			System.err.println("Error: findById CategoryDAO");
		}
		return null;
	}

	public List<Category> getList(int offset, int rowCount, int deleteStatus) {
		String sql = "SELECT * FROM product_cat WHERE deleteStatus = ? ORDER BY catId DESC LIMIT ?,?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class), deleteStatus, offset, rowCount);
	}

	public int totalRow(int deleteStatus) {
		String sql = "SELECT COUNT(*) FROM product_cat WHERE deleteStatus = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, deleteStatus);
	}

	// check trùng data
	public Category findByName(String catName) {
		try {
			String sql = "SELECT * FROM product_cat WHERE catName = ?";
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Category.class), catName);
		} catch (Exception e) {
			System.err.println("Error: findByName CategoryDAO");
		}
		return null;
	}

	// search
	public List<Category> search(String catName, int offset, int rowCount, int deleteStatus) {
		String sql = "SELECT * FROM product_cat WHERE catName LIKE ? AND deleteStatus = ? ORDER BY catId DESC LIMIT ?,?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class), "%" + catName + "%", offset,
				rowCount, deleteStatus);
	}

	public int totalRowSearch(String catName, int deleteStatus) {
		String sql = "SELECT COUNT(*) FROM product_cat WHERE catName LIKE ? AND deleteStatus = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, "%" + catName + "%", deleteStatus);
	}

}
