package com.sunbeam.controller;

import com.sunbeam.dto.PostDTO;
import com.sunbeam.dto.UpdatePostDTO;
import com.sunbeam.entities.Post;
import com.sunbeam.entities.Role;
import com.sunbeam.entities.User;
import com.sunbeam.entities.Category;
import com.sunbeam.service.PostService;
import com.sunbeam.service.UserService;
import com.sunbeam.service.CategoryService;
import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;  // Injecting UserService

    @Autowired
    private CategoryService categoryService;  // Injecting CategoryService

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return post != null ? ResponseEntity.ok(post) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostDTO postDTO) {
        Optional<User> user = userService.findById(postDTO.getAuthorId());  // Use userService's findById method
        Optional<Category> category = categoryService.getCategoryById(postDTO.getCategoryId());  // Use categoryService's findById method

        if (user.isPresent() && category.isPresent()) {
            Post post = new Post();
            post.setTitle(postDTO.getTitle());
            post.setContent(postDTO.getContent());
            post.setAuthor(user.get());  // Set the author user
            post.setCategory(category.get());  // Set the category

            Post createdPost = postService.createPost(post);
            return ResponseEntity.ok(createdPost);
        } else {
            return ResponseEntity.badRequest().build();  // Return 400 if user or category is not found
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody @Valid UpdatePostDTO updatePostDTO) {
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userService.findByEmail(loggedInUsername);

        Post post = postService.getPostById(id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }

        if (!post.getAuthor().getId().equals(loggedInUser.getId())) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        // Update only title and content
        post.setTitle(updatePostDTO.getTitle());
        post.setContent(updatePostDTO.getContent());

        Post updatedPost = postService.updatePost(id, post);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        // Get the logged-in user's details
    	
    	
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userService.findByEmail(loggedInUsername); // Fetch user details
        System.out.println(loggedInUser);
        
        
       
        
      

        // Fetch the post from the database
        Post post = postService.getPostById(id);
        if (post == null) {
            return ResponseEntity.notFound().build(); // Post not found
        }

        
        // Check if the user has the ADMIN role
        if (loggedInUser.getRoles() == Role.ADMIN) {
            postService.deletePost(id);
            return ResponseEntity.noContent().build();
        }

        

        
        if (post.getAuthor().getId().equals(loggedInUser.getId())) {
            postService.deletePost(id);
            return ResponseEntity.noContent().build();
        }

        // If neither condition is met, return forbidden
        return ResponseEntity.status(403).build(); // Forbidden
    }


}
