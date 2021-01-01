package com.video.lib.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Venkatesh Rajendran
 *
 */

@Data
@Entity
@Table(name = "playlist_table")
public class PlaylistEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true, nullable = false)
    private Integer id;

    @Column(name = "video_id")
    private String videoId;

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
    @JoinColumn(name = "content_id", referencedColumnName = "content_id")
    private ContentEntity contentEntity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "playlistEntity", cascade = CascadeType.ALL)
    private List<DurationEntity> durationEntities;
}
