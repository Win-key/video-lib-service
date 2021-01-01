package com.video.lib.repository;

import com.video.lib.model.ContentEntity;
import com.video.lib.model.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author Venkatesh Rajendran
 */

public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Integer> {

    @Modifying
    @Query(value = " delete from PlaylistEntity pl where pl.contentEntity = :contentEntity")
    void deleteByContentEntity(@Param("contentEntity") ContentEntity contentEntity);

    Optional<PlaylistEntity> findByVideoId(String videoID);

}
