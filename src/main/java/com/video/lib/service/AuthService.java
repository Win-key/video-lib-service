package com.video.lib.service;

import com.video.lib.dto.BaseResponse;
import com.video.lib.dto.LogInDTO;
import com.video.lib.dto.UserDTO;
import com.video.lib.model.UserEntity;
import com.video.lib.repository.UserRepository;
import com.video.lib.response.LoginResponse;
import com.video.lib.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Venkatesh Rajendran
 */

@Service
@Slf4j
public class AuthService implements UserDetailsService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public BaseResponse<String> registerUser(UserDTO registration) {
        UserEntity newUser =userRepository.findByUsername(registration.getUsername()).orElse(null);

        if(Objects.nonNull(newUser)){
            return new BaseResponse<>(HttpStatus.BAD_REQUEST,"UserName already exist. Please try new one.");
        }

        newUser = modelMapper.map(registration, UserEntity.class);
        // Encrypts the password
        newUser.setPasswordEnc(passwordEncoder.encode(registration.getPassword()));
        try {
            userRepository.save(newUser);
        }catch (Exception e){
            log.error("Unable to register the user. Please try again.");
            return new BaseResponse<>(HttpStatus.BAD_REQUEST,"Failed to register the user.");
        }
        return new BaseResponse<>(HttpStatus.OK,"Successfully registered the user.");
    }

    public LoginResponse login(LogInDTO logInDetails){
        LoginResponse loginResponse = new LoginResponse();
        UserEntity userEntity = userRepository.findByUsername(logInDetails.getUsername()).orElse(null);

        if(Objects.isNull(userEntity)){
            loginResponse.setAuthenticated(false);
            loginResponse.error("Please verify your user name");
            return loginResponse;
        }

        loginResponse.setAuthenticated(userEntity.getPasswordEnc().equals(logInDetails.getPassword()));

        if(!loginResponse.isAuthenticated())
            loginResponse.error("Please verify your password");

        return loginResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity userEntity = userByUsername(userName)
                                .orElseThrow(() -> new UsernameNotFoundException("User is not found. Please register."));

        return UserPrincipal.create(userEntity);
    }

    public Optional<UserEntity> userByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

    public UserDetails loadUserById(Integer userId) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findById(userId)
                                .orElseThrow(() -> new UsernameNotFoundException("No matching User is not found with the id "+userId+"."));

        return UserPrincipal.create(userEntity);
    }
}
