package com.video.lib.repository;

import com.video.lib.model.DurationEntity;
import com.video.lib.model.PlaylistEntity;
import com.video.lib.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author Venkatesh Rajendran
 */

public interface DurationRepository extends JpaRepository<DurationEntity, Integer> {

    @Query(value = "SELECT du FROM DurationEntity du WHERE du.playlistEntity=:playlistEntity AND du.userEntity=:userEntity")
    Optional<DurationEntity> findByVideoAndUser(@Param("playlistEntity") PlaylistEntity playlistEntity, @Param("userEntity") UserEntity userEntity);

}
