package com.sunbeam.service;

import com.sunbeam.entities.Post;

import java.util.List;

public interface PostService {
    Post createPost(Post post);
    List<Post> getAllPosts();
    Post getPostById(Long id);
    Post updatePost(Long id, Post postDetails);
    void deletePost(Long id);
}
