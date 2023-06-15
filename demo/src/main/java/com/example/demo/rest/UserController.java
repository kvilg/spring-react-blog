package com.example.demo.rest;

import com.example.demo.model.moduleHttp.UserModuleIn;
import com.example.demo.servis.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping(value="/avatar/update")
    public ResponseEntity<?> updateAvatar(@RequestHeader("Authorization") String token,
                                          @RequestBody UserModuleIn json){
        return service.updateAvatar(
                json.getImg(),
                token
        );
    }

}
