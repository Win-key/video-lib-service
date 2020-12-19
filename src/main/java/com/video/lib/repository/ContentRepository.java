package com.video.lib.repository;

import com.video.lib.model.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContentRepository extends JpaRepository<ContentEntity, Integer> {

    Optional<ContentEntity> findByContentID(String contentID);

}
