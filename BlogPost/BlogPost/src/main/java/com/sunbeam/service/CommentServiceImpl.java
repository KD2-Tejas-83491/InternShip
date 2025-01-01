package com.sunbeam.service;

import com.sunbeam.dao.CommentDao;
import com.sunbeam.entities.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public Comment createComment(Comment comment) {
        return commentDao.save(comment);
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentDao.findByPostId(postId);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentDao.findById(id).orElse(null);
    }

    @Override
    public Comment updateComment(Long id, Comment commentDetails) {
        return commentDao.findById(id).map(comment -> {
            comment.setContent(commentDetails.getContent());
            return commentDao.save(comment);
        }).orElse(null);
    }

    @Override
    public void deleteComment(Long id) {
        commentDao.deleteById(id);
    }
}
