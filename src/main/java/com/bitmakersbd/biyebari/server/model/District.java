package com.bitmakersbd.biyebari.server.model;

import com.bitmakersbd.biyebari.server.util.State;
import com.bitmakersbd.biyebari.server.validation.ValidateOnCreate;
import com.bitmakersbd.biyebari.server.validation.ValidateOnUpdate;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * This model holds districts. Districts are used to hold {@link com.bitmakersbd.biyebari.server.model.Area}.
 * Vendors are tagged with areas.
 *
 * @see com.bitmakersbd.biyebari.server.model.Area
 * @see com.bitmakersbd.biyebari.server.model.Vendor
 */
@Entity
@Table(name = "tbl_district")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Column(name = "d_name", length = 100)
    private String name;

    @Column(name = "is_active", columnDefinition = "enum('active', 'inactive')")
    @Enumerated(EnumType.STRING)
    private State isActive;

    public District() {
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

    public State getIsActive() {
        return isActive;
    }

    public void setIsActive(State isActive) {
        this.isActive = isActive;
    }
}
