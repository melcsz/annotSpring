package com.test.service;

import com.test.exceptions.NotFoundException;
import com.test.model.Phone;

import java.util.List;

public interface PhoneService {

    void savePhone(Phone phone);

    Phone getByID(Integer id) throws NotFoundException;

    List<Phone> getAll();
}
