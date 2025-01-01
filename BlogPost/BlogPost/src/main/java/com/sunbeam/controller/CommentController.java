package com.sunbeam.controller;

import com.sunbeam.dto.CommentDTO;
import com.sunbeam.dto.UpdateCommentDTO;
import com.sunbeam.entities.Comment;
import com.sunbeam.entities.User;
import com.sunbeam.entities.Post;
import com.sunbeam.entities.Role;
import com.sunbeam.service.CommentService;
import com.sunbeam.service.UserService;
import com.sunbeam.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;  

    @Autowired
    private PostService postService;  

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentDTO commentDTO) {
        Optional<User> user = userService.findById(commentDTO.getAuthorId());
        Post post = postService.getPostById(commentDTO.getPostId());  

        if (user.isPresent() && post != null) {  
            Comment comment = new Comment();
            comment.setContent(commentDTO.getContent());
            comment.setAuthor(user.get());
            comment.setPost(post);

            Comment createdComment = commentService.createComment(comment);
            return ResponseEntity.ok(createdComment);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        Comment comment = commentService.getCommentById(id);
        return comment != null ? ResponseEntity.ok(comment) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody @Valid UpdateCommentDTO updateCommentDTO) {
        // Get the logged-in user's details
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userService.findByEmail(loggedInUsername); // Fetch user details

        // Fetch the comment from the database
        Comment comment = commentService.getCommentById(id);
        if (comment == null) {
            return ResponseEntity.notFound().build(); // Comment not found
        }

        // Check if the logged-in user has ADMIN role
        if (loggedInUser.getRoles() == Role.ADMIN) {
            comment.setContent(updateCommentDTO.getContent());
            Comment updatedComment = commentService.updateComment(id, comment);
            return ResponseEntity.ok(updatedComment);
        }

        // Check if the logged-in user is the author of the comment
        if (comment.getAuthor().getId().equals(loggedInUser.getId())) {
            comment.setContent(updateCommentDTO.getContent());
            Comment updatedComment = commentService.updateComment(id, comment);
            return ResponseEntity.ok(updatedComment);
        }

        // If neither condition is met, return forbidden
        return ResponseEntity.status(403).build(); // Forbidden
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
       
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userService.findByEmail(loggedInUsername); // Fetch user details

        
        Comment comment = commentService.getCommentById(id);
        if (comment == null) {
            return ResponseEntity.notFound().build(); // Comment not found
        }

      
        if (loggedInUser.getRoles() == Role.ADMIN) {
            commentService.deleteComment(id);
            return ResponseEntity.noContent().build();
        }

       
        if (comment.getAuthor().getId().equals(loggedInUser.getId())) {
            commentService.deleteComment(id);
            return ResponseEntity.noContent().build();
        }

       
        return ResponseEntity.status(403).build(); // Forbidden
    }

}
