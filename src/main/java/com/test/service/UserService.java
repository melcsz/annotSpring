package com.test.service;

import com.test.exceptions.BadRequest;
import com.test.exceptions.NotFoundException;
import com.test.model.Status;
import com.test.model.User;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();

    void saveUser(User user) throws NotFoundException;

    User getByID(Integer id) throws NotFoundException;

    void deleteByID(Integer id);

    void updateUser(String email, String name, String password, int id);

    User findByEmail(String email) throws NotFoundException;

    void logIn(User user) throws BadRequest;

    void verify(String email) throws NotFoundException;

    void sendEmail(String email,String token);

    Optional<User> findUserByResetToken(String resetToken);

    void updateResetPasswordToken(String token, String email) throws NotFoundException, BadRequest;

    User getByResetPasswordToken(String token);

    void updatePassword(User user, String newPassword,String token) throws Exception;

}
