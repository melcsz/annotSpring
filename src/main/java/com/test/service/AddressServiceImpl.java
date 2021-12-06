package com.test.service;

import com.test.exceptions.NotFoundException;
import com.test.model.Address;
import com.test.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    @Transactional()
    @Override
    public void save(Address address) {
        addressRepository.save(address);
    }

    @Override
    public Address getByID(Integer id) throws NotFoundException{
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if(!optionalAddress.isPresent()) throw new NotFoundException("address not found");
        return optionalAddress.get();
    }

    @Override
    public void deleteByID(Integer id) {
        addressRepository.deleteById(id);
    }

    @Override
    public void update(String buildingNumber,String city,String street,int id) {
        addressRepository.update(buildingNumber,city,street,id);
    }
}
