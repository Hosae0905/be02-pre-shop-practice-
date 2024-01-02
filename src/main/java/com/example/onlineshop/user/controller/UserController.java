package com.example.onlineshop.user.controller;

import com.example.onlineshop.user.model.request.PostSignUpUserReq;
import com.example.onlineshop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public ResponseEntity<Object> signUp(PostSignUpUserReq postSignUpUserReq) {
        return ResponseEntity.ok().body(userService.signUp(postSignUpUserReq));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/read")
    public ResponseEntity<Object> read(Long id) {
        userService.read(id);
        return ResponseEntity.ok().body("ok");
    }
}
