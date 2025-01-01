package com.sunbeam.dao;

import com.sunbeam.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostDao extends JpaRepository<Post, Long> {
    List<Post> findByAuthorId(Long authorId);
}
