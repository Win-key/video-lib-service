package com.video.lib.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Venkatesh Rajendran
 * @vendor (Ideas2IT)
 */

@Data
public class CategoryWithContentDTO {
    private String category;
    private String categoryDisplayName;
    private String categoryId;

    private List<ContentDTO> contentList;
}
