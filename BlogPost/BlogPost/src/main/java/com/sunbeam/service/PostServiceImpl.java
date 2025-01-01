package com.sunbeam.service;

import com.sunbeam.dao.PostDao;
import com.sunbeam.entities.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

   

    @Override
    public List<Post> getAllPosts() {
        return postDao.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        return postDao.findById(id).orElse(null);
    }

    @Override
    public Post createPost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        return postDao.save(post);
    }

    @Override
    public Post updatePost(Long id, Post postDetails) {
        return postDao.findById(id).map(post -> {
            post.setTitle(postDetails.getTitle());
            post.setContent(postDetails.getContent());
            post.setCategory(postDetails.getCategory());
            post.setUpdatedAt(LocalDateTime.now());
            return postDao.save(post);
        }).orElse(null);
    }


    @Override
    public void deletePost(Long id) {
        postDao.deleteById(id);
    }
}
