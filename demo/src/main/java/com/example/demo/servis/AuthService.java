package com.example.demo.servis;


import com.example.demo.model.moduleHttp.ExceptionModel;
import com.example.demo.model.moduleHttp.UserOut;
import com.example.demo.model.entity.User;
import com.example.demo.repo.UserRepo;

import com.example.demo.security.JwtTokenUtil;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;
import java.util.*;

import static com.example.demo.model.Constants.TOKEN_PREFIX;


@Service
public class AuthService implements UserDetailsService{

    @Autowired
    private UserRepo userData;

    public User getByEmail(String email) {
        return userData.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = userData.findByEmail(email);
        if (Objects.isNull(u)) {
            throw new UsernameNotFoundException(String.format("User %s is not found", email));
        }
        return new org.springframework.security.core.userdetails.User(u.getEmail(), u.getPassword(), true, true, true, true, new HashSet<>());
   }




}
