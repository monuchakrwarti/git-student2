package com.student2.controller;

import com.student2.entity.Comment;
import com.student2.payload.CommentDto;
import com.student2.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }
    //http://localhost:8080/api/posts/1/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value="postId") long postId, @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    //http:localhost:8080/api/post/1/comments
    @DeleteMapping("/post/{postId}/comments")
    public ResponseEntity<?> delete(@PathVariable("postId")long id){
        commentService.deletePostComment(id);
        return new ResponseEntity<String>("delete this is id: "+id, HttpStatus.OK);
    }

        //http://localhost:8080/api/post/1/comments
    @PutMapping("/post/{postId}/comments")
    public ResponseEntity<?> update(@PathVariable("postId")long id, @RequestBody CommentDto commentDto){
        commentService.updateComment(id, commentDto);
        return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/post/1/comments
    @GetMapping("/post/{postId}/comments")
    public List<CommentDto> getAll(@PathVariable(value = "postId")Long postId){
        return commentService.getAllComments(postId);
    }

    //http://localhost:8080/api/post/1/comments/2
    @GetMapping("post/{postId}/comments/commentId")
    public CommentDto getPostComment(
            @PathVariable(value = "postId")long postId,
            @PathVariable(value = "commentId")long commentId
    ){
        CommentDto postComment = commentService.getPostComment(postId, commentId);
        return postComment;

    }

    //http://localhost:8080/api
    @GetMapping
    public List<Comment> getAllComment(){
        return commentService.getcomment();
    }
}

