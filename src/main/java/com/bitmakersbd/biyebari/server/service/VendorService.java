package com.bitmakersbd.biyebari.server.service;

import com.bitmakersbd.biyebari.server.model.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface VendorService {
    Page getAll(Pageable pageable) throws Exception;

    Vendor get(Long id) throws Exception;

    Vendor create(Vendor vendor) throws Exception;

    void delete(Long id) throws Exception;

    Vendor update(Vendor vendor) throws Exception;

    Vendor login(String user, String password) throws Exception;

    boolean isDuplicateEmail(String email);

    boolean isDuplicateContactNo(String contactNo);

    Vendor updateLogo(Long id, MultipartFile file) throws Exception;

    Vendor updateAreas(Long vendorId, Set<Long> areaIds) throws Exception;

    Vendor updateCategories(Long vendorId, Set<Long> categoryIds) throws Exception;

    Page getAllItemsByVendor(Long vendorId, Pageable pageable) throws Exception;

    boolean changePassword(Long vendorId, String oldPassword, String newPassword) throws Exception;

    boolean sendVerificationCode(Long vendorId) throws Exception;

    Vendor verifyCode(Long vendorId, Integer verificationCode) throws Exception;
}
