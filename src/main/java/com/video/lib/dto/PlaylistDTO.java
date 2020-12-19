package com.video.lib.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author Venkatesh Rajendran
 * @vendor (Ideas2IT)
 */

@Data
public class PlaylistDTO {

    private Integer id;
    private Long startTime;
    private String title;
    private String src;
    private String type;
    private String thumbnail;
    private String description;
    private String duration;
    private String displayDuration;

}
