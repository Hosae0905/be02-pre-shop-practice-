package com.example.onlineshop.user.service;

import com.amazonaws.services.s3.AmazonS3;
import com.example.onlineshop.user.model.User;
import com.example.onlineshop.user.model.request.PostSignUpUserReq;
import com.example.onlineshop.user.model.response.PostSignUpUserRes;
import com.example.onlineshop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SaveFileService saveFileService;

    public PostSignUpUserRes signUp(PostSignUpUserReq postSignUpUserReq) {
        String saveFile = saveFileService.saveFile(postSignUpUserReq.getImage());
        User user = userRepository.save(User.dtoToEntity(postSignUpUserReq, saveFile));


        return PostSignUpUserRes.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .imagePath(user.getImage())
                .build();
    }

    public void read(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {

        }
    }
}
