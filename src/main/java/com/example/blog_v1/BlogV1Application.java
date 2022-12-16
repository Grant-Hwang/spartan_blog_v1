package com.example.blog_v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BlogV1Application {

    public static void main(String[] args) {
        SpringApplication.run(BlogV1Application.class, args);
    }

}
