package com.example.demo.controller;


import com.example.demo.dal.PostDAL;
import com.example.demo.model.Post;
import com.example.demo.model.Product;
import com.example.demo.payload.response.ResponseModel;
import com.example.demo.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    PostDAL postDAL;

    @GetMapping("/getAllPosts")
    public ResponseEntity<?> getAllPost(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new ResponseModel(true, "Success", postDAL.getAllPosts(userDetails.getId())));
    }

    @GetMapping("/getPosts")
    public ResponseEntity<?> getPosts(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new ResponseModel(true, "Success", postDAL.getPosts(userDetails.getId())));
    }

    @PostMapping("/addPost")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Post post, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        post.setUserId(userDetails.getId());
        return ResponseEntity.ok(new ResponseModel(true, "Success", postDAL.addPost(post)));
    }



}
