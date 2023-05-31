package com.duy.carshowroomdemo.mapper;

import com.duy.carshowroomdemo.dto.PostDto;
import com.duy.carshowroomdemo.entity.Post;

public class PostMapper implements Mapper<Post, PostDto> {
    @Override
    public Post toEntity(PostDto source) {
        return (source == null) ? null : modelMapper.map(source, Post.class);
    }

    @Override
    public PostDto toDto(Post source) {
        return (source == null) ? null : modelMapper.map(source, PostDto.class);
    }
}
