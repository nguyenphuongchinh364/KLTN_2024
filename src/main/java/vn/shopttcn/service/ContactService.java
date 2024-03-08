package vn.shopttcn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.shopttcn.dao.ContactDAO;
import vn.shopttcn.model.Contact;

@Service
public class ContactService implements CRUDService<Contact> {

	@Autowired
	private ContactDAO contactDAO;

	@Override
	public List<Contact> getAll() {
		return contactDAO.getAll();
	}

	@Override
	public int save(Contact contact) {
		return contactDAO.save(contact);
	}

	@Override
	public int update(Contact contact) {
		return 0;
	}

	@Override
	public int del(int id) {
		return contactDAO.del(id);
	}

	@Override
	public Contact findById(int id) {
		return null;
	}

	public List<Contact> getList(int offset, int rowCount, int deleteStatus) {
		return contactDAO.getList(offset, rowCount, deleteStatus);
	}

	public int totalRow(int deleteStatus) {
		return contactDAO.totalRow(deleteStatus);
	}

	public List<Contact> search(String content, int offset, int rowCount, int deleteStatus) {
		return contactDAO.search(content, offset, rowCount, deleteStatus);
	}

	public int totalRowSearch(String content, int deleteStatus) {
		return contactDAO.totalRowSearch(content, deleteStatus);
	}

}
