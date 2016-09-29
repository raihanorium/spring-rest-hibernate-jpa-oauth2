package com.bitmakersbd.biyebari.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This model holds the application level settings stored in the database. In the database, these settings are
 * stored a key-value pairs. The {@link com.bitmakersbd.biyebari.server.model.ApplicationSettings#name} field
 * holds the key and the {@link com.bitmakersbd.biyebari.server.model.ApplicationSettings#value} field holds
 * the value of the setting.
 */
@Entity
@Table(name = "applicationsettings")
public class ApplicationSettings {
    @Id
    @Column(name = "pname", length = 100, nullable = false)
    private String name;

    @Column(name = "pvalue", nullable = false)
    private String value;

    public ApplicationSettings() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
