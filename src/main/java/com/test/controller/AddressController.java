package com.test.controller;

import com.test.exceptions.NotFoundException;
import com.test.model.Address;
import com.test.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    List<Address> getAll(){
        return addressService.getAll();
    }

    @GetMapping("/{id}")
    Address getByID(@PathVariable int id) throws NotFoundException {
        return addressService.getByID(id);
    }

    @DeleteMapping("/{id}")
    void deleteByID(@PathVariable int id){
        addressService.deleteByID(id);
    }

    @Transactional
    @PutMapping("/{buildingNumber}/{city}/{street}/{id}")
    public void updateUser(@PathVariable String buildingNumber,@PathVariable String city,@PathVariable String street,@PathVariable int id){
        addressService.update(buildingNumber,city,street,id);
    }

    @PostMapping
    public void create(@RequestBody Address address){
        addressService.save(address);
    }

    @PutMapping("/{buildingNumber}/{city}/{street}")
    public void save(@PathVariable String buildingNumber,@PathVariable String city,@PathVariable String street){
        Address address = new Address(buildingNumber,city,street);
        addressService.save(address);
    }
}
