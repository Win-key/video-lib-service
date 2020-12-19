package com.video.lib.controller;

import com.video.lib.dto.BaseResponse;
import com.video.lib.dto.UserDTO;
import com.video.lib.exception.ResourceNotFoundException;
import com.video.lib.model.UserEntity;
import com.video.lib.repository.UserRepository;
import com.video.lib.security.UserPrincipal;
import com.video.lib.utils.ObjectMapperUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Venkatesh Rajendran
 */

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserController(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/whoami")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse> getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        UserEntity userEntity = userRepository.findByUsername(userPrincipal.getUserName())
                .orElseThrow(() -> new ResourceNotFoundException("User", "userName", userPrincipal.getUserName()));
        return new BaseResponse<>(HttpStatus.OK,modelMapper.map(userEntity, UserDTO.class)).asResponseEntity();
    }

    @GetMapping
    public ResponseEntity<BaseResponse> fetchUsers(){
        List<UserDTO> users = ObjectMapperUtils.mapAll(userRepository.findAll(), UserDTO.class);
        return new BaseResponse<>(HttpStatus.OK, users).asResponseEntity();
    }
}
