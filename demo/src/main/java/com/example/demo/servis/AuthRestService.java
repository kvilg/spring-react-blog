package com.example.demo.servis;

import com.example.demo.model.entity.User;
import com.example.demo.model.moduleHttp.ExceptionModel;
import com.example.demo.model.moduleHttp.UserOut;
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
import java.util.HashSet;
import java.util.Objects;

import static com.example.demo.model.Constants.TOKEN_PREFIX;

@Service
public class AuthRestService {

    @Autowired
    private UserRepo userData;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public User getByEmail(String email) {
        return userData.findByEmail(email);
    }

    public User getUserByToken(String token){
        String userEmail = jwtTokenUtil.getUsernameFromToken(TOKEN_PREFIX + token);
        return getByEmail(userEmail);
    }



    @Transactional
    public ResponseEntity<?> login(String email, String password){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
            User user = getByEmail(email);
            String token = jwtTokenUtil.generateToken(user);
            UserOut outUser = new UserOut(user);
            MultiValueMap<String,String> headers = new HttpHeaders();
            headers.add("Authorization", TOKEN_PREFIX+token);
            return new ResponseEntity<>(outUser, headers, HttpStatus.OK);
        }catch (UsernameNotFoundException e){
            return new ResponseEntity<>(new ExceptionModel(e), HttpStatus.UNAUTHORIZED);
        }catch (RuntimeException e){
            return new ResponseEntity<>(new ExceptionModel(e), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(new ExceptionModel(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> registration(String firstName, String secondName, String email, String password) {
        try {
            if (firstName == null || secondName == null || email == null || password == null ||
                    firstName.equals("") || secondName.equals("") || email.equals("") || password.equals("")) {
                throw new NullPointerException("Не все данные заполнины");
            }
            User user = userData.findByEmail(email);
            if (user != null) {
                throw new RuntimeException("Пользователь уже существует");
            }
            User newUser = new User(firstName, secondName, email, password);
            userData.save(newUser);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (NullPointerException e){
            return new ResponseEntity<>(new ExceptionModel(e), HttpStatus.BAD_REQUEST);
        }catch (RuntimeException e){
            return new ResponseEntity<>(new ExceptionModel(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> loginByToken(String token) {
        try{
            if(token == null || token.equals("")){
                throw new NullPointerException("Отсусвует токен");
            }
            User user = getUserByToken(token);
            UserOut outUser = new UserOut(user);
            return new ResponseEntity<>(outUser,HttpStatus.OK);
        }catch (NullPointerException e){
            return new ResponseEntity<>(new ExceptionModel(e), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(new ExceptionModel(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
