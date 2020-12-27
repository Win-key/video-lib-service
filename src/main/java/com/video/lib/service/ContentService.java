package com.video.lib.service;

import com.video.lib.dto.BaseResponse;
import com.video.lib.dto.PlaylistDTO;
import com.video.lib.dto.ReviewDTO;
import com.video.lib.dto.ReviewUserDto;
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
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
        ReviewEntity reviewEntity;
        try {
            reviewEntity = getReviewEntity(contentID, username);
        }catch (EntityNotFoundException e){
            log.error("Some error", e);
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, Objects.nonNull(e.getMessage()) ? e.getMessage() : "Exception occured. Please try later.");
        }

        reviewEntity.setRating(Rating.of(reviewDTO.getRating()));
        reviewEntity.setReviewComment(reviewDTO.getReviewComment());
        /*reviewEntity.setContentEntity(contentEntity.get());
        reviewEntity.setUserEntity(userEntity.get());*/
        try {
            reviewRepository.save(reviewEntity);
        }catch (Exception e){
            log.error("Unable to submit the review.", e);
            return new BaseResponse<>(HttpStatus.EXPECTATION_FAILED, "Unable to submit the review. Please try later!");
        }
        return new BaseResponse<>(HttpStatus.CREATED, "Successfully submitted the review!");
    }

    public BaseResponse<Object> userReview(String contentID, String username) {
        ReviewEntity reviewEntity;
        try {
            reviewEntity = getReviewEntity(contentID, username);
        }catch (EntityNotFoundException e){
            log.error("Some error", e);
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, Objects.nonNull(e.getMessage()) ? e.getMessage() : "Exception occured. Please try later.");
        }

        ReviewDTO reviewDTO = mapReviewDTO(reviewEntity);
        return new BaseResponse<>(HttpStatus.OK, reviewDTO);
    }

    private ReviewDTO mapReviewDTO(ReviewEntity reviewEntity) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setRating(Objects.nonNull(reviewEntity.getRating()) ? reviewEntity.getRating().getValue() : null);
        reviewDTO.setReviewComment(reviewEntity.getReviewComment());
        return reviewDTO;
    }

    private ReviewEntity getReviewEntity(String contentID,String username) throws EntityNotFoundException {
        Optional<UserEntity> userEntity = authService.userByUsername(username);
        if(userEntity.isEmpty())
            throw new EntityNotFoundException("User doesn't found! Please try later.");
        Optional<ContentEntity> contentEntity = contentRepository.findByContentID(contentID);
        if(contentEntity.isEmpty())
            throw new EntityNotFoundException("Content doesn't found! Please try later.");

        Optional<ReviewEntity> reviewOptional = reviewRepository.findByUserAndContent(userEntity.get(), contentEntity.get());
        ReviewEntity reviewEntity;
        if(reviewOptional.isEmpty())
            reviewEntity = new ReviewEntity();
        else
            reviewEntity = reviewOptional.get();

        reviewEntity.setUserEntity(userEntity.get());
        reviewEntity.setContentEntity(contentEntity.get());
        return reviewEntity;
    }

    public BaseResponse<Object> allReview(String contentID) {
        List<ReviewUserDto> contentUserDto = contentRepository.findReviewAndUserByContentID(contentID);
        if(Objects.isNull(contentUserDto) || contentUserDto.isEmpty())
            return new BaseResponse<>(HttpStatus.NOT_FOUND,"Content doesn't found! Please try later.");

        return new BaseResponse<>(HttpStatus.OK, contentUserDto);
    }

    public BaseResponse<Object> avgRating(String contentID){
        Integer avgRating = contentRepository.avgRating(contentID);
        if(Objects.isNull(avgRating))
            return new BaseResponse<>(HttpStatus.OK, 0);

        return new BaseResponse<>(HttpStatus.OK, avgRating);
    }
}
