package com.video.lib.controller;

import com.video.lib.dto.LogInDTO;
import com.video.lib.dto.UserDTO;
import com.video.lib.model.UserEntity;
import com.video.lib.response.LoginResponse;
import com.video.lib.security.TokenProvider;
import com.video.lib.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping("/auth/")
public class AuthController {

    public static String COOKIE_NAME = "x-auth-token";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registration(@RequestBody UserDTO registration){
        return ResponseEntity.ok(authService.registerUser(registration));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LogInDTO login, HttpServletResponse response){

        Optional<UserEntity> userEntity = authService.userByUsername(login.getUserName());
        if(userEntity.isEmpty())
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUserName(),
                        login.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,COOKIE_NAME+"="+token)
                .build();
    }

}
