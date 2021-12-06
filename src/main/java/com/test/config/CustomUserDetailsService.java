package com.test.config;

import com.test.exceptions.NotFoundException;
import com.test.model.Authority;
import com.test.model.Status;
import com.test.model.User;
import com.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        User user;
        try {
            user = userService.findByEmail(email);
        } catch (UsernameNotFoundException | NotFoundException e) {
            throw new UsernameNotFoundException("Wrong user email: " + email);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getAuthority().forEach((authority -> authorities.add(new SimpleGrantedAuthority(authority.getName()))));
        if (user.getStatus().equals(Status.UNVERIFIED)) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                    false, true, true, false, authorities);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true, true, false, authorities);
    }
}
