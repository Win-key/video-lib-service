package com.video.lib.controller;

import com.video.lib.dto.BaseResponse;
import com.video.lib.dto.UserDTO;
import com.video.lib.exception.ResourceNotFoundException;
import com.video.lib.model.UserEntity;
import com.video.lib.model.UserRole;
import com.video.lib.repository.UserRepository;
import com.video.lib.security.UserPrincipal;
import com.video.lib.utils.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @author Venkatesh Rajendran
 */

@Slf4j
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

    // Todo: modify with admin
    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/add")
    public ResponseEntity<BaseResponse> addUsers(@RequestBody UserDTO userDTO){
        return saveUsers(-1, userDTO);
    }

    // Todo: modify with admin
    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> saveUsers(@PathVariable("id") Integer id, @RequestBody UserDTO userDTO){
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);

        UserEntity userEntity;

        if (userEntityOptional.isEmpty()) {
            userEntity = new UserEntity();
            userEntity.setPasswordEnc(userDTO.getPassword());
        }
        else {
            userEntity = userEntityOptional.get();
        }
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setAdmin(userDTO.isAdmin());
        userEntity.setRole(userDTO.isAdmin() ? UserRole.ADMIN : UserRole.USER);

        try {
            userRepository.save(userEntity);
        }catch (Exception e){
            log.error("Unable to modify the user",e);
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Unable save the user "+ userEntity.getFirstName()).asResponseEntity();
        }

        return new BaseResponse<>(HttpStatus.CREATED, "Successfully save the user "+ userEntity.getFirstName()).asResponseEntity();
    }

    // Todo: modify with admin
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> deleteUser(@PathVariable("id") Integer id) {
        try {
            userRepository.deleteById(id);
        }catch (Exception e){
            log.error("Unable to delete the user");
            return new BaseResponse<>(HttpStatus.EXPECTATION_FAILED, "Unable to deleted the user").asResponseEntity();
        }
        return new BaseResponse<>(HttpStatus.OK, "Successfully deleted the user").asResponseEntity();
    }
}
