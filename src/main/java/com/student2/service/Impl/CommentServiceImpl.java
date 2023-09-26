package com.student2.service.Impl;

import com.student2.entity.Comment;
import com.student2.entity.Post;
import com.student2.exception.ResourceNotFound;
import com.student2.payload.CommentDto;
import com.student2.repository.CommentRepository;
import com.student2.repository.PostRepository;
import com.student2.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("not find this id " + postId)
        );
        comment.setPost(post);
        Comment saveComment = commentRepository.save(comment);
        CommentDto commentDto1 = mapToDto(saveComment);
        return commentDto1;
    }

    @Override
    public void deletePostComment(long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public CommentDto updateComment(long id, CommentDto commentDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("this id is not exist:-" + id)
        );
        Optional<Comment> byId = commentRepository.findById(id);
        Comment comt = byId.get();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment save = commentRepository.save(comt);
        CommentDto comtDto = mapToDto(save);

        return comtDto;
    }

    @Override
    public List<CommentDto> getAllComments(long postId) {
        Comment comt = commentRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("this id is not exist:-" + postId)
        );
        List<Comment> comments = commentRepository.findByPostId(postId);

        List<CommentDto> commentDtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public CommentDto getPostComment(long postId, long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFound("this id is not exist: " + commentId)
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("not find this id " + postId)
        );

        CommentDto commentDto = mapToDto(comment);
        return commentDto;
    }

    @Override
    public List<Comment> getcomment() {
        List<Comment> all = commentRepository.findAll();

        return all;
    }

    //dto to commentEntity
    public CommentDto mapToDto(Comment comment){
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }

    //commentEntity to dto
    public Comment mapToEntity(CommentDto commentDto){
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }

}
