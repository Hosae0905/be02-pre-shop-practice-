package com.example.onlineshop.user.model;

import com.example.onlineshop.user.model.request.PostSignUpUserReq;
import lombok.*;

import javax.persistence.*;
import java.io.File;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(length = 30)
    private String name;

    @Column(length = 200)
    private String image;


    public static User dtoToEntity(PostSignUpUserReq postSignUpUserReq, String saveFile) {
        return User.builder()
                .email(postSignUpUserReq.getEmail())
                .password(postSignUpUserReq.getPassword())
                .name(postSignUpUserReq.getName())
                .image(saveFile.replace(File.separator, "/"))
                .build();
    }
}
