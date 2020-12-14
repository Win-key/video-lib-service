package com.video.lib.service;

import com.video.lib.model.CategoryEntity;
import com.video.lib.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Venkatesh Rajendran
 * @vendor (Ideas2IT)
 */

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryEntity> fetchCategories() {
        return categoryRepository.findAll();
    }
}
