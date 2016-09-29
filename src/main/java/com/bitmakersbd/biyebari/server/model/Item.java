package com.bitmakersbd.biyebari.server.model;

import com.bitmakersbd.biyebari.server.util.State;
import com.bitmakersbd.biyebari.server.validation.ValidateOnCreate;
import com.bitmakersbd.biyebari.server.validation.ValidateOnUpdate;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * This model holds product items. Products are created by {@link com.bitmakersbd.biyebari.server.model.Vendor}.
 * A vendor can create multiple items. An item has a {@link com.bitmakersbd.biyebari.server.model.Category}
 * associated with it. Item prices are tagged with {@link com.bitmakersbd.biyebari.server.model.Unit}.
 *
 * An item can have multiple {@link com.bitmakersbd.biyebari.server.model.ItemImage}.
 *
 * @see com.bitmakersbd.biyebari.server.model.Vendor
 * @see com.bitmakersbd.biyebari.server.model.Category
 * @see com.bitmakersbd.biyebari.server.model.Unit
 * @see com.bitmakersbd.biyebari.server.model.ItemImage
 */
@Entity
@Table(name = "tbl_item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Column(length = 20)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    private Integer stock;

    @Valid
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "item", targetEntity = ItemImage.class, fetch = FetchType.EAGER)
    private Set<ItemImage> images = new HashSet<>();

    @Column(name = "is_active", columnDefinition = "enum('active', 'inactive', 'deleted')")
    @Enumerated(EnumType.STRING)
    private State isActive;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", columnDefinition = "timestamp", nullable = true, updatable = false)
    private Date createdAt = new Date();

    @NotNull(groups = ValidateOnCreate.class)
    @ManyToOne
    @JoinColumn(name = "created_by")
    private Vendor createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", columnDefinition = "timestamp", nullable = true, insertable = false)
    private Date updatedAt;

    @NotNull(groups = ValidateOnUpdate.class)
    @Column(name = "updated_by", columnDefinition = "varchar", length = 50)
    private Long updatedBy;

    public Item() {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Set<ItemImage> getImages() {
        return images;
    }

    public void setImages(Set<ItemImage> images) {
        this.images.clear();
        if(images != null){
            this.images.addAll(images);
        }
    }

    public State getIsActive() {
        return isActive;
    }

    public void setIsActive(State isActive) {
        this.isActive = isActive;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Vendor getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Vendor createdBy) {
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

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = new Date();

        if (this.images != null && this.images.size() > 0) {
            for (ItemImage itemImage : this.images) {
                String currentPath = itemImage.getImageFileName();
                String fileName = currentPath.substring(currentPath.lastIndexOf('/') + 1, currentPath.length());
                itemImage.setImageFileName(fileName);
            }
        }
    }
}
