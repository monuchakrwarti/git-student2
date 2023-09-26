package com.student2.service;

import com.student2.entity.Comment;
import com.student2.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    void deletePostComment(long id);

    CommentDto updateComment(long id, CommentDto commentDto);

    List<CommentDto> getAllComments(long postId);

    CommentDto getPostComment(long postId, long commentId);

    List<Comment> getcomment();
}
