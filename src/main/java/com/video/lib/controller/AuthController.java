package com.video.lib.controller;

import com.video.lib.dto.BaseResponse;
import com.video.lib.dto.LogInDTO;
import com.video.lib.dto.UserDTO;
import com.video.lib.model.UserEntity;
import com.video.lib.security.TokenProvider;
import com.video.lib.service.AuthService;
import org.modelmapper.ModelMapper;
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

/**
 * @author Venkatesh Rajendran
 */

@RestController
@RequestMapping("/auth")
public class AuthController {

    public static String COOKIE_NAME = "x-auth-token";

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private TokenProvider tokenProvider;
    private AuthService authService;
    private ModelMapper modelMapper;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
                          TokenProvider tokenProvider, AuthService authService, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.authService = authService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> registration(@RequestBody UserDTO registration){
        BaseResponse<String> response = authService.registerUser(registration);
        return response.asResponseEntity();
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@RequestBody LogInDTO login, HttpServletResponse response){

        Optional<UserEntity> userEntity = authService.userByUsername(login.getUsername());
        if(userEntity.isEmpty()) {
            return new BaseResponse<>(HttpStatus.UNAUTHORIZED, "User doesn't exist.").asResponseEntity();
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUsername(),
                        login.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);

        UserDTO userDTO = modelMapper.map(userEntity.get(), UserDTO.class);
        userDTO.setToken(token);
        BaseResponse<UserDTO> baseResponse = new BaseResponse<>(HttpStatus.OK, userDTO);
        return baseResponse.asResponseEntity();
    }

}
