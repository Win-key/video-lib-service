package com.video.lib.service;

import com.video.lib.dto.BaseResponse;
import com.video.lib.dto.ContentDTO;
import com.video.lib.dto.ContentPlaylistDto;
import com.video.lib.dto.PlaylistDTO;
import com.video.lib.dto.ReviewDTO;
import com.video.lib.dto.ReviewUserDto;
import com.video.lib.model.CategoryEntity;
import com.video.lib.model.ContentEntity;
import com.video.lib.model.DurationEntity;
import com.video.lib.model.PlaylistEntity;
import com.video.lib.model.Rating;
import com.video.lib.model.ReviewEntity;
import com.video.lib.model.UserEntity;
import com.video.lib.repository.CategoryRepository;
import com.video.lib.repository.ContentRepository;
import com.video.lib.repository.DurationRepository;
import com.video.lib.repository.PlaylistRepository;
import com.video.lib.repository.ReviewRepository;
import com.video.lib.utils.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private PlaylistRepository playlistRepository;
    private CategoryRepository categoryRepository;
    private DurationRepository durationRepository;

    @Autowired
    public ContentService(ContentRepository contentRepository, AuthService authService,
                          ReviewRepository reviewRepository, PlaylistRepository playlistRepository,
                          CategoryRepository categoryRepository, DurationRepository durationRepository) {
        this.contentRepository = contentRepository;
        this.authService = authService;
        this.reviewRepository = reviewRepository;
        this.playlistRepository = playlistRepository;
        this.categoryRepository = categoryRepository;
        this.durationRepository = durationRepository;
    }

    public BaseResponse<ContentPlaylistDto> getContentPlaylist(String contentID, String username) {
        Optional<ContentEntity> contentOptional = contentRepository.findByContentID(contentID);
        if (contentOptional.isEmpty()){
            return new BaseResponse<>(HttpStatus.NO_CONTENT, new ContentPlaylistDto());
        }
        try {
            ContentEntity contentEntity = contentOptional.get();
            ContentPlaylistDto contentPlaylistDto = ObjectMapperUtils.map(contentEntity, ContentPlaylistDto.class);
            getPlaylistDuration(contentPlaylistDto, contentEntity, username);
            return new BaseResponse<>(HttpStatus.OK, contentPlaylistDto);
        }catch (Exception e){
            log.error("Unable to load the playlist", e);
        }

        return new BaseResponse<>(HttpStatus.EXPECTATION_FAILED, new ContentPlaylistDto());
    }

    private void getPlaylistDuration(ContentPlaylistDto contentPlaylistDto, ContentEntity contentEntity, String username) {
        Optional<UserEntity> userEntity = authService.userByUsername(username);
        if(userEntity.isEmpty()){
            return;
        }
        Map<String, PlaylistDTO> videoMap = new HashMap<>();
        contentPlaylistDto.getPlaylists().forEach(playlistDTO -> videoMap.put(playlistDTO.getVideoId(), playlistDTO));
        contentEntity.getPlaylists().forEach(video-> videoMap.get(video.getVideoId()).setStartTime(getPlaylistDuration(video,userEntity.get())));
    }

    private Long getPlaylistDuration(PlaylistEntity video, UserEntity user) {
        Optional<DurationEntity> durationOpt = durationRepository.findByVideoAndUser(video, user);
        if(durationOpt.isEmpty()){
            return 0L;
        }
        return durationOpt.get().getDuration();
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

    @Transactional
    public BaseResponse<String> postContentData(ContentPlaylistDto contentPlaylist) {
        ContentEntity contentEntity = ObjectMapperUtils.map(contentPlaylist, ContentEntity.class);
        Optional<ContentEntity> contentOpt = contentRepository.findByContentID(contentPlaylist.getContentID());
        if(contentOpt.isPresent()){
            ContentEntity contentEditable = contentOpt.get();
            contentEntity.setId(contentEditable.getId());
            contentEntity.setReviewEntities(contentEditable.getReviewEntities());
            contentEntity.setCategoryEntity(contentEditable.getCategoryEntity());

            playlistRepository.deleteByContentEntity(contentEditable);
        }else{
            Optional<CategoryEntity> categoryOpt = categoryRepository.findByCategoryId(contentPlaylist.getCategoryID());
            if(categoryOpt.isPresent()){
                contentEntity.setCategoryEntity(categoryOpt.get());
            }else
                return new BaseResponse<>(HttpStatus.BAD_REQUEST, "It's an Content! Please add it to a content");
        }

        contentEntity.getPlaylists().forEach(playlist->playlist.setContentEntity(contentEntity));

        try {
            contentRepository.save(contentEntity);
        }catch (Exception e){
            log.error("Exception: Unable to store the content for give data content ID {}",contentPlaylist.getContentID(),e);
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Unable to store the data");
        }
        return new BaseResponse<>(HttpStatus.OK, "Successfully store the content data");
    }

    public BaseResponse<String> postVideoDuration(String videoID, Long duration, String username) {
        Optional<PlaylistEntity> playlistOpt =playlistRepository.findByVideoId(videoID);
        if(playlistOpt.isEmpty()){
            return new BaseResponse<>(HttpStatus.NOT_FOUND, String.format("Playlist with given id %s is not found!",videoID));
        }

        Optional<UserEntity> userOpt = authService.userByUsername(username);
        if(userOpt.isEmpty()){
            return new BaseResponse<>(HttpStatus.NOT_FOUND, String.format("User with given id %s is not found!",username));
        }

        Optional<DurationEntity> durationOpt = durationRepository.findByVideoAndUser(playlistOpt.get(), userOpt.get());

        DurationEntity durationEntity;
        if(durationOpt.isPresent()){
            durationEntity = durationOpt.get();
            durationEntity.setDuration(duration);
        }else{
            durationEntity = new DurationEntity(duration, playlistOpt.get(), userOpt.get());
        }

        try {
            durationRepository.save(durationEntity);
        }catch (Exception e){
            log.error("Unable to store the duration for the video id {}",videoID, e);
            return new BaseResponse<>(HttpStatus.EXPECTATION_FAILED, String.format("Playlist with given id %s is not found!",videoID));
        }
        return new BaseResponse<>(HttpStatus.OK, "Successfully added the duration");
    }
}
