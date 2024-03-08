package vn.shopttcn.service;

import java.util.List;

public interface CRUDService<T> {

	List<T> getAll();

	int save(T t);

	int update(T t);

	int del(int id);

	T findById(int id);

}
