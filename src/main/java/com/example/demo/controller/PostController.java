package com.example.demo.controller;


import com.example.demo.dal.PostDAL;
import com.example.demo.model.Post;
import com.example.demo.payload.response.ResponseModel;
import com.example.demo.security.services.UserDetailsImpl;
import com.example.demo.util.Constants;
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
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return ResponseEntity.ok(new ResponseModel(true, Constants.successMessage, null, postDAL.getAllPosts(userDetails.getId())));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseModel(false, Constants.errorMessage, e.getMessage(), null));
        }
    }

    @GetMapping("/likePost/{postId}")
    public ResponseEntity<?> likePost(@Valid @PathVariable String postId,Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return ResponseEntity.ok(new ResponseModel(true, Constants.successMessage, null, postDAL.likePost(userDetails.getId(), postId)));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseModel(false, Constants.errorMessage, e.getMessage(), null));
        }
    }

    @GetMapping("/disLikePost/{postId}")
    public ResponseEntity<?> disLikePost(@Valid @PathVariable String postId,Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return ResponseEntity.ok(new ResponseModel(true, Constants.successMessage, null, postDAL.disLikePost(userDetails.getId(), postId)));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseModel(false, Constants.errorMessage, e.getMessage(), null));
        }
    }

    @GetMapping("/getPosts")
    public ResponseEntity<?> getPosts(Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return ResponseEntity.ok(new ResponseModel(true, Constants.successMessage, null, postDAL.getPosts(userDetails.getId())));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseModel(false, Constants.errorMessage, e.getMessage(), null));
        }
    }

    @PostMapping("/addPost")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Post post, Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            post.setUserId(userDetails.getId());
            return ResponseEntity.ok(new ResponseModel(true, Constants.successMessage, null, postDAL.addPost(post)));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseModel(false, Constants.errorMessage, e.getMessage(), null));
        }
    }



}
