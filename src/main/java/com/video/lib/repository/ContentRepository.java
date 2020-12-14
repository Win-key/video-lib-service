package com.video.lib.repository;

import com.video.lib.model.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<ContentEntity, Integer> {
}
