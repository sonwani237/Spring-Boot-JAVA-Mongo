package com.example.demo.dal;

import com.example.demo.model.Post;

import java.util.List;

public interface PostDAL {
    List<Post> getAllPosts(String userId);

    List<Post> getPosts(String userId);

    Post addPost(Post post);

    Post likePost(String userId, String postId);

    Post disLikePost(String userId, String postId);
}
