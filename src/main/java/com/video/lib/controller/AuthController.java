package com.video.lib.controller;

import com.video.lib.dto.LogInDTO;
import com.video.lib.dto.UserDTO;
import com.video.lib.response.LoginResponse;
import com.video.lib.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registration(@RequestBody UserDTO registration){
        return ResponseEntity.ok(authService.registerUser(registration));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LogInDTO login){
        return ResponseEntity.ok(authService.login(login));
    }

}
