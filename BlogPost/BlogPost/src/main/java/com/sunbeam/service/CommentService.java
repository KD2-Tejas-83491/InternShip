package com.sunbeam.service;

import com.sunbeam.entities.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment);
    List<Comment> getCommentsByPostId(Long postId);
    Comment getCommentById(Long id);
    Comment updateComment(Long id, Comment commentDetails);
    void deleteComment(Long id);
}
