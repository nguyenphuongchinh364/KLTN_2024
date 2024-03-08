package vn.shopttcn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.shopttcn.dao.AddressDAO;
import vn.shopttcn.model.Address;

@Service
public class AddressService implements CRUDService<Address> {

	@Autowired
	private AddressDAO addressDAO;

	@Override
	public List<Address> getAll() {
		return null;
	}

	@Override
	public int save(Address address) {
		return addressDAO.save(address);
	}

	@Override
	public int update(Address address) {
		return addressDAO.update(address);
	}

	@Override
	public int del(int id) {
		return addressDAO.del(id);
	}

	@Override
	public Address findById(int id) {
		return null;
	}

	public Address getNewAddress() {
		return addressDAO.getNewAddress();
	}

}
