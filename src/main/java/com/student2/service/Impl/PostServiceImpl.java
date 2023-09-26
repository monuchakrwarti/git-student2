package com.student2.service.Impl;

import com.student2.entity.Post;
import com.student2.exception.ResourceNotFound;
import com.student2.payload.PostDto;
import com.student2.postResponse.PostResponse;
import com.student2.repository.PostRepository;
import com.student2.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto savePost(PostDto dto) {
        Post post = mapToEntity(dto);
        Post save = postRepository.save(post);
        PostDto postDto = mapToDto(save);
        return postDto;
    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFound("this id is not found->"+id)

        );
        Optional<Post> byId = postRepository.findById(id);
        Post post1 = byId.get();
        post1.setTitle(postDto.getTitle());
        post1.setDescription(postDto.getDescription());
        post1.setContent(postDto.getContent());
        postRepository.save(post1);
        return postDto;
    }

    //http://localhost:8080/api/post?pageNo=0&pageSize=3&sortBy=id
    @Override
    public PostResponse getPost(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Post> pagePosts = postRepository.findAll(pageable);
        List<Post> posts = pagePosts.getContent();
        List<PostDto> postDtos = posts.stream().map(post->mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setPostDto(postDtos);
        postResponse.setPageNo(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPage(pagePosts.getTotalPages());
        postResponse.setLast(pagePosts.isLast());

        return postResponse;
    }


    //Entity to Dto
    public PostDto mapToDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);
        //PostDto dto = new PostDto();
        //dto.setId(post.getId());
        //dto.setTitle(post.getTitle());
        //dto.setDescription(post.getDescription());
        //dto.setContent(post.getContent());
        return dto;
    }

    //Dto to Entity
    public Post mapToEntity(PostDto dto){
        Post post = modelMapper.map(dto, Post.class);
        //Post post = new Post();
        //post.setId(dto.getId());
        //post.setTitle(dto.getTitle());
        //post.setDescription(dto.getDescription());
        //post.setContent(dto.getContent());
        return post;
    }
}
