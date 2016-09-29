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

/**
 * Users are the user of the site.
 */
@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 100)
    private String email;

    @Column(name = "user_role", length = 10)
    private int userRole;

    @Column(name = "full_name", length = 200)
    private String fullName;

    @NotEmpty(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Column(name = "first_name", length = 100, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @NotEmpty(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Column(name = "contact_no", length = 100)
    private String contactNo;

    @NotNull(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Column(name = "nid_or_passport_no", length = 50, nullable = false)
    private String nidOrPassportNo;

    @NotEmpty(groups = ValidateOnCreate.class)
    @Column(nullable = false)
    private String password;

    @Column(name = "remember_token", length = 100)
    private String rememberToken;

    @Column(columnDefinition = "enum('yes','no')")
    @Enumerated(EnumType.STRING)
    private Verified verified;

    @Column(name = "company_id", length = 50)
    private String companyId;

    @Column(columnDefinition = "text")
    private String about;

    @Column(name = "profile_pic", columnDefinition = "text")
    private String profilePic;

    @Column(name = "last_login_ip", length = 100)
    private String lastLoginIp;

    @Column(name = "signup_ip_address", length = 100)
    private String signupIpAddress;

    @Column(length = 200)
    private String position;

    @NotNull(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Column(columnDefinition = "text")
    private String location;

    @NotNull(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Column(columnDefinition = "text")
    private String website;

    @NotNull(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Column(name = "fb_url", columnDefinition = "text")
    private String fbUrl;

    @NotNull(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Column(name = "google_url", columnDefinition = "text")
    private String googleUrl;

    @NotNull(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Column(name = "twitter_url", columnDefinition = "text")
    private String twitterUrl;

    @NotNull(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Column(name = "linkedin_url", columnDefinition = "text")
    private String linkedinUrl;

    @JsonIgnore
    @Column(name = "verification_code")
    private Integer verificationCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", columnDefinition = "timestamp", nullable = true, updatable = false)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", columnDefinition = "timestamp", nullable = true, insertable = false)
    private Date updatedAt;

    public User() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getNidOrPassportNo() {
        return nidOrPassportNo;
    }

    public void setNidOrPassportNo(String nidOrPassportNo) {
        this.nidOrPassportNo = nidOrPassportNo;
    }

    @JsonIgnore
    public String getPassword() {
        return this.password;
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

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @PostLoad
    private void onLoad() throws Exception {
        if (!StringUtils.isEmpty(this.profilePic)) {
            ApplicationSettingsService applicationSettingsService = SpringUtil.bean(ApplicationSettingsServiceImpl.class);
            Messages messages = SpringUtil.bean(Messages.class);

            String fileName = this.profilePic;
            String imageRelPath = applicationSettingsService.getByKey(messages.getMessage("user.pic.rel.path.dbkey")).getValue();

            this.profilePic = imageRelPath + "/" + fileName;
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = new Date();

        if (StringUtils.isNotEmpty(this.profilePic)) {
            String currentPath = this.profilePic;
            this.profilePic = currentPath.substring(currentPath.lastIndexOf('/') + 1, currentPath.length());
        }
    }
}
