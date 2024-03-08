package vn.shopttcn.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import vn.shopttcn.model.Contact;

@Repository
public class ContactDAO extends AbstractDAO<Contact> {

	@Override
	public List<Contact> getAll() {
		String sql = "SELECT * FROM contact ORDER BY contactId DESC";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Contact.class));
	}

	@Override
	public int save(Contact contact) {
		String sql = "INSERT INTO contact(contactName,contactEmail,contactPhone,contactMessage) VALUES (?,?,?,?)";
		return jdbcTemplate.update(sql, contact.getContactName(), contact.getContactEmail(), contact.getContactPhone(),
				contact.getContactMessage());
	}

	@Override
	public int del(int id) {
		String sql = "DELETE FROM contact WHERE contactId = ?";
		return jdbcTemplate.update(sql, id);
	}

	public List<Contact> getList(int offset, int rowCount, int deleteStatus) {
		String sql = "SELECT * FROM contact WHERE deleteStatus = ? ORDER BY contactId DESC LIMIT ?,?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Contact.class), deleteStatus, offset, rowCount);
	}

	public int totalRow(int deleteStatus) {
		String sql = "SELECT COUNT(*) FROM contact WHERE deleteStatus = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, deleteStatus);
	}

	public List<Contact> search(String content, int offset, int rowCount, int deleteStatus) {
		String sql = "SELECT * FROM contact WHERE contactName LIKE ? AND deleteStatus = ? ORDER BY contactId DESC LIMIT ?,?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Contact.class), "%" + content + "%", deleteStatus,
				offset, rowCount);
	}

	public int totalRowSearch(String content, int deleteStatus) {
		String sql = "SELECT COUNT(*) FROM contact WHERE contactName LIKE ? AND deleteStatus = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, "%" + content + "%", deleteStatus);
	}

}
