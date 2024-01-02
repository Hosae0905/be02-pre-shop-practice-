package com.example.onlineshop.user.model.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostSignUpUserRes {
    private Long id;
    private String email;
    private String name;
    private String imagePath;
}
