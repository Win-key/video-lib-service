package com.video.lib.model;

import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Venkatesh Rajendran
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_table")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",nullable = false, unique = true)
    private Integer id;

    @Column(name="user_name", nullable = false, unique = true)
    private String username;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="password_enc", nullable = false)
    private String passwordEnc;

    @Column(name = "location")
    private String location;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private AuthProvider provider;

}
