package com.video.lib.service;

import com.video.lib.dto.LogInDTO;
import com.video.lib.dto.UserDTO;
import com.video.lib.model.UserEntity;
import com.video.lib.repository.UserRepository;
import com.video.lib.response.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class AuthService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public AuthService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public String registerUser(UserDTO registration) {
        UserEntity newUser =userRepository.findByUserName(registration.getUserName());

        if(Objects.nonNull(newUser)){
            return "UserName already exist. Please try new one.";
        }

        newUser = modelMapper.map(registration, UserEntity.class);
        try {
            userRepository.save(newUser);
        }catch (Exception e){
            log.error("Unable to register the user. Please try again.");
            return "Failed to register the user.";
        }
        return "Successfully registered the user.";
    }

    public LoginResponse login(LogInDTO logInDetails){
        LoginResponse loginResponse = new LoginResponse();
        UserEntity userEntity = userRepository.findByUserName(logInDetails.getUserName());

        if(Objects.isNull(userEntity)){
            loginResponse.setAuthenticated(false);
            loginResponse.error("Please verify your user name");
            return loginResponse;
        }

        loginResponse.setAuthenticated(userEntity.getPassword().equals(logInDetails.getPassword()));

        if(!loginResponse.isAuthenticated())
            loginResponse.error("Please verify your password");

        return loginResponse;
    }
}
