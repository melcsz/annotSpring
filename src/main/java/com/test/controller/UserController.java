package com.test.controller;

import com.test.exceptions.BadRequest;
import com.test.exceptions.NotFoundException;
import com.test.model.*;
import com.test.service.AddressService;
import com.test.service.PhoneService;
import com.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private PhoneService phoneService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    List<User> getAll(){
        return userService.getAll();
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{id}")
    User getByID(@PathVariable int id) throws NotFoundException {
        return userService.getByID(id);
    }

    @GetMapping(path = "/getByEmail")
    User getByEmail(@RequestParam String email) throws NotFoundException {
        return userService.findByEmail(email);
    }

   @DeleteMapping("/{id}")
    void deleteByID(@PathVariable int id){
        userService.deleteByID(id);
   }

    @Transactional
    @PutMapping("/{email}/{name}/{password}/{id}")
    public void updateUser(@PathVariable String email,@PathVariable String name,@PathVariable String password,@PathVariable int id){
        userService.updateUser(email,name,password,id);
    }

    @PostMapping
    public void create(@RequestBody User user) throws NotFoundException {
        userService.saveUser(user);
    }

    @Transactional
    @PostMapping("/save-user/{addressId}/{phoneId}")
    public void save(
            @RequestParam String email,
            @RequestParam String name,
            @RequestParam String password,
            @RequestParam Gender gender,
            @RequestParam Status status,
            @PathVariable Integer addressId,
            @PathVariable Integer phoneId) throws NotFoundException {
        User user = new User(name,email,password,gender,status);
        Address address = addressService.getByID(addressId);
        Phone phone = phoneService.getByID(phoneId);
        user.setAddress(address);
        user.setPhone(phone);
        userService.saveUser(user);
    }

    @PostMapping("/login")
    public void logIn(@RequestBody User user) throws BadRequest {
        userService.logIn(user);
    }

    @RequestMapping("/verify")
    public void verify(@RequestParam String email) throws NotFoundException {
        userService.verify(email);
    }

    @PostMapping("/send-email")
    public void sendEmail(@RequestParam String email)  {
        userService.sendEmail(email);
    }
}
