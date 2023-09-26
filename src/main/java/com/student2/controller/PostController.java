package com.student2.controller;

import com.student2.payload.PostDto;
import com.student2.postResponse.PostResponse;
import com.student2.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    //http://localhost:8080/api/post
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto dto, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto postDto = postService.savePost(dto);
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/post?id=2
    @DeleteMapping
    public ResponseEntity<?> deletePostData(@RequestParam("id")long id){
        postService.deletePost(id);
        return new ResponseEntity<>("delete this record", HttpStatus.OK);
    }

    //http://localhost:8080/api/post/2
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> update(@PathVariable("id")long id, @RequestBody PostDto postDto){
        PostDto dto = postService.updatePost(id, postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public PostResponse getAllPost(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false)int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "3", required = false)int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false)String sortBy
    ){
        PostResponse dto = postService.getPost(pageNo, pageSize,sortBy);
        return dto;
    }
}
