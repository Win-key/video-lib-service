package com.video.lib.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Venkatesh Rajendran
 *
 */

public interface ReviewUserDto {
    String getFirstName();
    String getLastName();
    String getUsername();
    Integer getRating();
    String getReviewComment();
    String getContentId();
}
