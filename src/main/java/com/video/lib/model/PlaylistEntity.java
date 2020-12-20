package com.video.lib.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Venkatesh Rajendran
 * @vendor (Ideas2IT)
 */

@Data
@Entity
@Table(name = "playlist_table")
public class PlaylistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true, nullable = false)
    private Integer id;

    // Todo : Fix me this should be wrt User
    @Column(name = "start_time" )
    private Long startTime;

    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "src", nullable = false)
    private String src;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "duration", nullable = false)
    private String duration;

    // Todo : Fix me this should be wrt User
    @Column(name = "displayDuration")
    private String displayDuration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", referencedColumnName = "content_id", nullable = false)
    private ContentEntity contentEntity;

}
