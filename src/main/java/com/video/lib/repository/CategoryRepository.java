package com.video.lib.repository;

import com.video.lib.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Venkatesh Rajendran
 *
 */

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findByCategoryId(String categoryID);
}
