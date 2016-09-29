package com.bitmakersbd.biyebari.server.model;

import com.bitmakersbd.biyebari.server.service.ApplicationSettingsService;
import com.bitmakersbd.biyebari.server.service.ApplicationSettingsServiceImpl;
import com.bitmakersbd.biyebari.server.util.Messages;
import com.bitmakersbd.biyebari.server.util.SpringUtil;
import com.bitmakersbd.biyebari.server.util.Verified;
import com.bitmakersbd.biyebari.server.validation.ValidateOnCreate;
import com.bitmakersbd.biyebari.server.validation.ValidateOnUpdate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Vendors are the supplier company of {@link com.bitmakersbd.biyebari.server.model.Item}. Vendors can create
 * items with {@link com.bitmakersbd.biyebari.server.model.Category}. They provide services within
 * {@link com.bitmakersbd.biyebari.server.model.Area}.
 *
 * @see com.bitmakersbd.biyebari.server.model.Item
 * @see com.bitmakersbd.biyebari.server.model.Category
 * @see com.bitmakersbd.biyebari.server.model.Area
 */
@Entity
@Table(name = "tbl_vendor")
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Column(length = 200)
    private String name;

    @Column(length = 100)
    private String email;

    @NotNull(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Column(name = "role_id", nullable = false)
    private Integer roleId;

    @NotEmpty(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Column(name = "contact_no", length = 100)
    private String contactNo;

    @NotEmpty(groups = ValidateOnCreate.class)
    @Column(nullable = false)
    private String password;

    @Column(name = "remember_token", length = 100)
    private String rememberToken;

    @Column(columnDefinition = "enum('yes','no','cancelled')")
    @Enumerated(EnumType.STRING)
    private Verified verified;

    @Column(name = "company_id", length = 50)
    private String companyId;

    @Column(columnDefinition = "text")
    private String about;

    @Column(name = "company_logo", columnDefinition = "text")
    private String companyLogo;

    @Column(name = "last_login_ip", length = 100)
    private String lastLoginIp;

    @Column(name = "signup_ip_address", length = 100)
    private String signupIpAddress;

    @Column(length = 200)
    private String position;

    @NotNull(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Column(columnDefinition = "text")
    private String location;

    @Column(columnDefinition = "text")
    private String website;

    @Column(name = "fb_url", columnDefinition = "text")
    private String fbUrl;

    @Column(name = "google_url", columnDefinition = "text")
    private String googleUrl;

    @Column(name = "twitter_url", columnDefinition = "text")
    private String twitterUrl;

    @Column(name = "linkedin_url", columnDefinition = "text")
    private String linkedinUrl;

    @JsonIgnore
    @Column(name = "verification_code")
    private Integer verificationCode;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "tbl_area_vendor", joinColumns = {@JoinColumn(name = "vendor_id")}, inverseJoinColumns = {@JoinColumn(name = "area_id")})
    private Set<Area> areas = new HashSet<>();

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "tbl_category_vendor", joinColumns = {@JoinColumn(name = "vendor_id")}, inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private Set<Category> categories = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", columnDefinition = "timestamp", nullable = true, updatable = false)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", columnDefinition = "timestamp", nullable = true, insertable = false)
    private Date updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "createdBy")
    private Set<Item> items = new HashSet<>();

    public Vendor() {
    }

    public Vendor(Long id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(String rememberToken) {
        this.rememberToken = rememberToken;
    }

    public Verified getVerified() {
        return verified;
    }

    public void setVerified(Verified verified) {
        this.verified = verified;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getSignupIpAddress() {
        return signupIpAddress;
    }

    public void setSignupIpAddress(String signupIpAddress) {
        this.signupIpAddress = signupIpAddress;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFbUrl() {
        return fbUrl;
    }

    public void setFbUrl(String fbUrl) {
        this.fbUrl = fbUrl;
    }

    public String getGoogleUrl() {
        return googleUrl;
    }

    public void setGoogleUrl(String googleUrl) {
        this.googleUrl = googleUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public Integer getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(Integer verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Set<Area> getAreas() {
        return areas;
    }

    public void setAreas(Set<Area> areas) {
        this.areas = areas;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    @PostLoad
    private void onLoad() throws Exception {
        if (!StringUtils.isEmpty(this.companyLogo)) {
            ApplicationSettingsService applicationSettingsService = SpringUtil.bean(ApplicationSettingsServiceImpl.class);
            Messages messages = SpringUtil.bean(Messages.class);

            String fileName = this.companyLogo;
            String imageRelPath = applicationSettingsService.getByKey(messages.getMessage("vendor.logo.rel.path.dbkey")).getValue();

            this.companyLogo = imageRelPath + "/" + fileName;
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = new Date();

        if (StringUtils.isNotEmpty(this.companyLogo)) {
            String currentPath = this.companyLogo;
            this.companyLogo = currentPath.substring(currentPath.lastIndexOf('/') + 1, currentPath.length());
        }
    }
}
