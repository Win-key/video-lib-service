package com.video.lib.service;

import com.video.lib.dto.BaseResponse;
import com.video.lib.dto.PlaylistDTO;
import com.video.lib.model.ContentEntity;
import com.video.lib.model.PlaylistEntity;
import com.video.lib.repository.ContentRepository;
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

    @Autowired
    private ContentRepository contentRepository;

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
}
