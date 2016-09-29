package com.bitmakersbd.biyebari.server.model;

import com.bitmakersbd.biyebari.server.service.ApplicationSettingsService;
import com.bitmakersbd.biyebari.server.service.ApplicationSettingsServiceImpl;
import com.bitmakersbd.biyebari.server.util.Messages;
import com.bitmakersbd.biyebari.server.util.SpringUtil;
import com.bitmakersbd.biyebari.server.validation.ValidateOnCreate;
import com.bitmakersbd.biyebari.server.validation.ValidateOnUpdate;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * This is the model to hold images for {@link com.bitmakersbd.biyebari.server.model.Item}. An item can have
 * multiple images. Image files are uploaded in a physical location. The location to upload images are configured
 * in database and managed by {@link com.bitmakersbd.biyebari.server.model.ApplicationSettings} model.
 *
 * @see com.bitmakersbd.biyebari.server.model.Item
 * @see com.bitmakersbd.biyebari.server.model.ApplicationSettings
 */
@Entity
@Table(name = "tbl_item_images")
public class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @JsonBackReference
    private Item item;

    @NotNull(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Column(name = "image_file_name", length = 100)
    private String imageFileName;

    @Transient
    private String thumb;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", columnDefinition = "timestamp", nullable = true, updatable = false)
    private Date createdAt = new Date();

    @NotNull(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Column(name = "created_by", columnDefinition = "varchar", length = 50)
    private Long createdBy;

    public ItemImage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item itemId) {
        this.item = itemId;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getThumb() throws Exception {
        return this.thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    @PostLoad
    private void onLoad() throws Exception {
        ApplicationSettingsService applicationSettingsService = SpringUtil.bean(ApplicationSettingsServiceImpl.class);
        Messages messages = SpringUtil.bean(Messages.class);

        String fileName = this.imageFileName;
        String imageRelPath = applicationSettingsService.getByKey(messages.getMessage("item.image.rel.path.dbkey")).getValue();
        String thumbRelPath = applicationSettingsService.getByKey(messages.getMessage("item.image.thumb.rel.path.dbkey")).getValue();

        this.imageFileName = imageRelPath + "/" + this.item.getId() + "/" + fileName;
        this.thumb = thumbRelPath + "/" + this.item.getId() + "/" + fileName;
    }

    @PreUpdate
    private void onSave() {
        String currentPath = this.imageFileName;
        this.imageFileName = currentPath.substring(currentPath.lastIndexOf('/') + 1, currentPath.length());
    }
}
