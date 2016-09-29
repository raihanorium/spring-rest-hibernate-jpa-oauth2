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
 * Units are the measurement unit of the price of an {@link com.bitmakersbd.biyebari.server.model.Item}.
 * An item can have an unit of price and units can be associated with multiple items.
 *
 * @see com.bitmakersbd.biyebari.server.model.Item
 */
@Entity
@Table(name = "tbl_unit")
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Column(length = 20)
    private String name;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.DETACH, orphanRemoval = false, mappedBy = "unit", targetEntity = Item.class, fetch = FetchType.EAGER)
    private List<Item> items = new ArrayList<>();

    @Column(name = "is_active", columnDefinition = "enum('active', 'inactive')")
    @Enumerated(EnumType.STRING)
    private State isActive;

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

    public Unit() {
    }

    public Unit(Long id) {
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
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
