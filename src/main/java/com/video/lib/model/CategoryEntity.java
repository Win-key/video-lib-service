package com.video.lib.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "category_table")
public class CategoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true, nullable = false)
    private Integer id;

    @Column(name = "category_name", nullable = false)
    private String category;
    @Column(name = "category_display_name", nullable = false)
    private String categoryDisplayName;
    @Column(name = "category_id", nullable = false, unique = true)
    private String categoryId;

    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.ALL)
    private List<ContentEntity> contentList;

    public void setContentList(List<ContentEntity> contentList) {
        this.contentList.clear();
        this.contentList.addAll(contentList);
    }


}
