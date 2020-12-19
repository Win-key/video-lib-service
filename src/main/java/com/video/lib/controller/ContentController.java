package com.video.lib.controller;

import com.video.lib.dto.BaseResponse;
import com.video.lib.dto.PlaylistDTO;
import com.video.lib.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Venkatesh Rajendran
 *
 */

@RestController
@RequestMapping(value = "/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @GetMapping(value = "/{contentID}")
    public ResponseEntity<BaseResponse> getContentPlaylist(@NonNull @PathVariable("contentID") String contentID){
        BaseResponse<List<PlaylistDTO>> response = contentService.getContentPlaylist(contentID);
        return response.asResponseEntity();
    }

}
