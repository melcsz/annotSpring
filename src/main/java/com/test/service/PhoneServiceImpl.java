package com.test.service;

import com.test.exceptions.NotFoundException;
import com.test.model.Phone;
import com.test.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneServiceImpl implements PhoneService{

    @Autowired
    private PhoneRepository phoneRepository;

    @Override
    public void savePhone(Phone phone) {
        phoneRepository.save(phone);
    }

    @Override
    public Phone getByID(Integer id) throws NotFoundException {
        Optional<Phone> optionalPhone = phoneRepository.findById(id);
        if(!optionalPhone.isPresent()) throw new NotFoundException("address not found");
        return optionalPhone.get();
    }

    @Override
    public List<Phone> getAll() {
        return phoneRepository.findAll();
    }
}
