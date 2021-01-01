package com.video.lib.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Venkatesh Rajendran
 * @vendor (Ideas2IT)
 */

@Data
@Entity
@Table(name = "duration_table")
@NoArgsConstructor
public class DurationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true, nullable = false)
    private Integer id;

    @Column(name = "duration",unique = true, nullable = false)
    private Long duration;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "content_id", referencedColumnName = "content_id", nullable = false)
    private ContentEntity contentEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "video_id", referencedColumnName = "video_id", nullable = false)
    private PlaylistEntity playlistEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_name", referencedColumnName = "user_name", nullable = false)
    private UserEntity userEntity;

    public DurationEntity(Long duration, PlaylistEntity playlistEntity, UserEntity userEntity) {
        this.duration = duration;
        this.playlistEntity = playlistEntity;
        this.contentEntity = playlistEntity.getContentEntity();
        this.userEntity = userEntity;
    }
}
