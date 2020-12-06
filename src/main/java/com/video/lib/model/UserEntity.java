package com.video.lib.model;

import com.video.lib.utils.CryptoUtil;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_table")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",nullable = false, unique = true)
    private Integer userId;

    @Column(name="user_name", nullable = false, unique = true)
    private String userName;

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


}