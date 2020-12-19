package com.video.lib.service;

import com.video.lib.dto.BaseResponse;
import com.video.lib.dto.CategoryDTO;
import com.video.lib.dto.CategoryWithContentDTO;
import com.video.lib.dto.LogInDTO;
import com.video.lib.model.CategoryEntity;
import com.video.lib.repository.CategoryRepository;
import com.video.lib.utils.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Venkatesh Rajendran
 *
 */

@Slf4j
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

    @Transactional
    public BaseResponse<String> deleteCategory(String categoryID) {
        Optional<CategoryEntity> categoryOptional = categoryRepository.findByCategoryId(categoryID);
        if(categoryOptional.isEmpty())
                return new BaseResponse<>(HttpStatus.NOT_FOUND, String.format("Category ID %s does not exist", categoryID));
        try {
            categoryRepository.delete(categoryOptional.get());
        }catch (Exception e){
            log.error("Unable to delete the category.",e);
            return new BaseResponse<>(HttpStatus.EXPECTATION_FAILED, String.format("Unable to delete the category with category ID %s.", categoryID));
        }
        return new BaseResponse<>(HttpStatus.OK, String.format("Deleted category with id %s successfully", categoryID));
    }

    public BaseResponse<Object> getCategory(String categoryID) {
        Optional<CategoryEntity> categoryOptional = categoryRepository.findByCategoryId(categoryID);
        if(categoryOptional.isEmpty())
            return new BaseResponse<>(HttpStatus.NOT_FOUND, String.format("Category ID %s does not exist", categoryID));

        return new BaseResponse<>(HttpStatus.OK, ObjectMapperUtils.map(categoryOptional.get(), CategoryDTO.class));
    }

    // Todo : doesn't have support for parent id
    @Transactional
    public BaseResponse<String> addOrUpdateCategory(Boolean isUpdate, CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = ObjectMapperUtils.map(categoryDTO, CategoryEntity.class);
        if(Objects.isNull(categoryDTO) || Objects.isNull(categoryDTO.getCategoryId()) ||
                Objects.isNull(categoryDTO.getCategory()) || Objects.isNull(categoryDTO.getCategoryDisplayName()))
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Please enter valid category details");

        if(isUpdate && Objects.nonNull(categoryEntity.getCategoryId())){
            Optional<CategoryEntity> byCategoryId = categoryRepository.findByCategoryId(categoryEntity.getCategoryId());
            if(byCategoryId.isEmpty())
                return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Cannot find given category with id " + categoryEntity.getCategoryId());

            CategoryEntity updatedCategory = byCategoryId.get();
            categoryEntity.setId(updatedCategory.getId());

        }
        try {
            categoryRepository.save(categoryEntity);
        }catch (Exception e){
            log.error("Unable to save/update the category.",e);
            return new BaseResponse<>(HttpStatus.EXPECTATION_FAILED, "Failed updated the category.");
        }
        return new BaseResponse<>(HttpStatus.CREATED, "Successfully updated the category.");
    }

    public BaseResponse<List<CategoryWithContentDTO>> getAllContents() {
        List<CategoryEntity> allCategories = categoryRepository.findAll();
        List<CategoryWithContentDTO> categoryWithContents = ObjectMapperUtils.mapAll(allCategories, CategoryWithContentDTO.class);
        return new BaseResponse<>(HttpStatus.OK, categoryWithContents);
    }
}
