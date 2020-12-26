package com.video.lib.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Venkatesh Rajendran
 */

@Data
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    @JsonIgnore
    private String passwordEnc;

    private String password;

    private String location;

    private String mobileNumber;

    private String provider;

    private String token;

    private boolean isAdmin;

}