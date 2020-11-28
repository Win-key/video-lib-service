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

    @Column(name = "salt", nullable = false)
    private String salt;

    @Transient
    private String password;

    @Column(name = "location")
    private String location;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @PrePersist
    public void prePersist(){
        if(Objects.isNull(salt))
            salt = UUID.randomUUID().toString().replaceAll("-","");

        if(Objects.nonNull(password)){
            passwordEnc = CryptoUtil.encrypt(password, salt);
        }
    }

    @PostLoad
    public void postLoad(){
        if(Objects.nonNull(salt) && Objects.nonNull(passwordEnc)){
            password = CryptoUtil.decrypt(passwordEnc, salt);
        }
    }
}
