package com.test.service;

import com.test.exceptions.BadRequest;
import com.test.exceptions.NotFoundException;
import com.test.model.Status;
import com.test.model.User;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {
    List<User> getAll();

    void saveUser(User user) throws NotFoundException;

    User getByID(Integer id) throws NotFoundException;

    void deleteByID(Integer id);

    void updateUser(String email, String name, String password, int id);

    User findByEmail(String email) throws NotFoundException;

    void logIn(User user) throws BadRequest;

    void verify(String email) throws NotFoundException;

    void sendEmail(String email);

}
