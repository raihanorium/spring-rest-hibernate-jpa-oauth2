package com.bitmakersbd.biyebari.server.model;

import com.bitmakersbd.biyebari.server.util.State;
import com.bitmakersbd.biyebari.server.validation.ValidateOnCreate;
import com.bitmakersbd.biyebari.server.validation.ValidateOnUpdate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This model holds categories of {@link com.bitmakersbd.biyebari.server.model.Item}. An item can have a
 * category and categories inherit categories. {@link com.bitmakersbd.biyebari.server.model.Vendor} can
 * also define their choice of categories. A vendor can have multiple category and an item has a single
 * category.
 *
 * @see com.bitmakersbd.biyebari.server.model.Item
 * @see com.bitmakersbd.biyebari.server.model.Vendor
 */
@Entity
@Table(name = "tbl_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Column(length = 200, name = "category_name")
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "is_active", columnDefinition = "enum('active', 'inactive')")
    @Enumerated(EnumType.STRING)
    private State isActive;

    @Column(name = "group_id")
    private Long groupId;

    private Long parent;

    @Column(name = "c_order")
    private Integer cOrder;

    @Column(name = "default_image")
    private String defaultImage;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.DETACH, mappedBy = "categories", fetch = FetchType.EAGER)
    private List<Vendor> vendors = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", columnDefinition = "timestamp", nullable = true, updatable = false)
    private Date createdAt = new Date();

    @NotNull(groups = ValidateOnCreate.class)
    @Column(name = "created_by", columnDefinition = "varchar", length = 50)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", columnDefinition = "timestamp", nullable = true, insertable = false)
    private Date updatedAt;

    @NotNull(groups = ValidateOnUpdate.class)
    @Column(name = "updated_by", columnDefinition = "varchar", length = 50)
    private Long updatedBy;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.DETACH, orphanRemoval = false, mappedBy = "category", targetEntity = Item.class, fetch = FetchType.EAGER)
    private List<Item> items = new ArrayList<>();

    public Category() {
    }

    public Category(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public State getIsActive() {
        return isActive;
    }

    public void setIsActive(State isActive) {
        this.isActive = isActive;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public Integer getcOrder() {
        return cOrder;
    }

    public void setcOrder(Integer cOrder) {
        this.cOrder = cOrder;
    }

    public String getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public List<Vendor> getVendors() {
        return vendors;
    }

    public void setVendors(List<Vendor> vendors) {
        this.vendors = vendors;
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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = new Date();

        if (this.items != null && this.items.size() > 0) {
            for (Item item : this.items) {
                if (item.getImages() != null && item.getImages().size() > 0) {
                    for (ItemImage itemImage : item.getImages()) {
                        String currentPath = itemImage.getImageFileName();
                        String fileName = currentPath.substring(currentPath.lastIndexOf('/') + 1, currentPath.length());
                        itemImage.setImageFileName(fileName);
                    }
                }
            }
        }
    }
}
