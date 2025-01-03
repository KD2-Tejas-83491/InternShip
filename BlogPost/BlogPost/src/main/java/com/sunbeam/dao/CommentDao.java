package com.sunbeam.dao;

import com.sunbeam.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
}
