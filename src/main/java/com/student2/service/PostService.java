package com.student2.service;

import com.student2.payload.PostDto;
import com.student2.postResponse.PostResponse;

import java.util.List;

public interface PostService {
    PostDto savePost(PostDto dto);

    void deletePost(long id);

    PostDto updatePost(long id, PostDto postDto);

    PostResponse getPost(int pageNo, int pageSize, String sortBy);
}
