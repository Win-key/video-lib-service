package com.video.lib.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

    private Long userId;

    private String userName;

    private String firstName;

    private String lastName;

    @JsonIgnore
    private String passwordEnc;

    @JsonIgnore
    private String salt;

    private String password;

    private String location;

    private String mobileNumber;

}