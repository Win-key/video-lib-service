package com.video.lib.controller;

import com.video.lib.dto.BaseResponse;
import com.video.lib.dto.ContentDTO;
import com.video.lib.dto.ContentPlaylistDto;
import com.video.lib.dto.PlaylistDTO;
import com.video.lib.dto.ReviewDTO;
import com.video.lib.model.ContentEntity;
import com.video.lib.security.UserPrincipal;
import com.video.lib.service.ContentService;
import com.video.lib.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    public ResponseEntity<BaseResponse> postContentPlaylist(@NonNull @RequestBody ContentPlaylistDto contentPlaylist){
        BaseResponse<String> response = contentService.postContentData(contentPlaylist);
        return response.asResponseEntity();
    }

    @GetMapping(value = "/{contentID}")
    public ResponseEntity<BaseResponse> getContentPlaylist(@NonNull @PathVariable("contentID") String contentID, @AuthenticationPrincipal UserPrincipal userPrincipal){
        BaseResponse<ContentPlaylistDto> response = contentService.getContentPlaylist(contentID, userPrincipal.getUsername());
        return response.asResponseEntity();
    }

    @PutMapping(value = "/video/{videoID}/{duration}")
    public ResponseEntity<BaseResponse> postVideoDuration(@PathVariable("videoID") String videoID,@PathVariable("duration") Long duration,
                                                          @AuthenticationPrincipal UserPrincipal userPrincipal){
        BaseResponse<String> response = contentService.postVideoDuration(videoID, duration, "winkey");
        return response.asResponseEntity();
    }

    @PostMapping(value = "/{contentID}/review")
    public ResponseEntity<BaseResponse> submitReview(@PathVariable("contentID") String contentID,
                                                     @AuthenticationPrincipal UserPrincipal userPrincipal,
                                                     @RequestBody ReviewDTO reviewDTO){
        BaseResponse<String> response = contentService.submitReview(contentID, userPrincipal.getUsername() ,reviewDTO);
        return response.asResponseEntity();
    }

    @GetMapping(value = "/{contentID}/review")
    public ResponseEntity<BaseResponse> userReview(@PathVariable("contentID") String contentID,
                                                     @AuthenticationPrincipal UserPrincipal userPrincipal){
        BaseResponse<Object> response = contentService.userReview(contentID, userPrincipal.getUsername());
        return response.asResponseEntity();
    }


    @GetMapping(value = "/{contentID}/review/all")
    public ResponseEntity<BaseResponse> allContentReview(@PathVariable("contentID") String contentID){
        BaseResponse<Object> response = contentService.allReview(contentID);
        return response.asResponseEntity();
    }

    @GetMapping(value = "/{contentID}/avg")
    public ResponseEntity<BaseResponse> avgContentRating(@PathVariable("contentID") String contentID){
        BaseResponse<Object> response = contentService.avgRating(contentID);
        return response.asResponseEntity();
    }

}
