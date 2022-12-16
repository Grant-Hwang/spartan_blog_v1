package com.example.blog_v1.controller;

import com.example.blog_v1.dto.BlogRequestDto;
import com.example.blog_v1.dto.BlogResponseDto;
import com.example.blog_v1.dto.MsgResponseDto;
import com.example.blog_v1.service.BlogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BlogController {

    private final BlogService blogService;

    @PostMapping("/api/blogs")
    public BlogResponseDto createBlog(@RequestBody BlogRequestDto requestDto, HttpServletRequest request) {
        return blogService.createBlog(requestDto, request);
    }

    @GetMapping("/api/blogs")
    public List<BlogResponseDto> getListBlogs() {
        return blogService.getListBlogs();
    }

    @GetMapping("/api/blogs/{id}")
    public BlogResponseDto getBlogs(@PathVariable Long id) {
        return blogService.getBlog(id);
    }

    @PutMapping("/api/blogs/{id}")
    public BlogResponseDto updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto, HttpServletRequest request) {
        return blogService.updateBlog(id, requestDto, request);
    }

    @DeleteMapping("/api/blogs/{id}")
    public MsgResponseDto deleteBlog(@PathVariable Long id, HttpServletRequest request) {
        return blogService.deleteBlog(id, request);
    }
}