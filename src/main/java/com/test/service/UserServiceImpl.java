package com.test.service;

import com.test.exceptions.BadRequest;
import com.test.exceptions.NotFoundException;
import com.test.model.Status;
import com.test.model.User;
import com.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.List;
import java.util.Optional;


@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) throws NotFoundException {
        String encodedPw = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPw);
        user.setStatus(Status.UNVERIFIED);
        userRepository.save(user);
        String link = "http://localhost:8080/user/verify?email="+user.getEmail();
        mailSender.sendSimpleMessage(user.getEmail(),
                "Verification",
                link);
    }

    @Transactional
    public void updateResetPasswordToken(String token, String email) throws NotFoundException, BadRequest {
        User user = userRepository.getByEmail(email);
        if (user != null) {
            long crDate = System.currentTimeMillis();
            user.setResetPasswordToken(token);
            user.setReservePasswordTokenCreationDate(crDate);
            userRepository.save(user);
        } else {
            throw new NotFoundException("Could not find any customer with the email " + email);
        }
    }

    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword, String token) throws Exception {
        if(userRepository.findByResetPasswordToken(token).equals(user)){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        if(System.currentTimeMillis() - user.getReservePasswordTokenCreationDate() > 12000){
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);}}
        else throw new Exception("token has expired");
    }

    @Override
    public User getByID(Integer id) throws NotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new NotFoundException("user not found :(");
        }
        return optionalUser.get();
    }

    @Override
    public void deleteByID(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(String email, String name, String password, int id) {
        userRepository.update(email, name, password, id);
    }

    @Override
    public User findByEmail(String email) throws NotFoundException {
        if (userRepository.getByEmail(email) == null) throw new NotFoundException("blin");
        else return userRepository.getByEmail(email);
    }

    public List<User> getByName(String email) {
        return userRepository.getAllByName(email);
    }

    public void logIn(User user) throws BadRequest {
        String encodedPw = passwordEncoder.encode(user.getPassword());
        if (userRepository.getByPasswordAndEmail(encodedPw, user.getEmail()) != null) {
            User user1 = userRepository.getByPasswordAndEmail(encodedPw, user.getEmail());
            if (user1.getStatus() == Status.UNVERIFIED) throw new BadRequest("BLABLA");
            else System.out.println("nice");

        } else System.out.println("no user found");
    }

    @Override
    public void verify(String email) throws NotFoundException {
        userRepository.getByEmail(email).setStatus(Status.VERIFIED);

    }

    @Override
    public void sendEmail(String email, String token) {
        mailSender.sendSimpleMessage(email, "RESET", token);
    }

    @Override
    public Optional<User> findUserByResetToken(String resetToken) {
        return Optional.empty();
    }
}