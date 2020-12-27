package com.video.lib.repository;

import com.video.lib.dto.ReviewUserDto;
import com.video.lib.model.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContentRepository extends JpaRepository<ContentEntity, Integer> {

    Optional<ContentEntity> findByContentID(String contentID);

    @Query(nativeQuery = true, value = "SELECT ut.first_name firstName, ut.last_name lastName, ut.user_name username, rw.rating rating, rw.review_comment reviewComment, cnt.content_id contentId " +
                                        "from user_table ut " +
                                        "join review_table rw on ut.user_name = rw.user_name " +
                                        "join content_table cnt on cnt.content_id = rw.content_id " +
                                        "and cnt.content_id = :contentID")
    List<ReviewUserDto> findReviewAndUserByContentID(@Param("contentID") String contentID);

    @Query(nativeQuery = true, value = "SELECT sum (rw.rating)/count(*) rating from review_table rw join content_table cnt \n" +
                                        "on cnt.content_id = rw.content_id and cnt.content_id = :contentID")
    Integer avgRating(String contentID);
}
