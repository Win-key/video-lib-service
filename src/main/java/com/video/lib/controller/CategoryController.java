package com.video.lib.controller;

import com.video.lib.dto.BaseResponse;
import com.video.lib.dto.CategoryDTO;
import com.video.lib.dto.LogInDTO;
import com.video.lib.exception.ResourceNotFoundException;
import com.video.lib.service.CategoryService;
import com.video.lib.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/{categoryID}")
    public ResponseEntity<BaseResponse> getCategory(@PathVariable("categoryID") String categoryID){
        BaseResponse<Object> response = categoryService.getCategory(categoryID);
        return response.asResponseEntity();
    }

    // Todo : Fixme this has to be modified to PreAuthorize with admin role
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping(value = "/{categoryID}")
    public ResponseEntity<BaseResponse> deleteCategory(@PathVariable("categoryID") String categoryID){
        BaseResponse<String> response = categoryService.deleteCategory(categoryID);
        return response.asResponseEntity();
    }

    // Todo : Fixme this has to be modified to PreAuthorize with admin role
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<BaseResponse> addOrUpdateCategory(@RequestParam("isUpdate") boolean isUpdate, @RequestBody CategoryDTO categoryDTO){
        BaseResponse<String> response = categoryService.addOrUpdateCategory(isUpdate, categoryDTO);
        return response.asResponseEntity();
    }
}
