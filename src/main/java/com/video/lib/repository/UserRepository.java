package com.video.lib.repository;

import com.video.lib.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUserName(String userName);
}
