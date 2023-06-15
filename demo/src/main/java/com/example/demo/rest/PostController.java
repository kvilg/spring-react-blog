package com.example.demo.rest;

import com.example.demo.model.moduleHttp.PostModule;
import com.example.demo.servis.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/post")
public class PostController {

    @Autowired
    private PostService service;

    @PostMapping(value="/create")
    public ResponseEntity<?> createPost(@RequestBody PostModule json,
                                        @RequestHeader("Authorization") String token) {
        return service.createPost(
                json.getLogo(),
                json.getText(),
                json.getImg(),
                token
        );
    }

    @GetMapping(value="/all/{id}")
    public ResponseEntity<?> getAllPost(@RequestParam ("id") Long id) {
        return service.getAllPost(id);
    }

}
