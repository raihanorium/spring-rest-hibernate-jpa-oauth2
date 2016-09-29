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
 * This model holds the areas. Areas are dependent on {@link com.bitmakersbd.biyebari.server.model.District}.
 * A district can have many areas. Areas are used in {@link com.bitmakersbd.biyebari.server.model.Vendor} to
 * indicate the service areas of a vendor. A vendor can have multiple service areas and an area can have
 * multiple vendors.
 *
 * @see com.bitmakersbd.biyebari.server.model.District
 * @see com.bitmakersbd.biyebari.server.model.Vendor
 */
@Entity
@Table(name = "tbl_area")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Column(name = "a_name", length = 255)
    private String name;

    @NotNull(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Column(name = "d_id")
    private Long districtId;

    @JsonIgnore
    @ManyToMany(mappedBy = "areas", fetch = FetchType.EAGER)
    private List<Vendor> vendors = new ArrayList<>();

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

    public Area() {
    }

    public Area(Long id) {
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

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public State getIsActive() {
        return isActive;
    }

    public List<Vendor> getVendors() {
        return vendors;
    }

    public void setVendors(List<Vendor> vendors) {
        this.vendors = vendors;
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
    }
}
