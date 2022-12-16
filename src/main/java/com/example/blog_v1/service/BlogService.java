package com.example.blog_v1.service;

import com.example.blog_v1.dto.BlogRequestDto;
import com.example.blog_v1.dto.BlogResponseDto;
import com.example.blog_v1.dto.MsgResponseDto;
import com.example.blog_v1.entity.Blog;
import com.example.blog_v1.entity.User;
import com.example.blog_v1.jwt.JwtUtil;
import com.example.blog_v1.repository.BlogRepository;
import com.example.blog_v1.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public BlogResponseDto createBlog(BlogRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 사용자 입니다.")
            );

            Blog blog = blogRepository.saveAndFlush(new Blog(requestDto, user.getId()));
            return new BlogResponseDto(blog);
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<BlogResponseDto> getListBlogs() {
        List<Blog> blogList =  blogRepository.findAllByOrderByModifiedAtDesc();
        List<BlogResponseDto> blogResponseDto = new ArrayList<>();

        for (Blog blog : blogList) {
            blogResponseDto.add(new BlogResponseDto(blog));
        }
        return blogResponseDto;
    }

    @Transactional(readOnly = true)
    public BlogResponseDto getBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글 입니다.")
        );
        return new BlogResponseDto(blog);
    }

    @Transactional
    public BlogResponseDto updateBlog(Long id, BlogRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 유저 입니다.")
            );

            Blog blog = blogRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 게시글 입니다.")
            );
            blog.update(requestDto);
            return new BlogResponseDto(blog);
        } else {
            return null;
        }
    }

    @Transactional
    public MsgResponseDto deleteBlog (Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 유저 입니다.")
            );

            Blog blog = blogRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 게시글 입니다.")
            );
            blogRepository.delete(blog);
            return new MsgResponseDto("게시글 삭제 성공", HttpStatus.OK.value());

        } else {
            return new MsgResponseDto("게시글은 작성자만 삭제 가능", HttpStatus.OK.value());
        }
    }
}