package com.example.demo.servis;

import com.example.demo.model.entity.User;
import com.example.demo.model.moduleHttp.ExceptionModel;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private AuthRestService service;

    @Autowired
    private UserRepo dataUser;

    public ResponseEntity<?> updateAvatar(String img, String token) {
        try{
            User user = service.getUserByToken(token);
            if(user == null){
                return new ResponseEntity<>(new ExceptionModel("Неудалось авторизоваться"), HttpStatus.UNAUTHORIZED);
            }
            user.setImg(img);
            dataUser.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ExceptionModel("Ошибка обновления аватара"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
