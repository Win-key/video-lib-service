package com.video.lib.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Venkatesh Rajendran
 * @vendor (Ideas2IT)
 */

@Data
public class ContentPlaylistDto {
    private String contentID;

    private String contentDisplayName;

    private String contentDescription;

    private String contentThumbnail;

    private String duration;

    private String pricing;

    private String overview;

    List<PlaylistDTO> playlists;

    private String categoryID;
}
