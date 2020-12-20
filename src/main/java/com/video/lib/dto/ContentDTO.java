package com.video.lib.dto;

import lombok.Data;

/**
 * @author Venkatesh Rajendran
 *
 */

@Data
public class ContentDTO {

    private String contentID;

    private String contentDisplayName;

    private String contentDescription;

    private String contentThumbnail;

    private String duration;

    private String pricing;

}
