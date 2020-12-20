package com.video.lib.service;

import com.video.lib.dto.BaseResponse;
import com.video.lib.dto.PlaylistDTO;
import com.video.lib.dto.ReviewDTO;
import com.video.lib.model.ContentEntity;
import com.video.lib.model.PlaylistEntity;
import com.video.lib.model.Rating;
import com.video.lib.model.ReviewEntity;
import com.video.lib.model.UserEntity;
import com.video.lib.repository.ContentRepository;
import com.video.lib.repository.ReviewRepository;
import com.video.lib.utils.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Venkatesh Rajendran
 *
 */

@Slf4j
@Service
public class ContentService {

    private ContentRepository contentRepository;
    private AuthService authService;
    private ReviewRepository reviewRepository;

    @Autowired
    public ContentService(ContentRepository contentRepository, AuthService authService, ReviewRepository reviewRepository) {
        this.contentRepository = contentRepository;
        this.authService = authService;
        this.reviewRepository = reviewRepository;
    }

    public BaseResponse<List<PlaylistDTO>> getContentPlaylist(String contentID) {
        Optional<ContentEntity> contentOptional = contentRepository.findByContentID(contentID);
        if (contentOptional.isEmpty()){
            return new BaseResponse<>(HttpStatus.NO_CONTENT, Collections.emptyList());
        }
        try {
            List<PlaylistEntity> playlists = contentOptional.get().getPlaylists();
            return new BaseResponse<>(HttpStatus.OK, ObjectMapperUtils.mapAll(playlists, PlaylistDTO.class));
        }catch (Exception e){
            log.error("Unable to load the playlist", e);
        }

        return new BaseResponse<>(HttpStatus.EXPECTATION_FAILED, Collections.emptyList());
    }

    public BaseResponse<String> submitReview(String contentID,String username,  ReviewDTO reviewDTO) {
        Optional<UserEntity> userEntity = authService.userByUsername(username);
        if(userEntity.isEmpty())
            return new BaseResponse<>(HttpStatus.NOT_FOUND, "User not found. Please login again!");
        Optional<ContentEntity> contentEntity = contentRepository.findByContentID(contentID);
        if(contentEntity.isEmpty())
            return new BaseResponse<>(HttpStatus.NOT_FOUND, "Content not found! Please Contact admin.");

        Optional<ReviewEntity> reviewOptional = reviewRepository.findByUserAndContent(userEntity.get(), contentEntity.get());

        ReviewEntity reviewEntity;
        if(reviewOptional.isEmpty())
            reviewEntity = new ReviewEntity();
        else
            reviewEntity = reviewOptional.get();

        reviewEntity.setRating(Rating.of(reviewDTO.getRating()));
        reviewEntity.setReviewComment(reviewDTO.getReviewComment());
        reviewEntity.setContentEntity(contentEntity.get());
        reviewEntity.setUserEntity(userEntity.get());
        try {
            reviewRepository.save(reviewEntity);
        }catch (Exception e){
            log.error("Unable to submit the review.", e);
            return new BaseResponse<>(HttpStatus.EXPECTATION_FAILED, "Unable to submit the review. Please try later!");
        }
        return new BaseResponse<>(HttpStatus.CREATED, "Successfully submitted the review!");
    }
}
