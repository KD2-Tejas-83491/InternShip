package com.sunbeam.dto;

public class CommentDTO {

    private Long postId;
    private String content;
    private Long authorId;

    // Constructors, Getters, Setters

    public CommentDTO() {}

    public CommentDTO(Long postId, String content, Long authorId) {
        this.postId = postId;
        this.content = content;
        this.authorId = authorId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
