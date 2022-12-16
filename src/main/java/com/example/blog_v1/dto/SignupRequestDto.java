package com.example.blog_v1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class SignupRequestDto {
    private String username;
    private String password;
    private boolean admin = false;
    private String adminToken = "";
}