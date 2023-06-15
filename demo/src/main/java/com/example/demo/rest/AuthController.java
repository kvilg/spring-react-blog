package com.example.demo.rest;



import com.example.demo.model.moduleHttp.UserAuth;
import com.example.demo.servis.AuthRestService;
import org.springframework.http.ResponseEntity;
import com.example.demo.servis.AuthService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthRestService service;

    @GetMapping(value="/login/token")
    public ResponseEntity<?> getAuthUserByToken(@RequestHeader("Authorization") String token) {
        return service.loginByToken(token);
    }

    @PostMapping(value="/login")
    public ResponseEntity<?> getAuthUser(@RequestBody UserAuth json) {
        return service.login(
                json.getEmail(),
                json.getPassword()
        );
    }

    @PostMapping(path = "/registration")
    public ResponseEntity<?>  registration(@RequestBody UserAuth json) {
        return service.registration(
                json.getFirstName(),
                json.getSecondName(),
                json.getEmail(),
                json.getPassword()
        );
    }

}
