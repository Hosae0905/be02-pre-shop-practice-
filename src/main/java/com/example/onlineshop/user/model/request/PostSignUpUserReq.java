package com.example.onlineshop.user.model.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostSignUpUserReq {
    private String email;
    private String password;
    private String name;
    private MultipartFile image;
}
