package vn.shopttcn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.shopttcn.dao.RoleDAO;
import vn.shopttcn.model.Role;

@Service
public class RoleService implements CRUDService<Role> {

	@Autowired
	private RoleDAO roleDAO;

	@Override
	public List<Role> getAll() {
		return roleDAO.getAll();
	}

	@Override
	public int save(Role t) {
		return 0;
	}

	@Override
	public int update(Role t) {
		return 0;
	}

	@Override
	public int del(int id) {
		return 0;
	}

	@Override
	public Role findById(int id) {
		return null;
	}

	public List<Role> getRoleNotAdmin() {
		return roleDAO.getRoleNotAdmin();
	}

}
