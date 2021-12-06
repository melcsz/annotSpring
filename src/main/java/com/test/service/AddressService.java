package com.test.service;

import com.test.exceptions.NotFoundException;
import com.test.model.Address;

import java.util.List;

public interface AddressService {
    List<Address> getAll();
    void save(Address address);
    Address getByID(Integer id) throws NotFoundException;
    void deleteByID(Integer id);
    void update(String buildingNumber,String city,String street,int id);

}
