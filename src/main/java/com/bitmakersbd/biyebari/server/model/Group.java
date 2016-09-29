package com.bitmakersbd.biyebari.server.model;

import com.bitmakersbd.biyebari.server.util.State;
import com.bitmakersbd.biyebari.server.validation.ValidateOnCreate;
import com.bitmakersbd.biyebari.server.validation.ValidateOnUpdate;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Groups has no usage at this moment. It can be used in future to group the
 * {@link com.bitmakersbd.biyebari.server.model.Category} or {@link com.bitmakersbd.biyebari.server.model.Item}.
 */
@Entity
@Table(name = "tbl_group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Column(length = 200)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "is_active", columnDefinition = "enum('active', 'inactive')")
    @Enumerated(EnumType.STRING)
    private State isActive;

    private Long parent;

    @NotNull(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Column(name = "c_order")
    private Integer cOrder;

    @NotNull(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Column(name = "default_image")
    private String defaultImage;

    @Column(name = "info_format", columnDefinition = "text")
    private String infoFormat;

    @Column(name = "is_budget", columnDefinition = "enum('active', 'inactive')")
    @Enumerated(EnumType.STRING)
    private State isBudget;

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

    public Group() {
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

    public String getInfoFormat() {
        return infoFormat;
    }

    public void setInfoFormat(String infoFormat) {
        this.infoFormat = infoFormat;
    }

    public State getIsBudget() {
        return isBudget;
    }

    public void setIsBudget(State isBudget) {
        this.isBudget = isBudget;
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
    }
}
