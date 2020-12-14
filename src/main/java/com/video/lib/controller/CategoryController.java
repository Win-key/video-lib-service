package com.video.lib.controller;

import com.video.lib.dto.BaseResponse;
import com.video.lib.dto.CategoryDTO;
import com.video.lib.service.CategoryService;
import com.video.lib.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Venkatesh Rajendran
 *
 */

@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/all")
    public ResponseEntity<BaseResponse> fetchCategories(){
        List<CategoryDTO> categories = ObjectMapperUtils.mapAll(categoryService.fetchCategories(), CategoryDTO.class);
        return new BaseResponse<>(HttpStatus.OK,categories).asResponseEntity();
    }
}
