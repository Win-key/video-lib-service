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
import java.util.List;

/**
 * @author Venkatesh Rajendran
 *
 */

@Data
@Entity
@Table(name = "content_table")
public class ContentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true, nullable = false)
    private Integer id;

    @Column(name = "content_id", unique = true, nullable = false)
    private String contentID;

    @Column(name = "content_display_name", nullable = false)
    private String contentDisplayName;

    @Column(name = "content_description")
    private String contentDescription;

    @Column(name = "content_thumbnail", nullable = false)
    private String contentThumbnail;

    @Column(name = "duration", nullable = false)
    private String duration;

    @Column(name = "pricing", nullable = false)
    private String pricing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false)
    private CategoryEntity categoryEntity;

    @OneToMany(mappedBy = "contentEntity", cascade = CascadeType.ALL)
    private List<PlaylistEntity> playlists;

}
