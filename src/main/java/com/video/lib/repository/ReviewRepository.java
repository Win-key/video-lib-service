package com.video.lib.repository;

import com.video.lib.model.ContentEntity;
import com.video.lib.model.ReviewEntity;
import com.video.lib.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author Venkatesh Rajendran
 */

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {

    @Query(value = "SELECT r FROM ReviewEntity r WHERE userEntity=:userEntity AND contentEntity=:contentEntity")
    Optional<ReviewEntity> findByUserAndContent(@Param("userEntity") UserEntity userEntity, @Param("contentEntity") ContentEntity contentEntity);
}
