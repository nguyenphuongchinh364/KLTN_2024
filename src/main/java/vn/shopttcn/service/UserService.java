package vn.shopttcn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.shopttcn.dao.UserDAO;
import vn.shopttcn.model.User;

@Service
public class UserService implements CRUDService<User> {

	@Autowired
	private UserDAO userDAO;

	@Override
	public List<User> getAll() {
		return null;
	}

	@Override
	public int save(User user) {
		return userDAO.save(user);
	}

	@Override
	public int update(User user) {
		return userDAO.update(user);
	}

	public int updateStatus(User user) {
		return userDAO.updateStatus(user);
	}

	public int updatePassword(User user) {
		return userDAO.updatePassword(user);
	}

	@Override
	public int del(int id) {
		return userDAO.del(id);
	}

	@Override
	public User findById(int id) {
		return userDAO.findById(id);
	}

	public List<User> getList(int offset, int rowCount) {
		return userDAO.getList(offset, rowCount);
	}

	public int totalRow() {
		return userDAO.totalRow();
	}

	public User findByUsername(String username) {
		return userDAO.findByUsername(username);
	}

	public User findUserDuplicate(User user, User oldUser, String properties) {
		return userDAO.findUserDuplicate(user, oldUser, properties);
	}

	public List<User> findUserByRole(int roleId) {
		return userDAO.findUserByRole(roleId);
	}

	public List<User> search(String username, int enabled, int roleId, int offset, int rowCount) {
		return userDAO.search(username, enabled, roleId, offset, rowCount);
	}

	public int totalRowSearch(String username, int enabled, int roleId) {
		return userDAO.totalRowSearch(username, enabled, roleId);
	}

}
