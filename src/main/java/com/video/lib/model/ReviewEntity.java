package com.video.lib.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Venkatesh Rajendran
 *
 */

@Data
@Entity
@Table(name = "review_table")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true, nullable = false)
    private Integer id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "rating", nullable = false)
    private Rating rating;

    @Column(name = "review_comment")
    private String reviewComment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_name", referencedColumnName = "user_name", nullable = false)
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "content_id", referencedColumnName = "content_id", nullable = false)
    private ContentEntity contentEntity;

}
