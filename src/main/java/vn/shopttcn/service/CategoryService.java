package vn.shopttcn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.shopttcn.dao.CategoryDAO;
import vn.shopttcn.model.Category;

@Service
public class CategoryService implements CRUDService<Category> {

	@Autowired
	private CategoryDAO categoryDAO;

	@Override
	public List<Category> getAll() {
		return categoryDAO.getAll();
	}

	@Override
	public int save(Category category) {
		return categoryDAO.save(category);
	}

	@Override
	public int update(Category category) {
		return categoryDAO.update(category);
	}

	public int updateDeleteStatus(Category category) {
		return categoryDAO.updateDeleteStatus(category);
	}

	@Override
	public int del(int id) {
		return categoryDAO.del(id);
	}

	@Override
	public Category findById(int id) {
		return null;
	}

	public List<Category> findCatByParentId(int parentId, int deleteStatus) {
		return categoryDAO.findCatByParentId(parentId, deleteStatus);
	}

	public Category findById(int id, int deleteStatus) {
		return categoryDAO.findById(id, deleteStatus);
	}

	public List<Category> getList(int offset, int rowCount, int deleteStatus) {
		return categoryDAO.getList(offset, rowCount, deleteStatus);
	}

	public int totalRow(int deleteStatus) {
		return categoryDAO.totalRow(deleteStatus);
	}

	public Category findByName(String catName) {
		return categoryDAO.findByName(catName);
	}

	public List<Category> search(String catName, int offset, int rowCount, int deleteStatus) {
		return categoryDAO.search(catName, offset, rowCount, deleteStatus);
	}

	public int totalRowSearch(String catName, int deleteStatus) {
		return categoryDAO.totalRowSearch(catName, deleteStatus);
	}

}
