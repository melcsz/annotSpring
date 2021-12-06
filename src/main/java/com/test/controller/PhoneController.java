package com.test.controller;

import com.test.exceptions.NotFoundException;
import com.test.model.Phone;
import com.test.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phone")
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    @GetMapping
    List<Phone> getAll(){
        return phoneService.getAll();
    }

    @GetMapping("/{id}")
    Phone getByID(@PathVariable int id) throws NotFoundException {
        return phoneService.getByID(id);
    }

    @PostMapping
    public void create(@RequestBody Phone phone) {
        phoneService.savePhone(phone);
    }

    @PutMapping("/{model}")
    public void save(@PathVariable String model){
        Phone phone = new Phone(model);
        phoneService.savePhone(phone);
    }
}
