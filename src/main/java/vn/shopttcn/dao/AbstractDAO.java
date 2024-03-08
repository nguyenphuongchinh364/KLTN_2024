package vn.shopttcn.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class AbstractDAO<T> {

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	public List<T> getAll() {
		return null;
	}

	public int save(T t) {
		return 0;
	}

	public int update(T t) {
		return 0;
	}

	public int del(int id) {
		return 0;
	}

	public T findById(int id) {
		return null;
	}

}
