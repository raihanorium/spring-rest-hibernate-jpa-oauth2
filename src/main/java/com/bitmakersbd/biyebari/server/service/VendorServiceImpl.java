package com.bitmakersbd.biyebari.server.service;

import com.bitmakersbd.biyebari.server.model.Area;
import com.bitmakersbd.biyebari.server.model.Category;
import com.bitmakersbd.biyebari.server.model.Vendor;
import com.bitmakersbd.biyebari.server.repository.AreaRepository;
import com.bitmakersbd.biyebari.server.repository.CategoryRepository;
import com.bitmakersbd.biyebari.server.repository.ItemRepository;
import com.bitmakersbd.biyebari.server.repository.VendorRepository;
import com.bitmakersbd.biyebari.server.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class VendorServiceImpl implements VendorService {
    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    AreaRepository areaRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    Messages messages;

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    SmsService smsService;

    @Override
    public Page getAll(Pageable pageable) throws Exception {
        return vendorRepository.findAll(pageable);
    }

    @Override
    public Vendor get(Long id) throws Exception {
        return vendorRepository.findOne(id);
    }

    @Transactional
    @Override
    public Vendor create(Vendor vendor) throws Exception {
        if (StringUtils.isNotEmpty(vendor.getEmail())) {
            if (isDuplicateEmail(vendor.getEmail())) {
                throw new Exception(messages.getMessage("email.already.registered"));
            }
        }
        if (isDuplicateContactNo(vendor.getContactNo())) {
            throw new Exception(messages.getMessage("contactNo.already.exists"));
        }

        // do not set logo when add or update. logo is added by upload method
        vendor.setCompanyLogo(null);
        vendor.setPassword(Md5Encryption.encrypt(vendor.getPassword()));
        addAreas(vendor);
        addCategories(vendor);
        return vendorRepository.save(vendor);
    }

    @Transactional
    @Override
    public void delete(Long id) throws Exception {
        Vendor vendorInDb = vendorRepository.findOne(id);
        if (vendorInDb == null) throw new Exception(messages.getMessage("vendor.not.found"));

        if (vendorInDb.getCompanyLogo() != null) {
            fileUploadService.deleteFile(UploadType.VENDOR_LOGO, vendorInDb.getCompanyLogo(), false, null);
        }

        vendorRepository.delete(id);
    }

    @Transactional
    @Override
    public Vendor update(Vendor vendor) throws Exception {
        Vendor vendorInDb = vendorRepository.findOne(vendor.getId());
        if (vendorInDb == null) throw new Exception(messages.getMessage("vendor.not.found"));

        // check for duplicate email.
        if (StringUtils.isNotEmpty(vendor.getEmail())) {
            if ((!vendor.getEmail().equals(vendorInDb.getEmail())) && isDuplicateEmail(vendor.getEmail())) {
                throw new Exception(messages.getMessage("email.already.registered"));
            }
        }

        // check for duplicate contact no.
        if ((!vendor.getContactNo().equals(vendorInDb.getContactNo())) && isDuplicateContactNo(vendor.getContactNo())) {
            throw new Exception(messages.getMessage("contactNo.already.exists"));
        }

        // update only fields which are allowed to update
        vendorInDb.setName(vendor.getName());
        vendorInDb.setEmail(vendor.getEmail());
        vendorInDb.setRoleId(vendor.getRoleId());
        vendorInDb.setContactNo(vendor.getContactNo());
        vendorInDb.setCompanyId(vendor.getCompanyId());
        vendorInDb.setAbout(vendor.getAbout());
        // do not set logo when add or update. logo is added by upload method
        vendorInDb.setPosition(vendor.getPosition());
        vendorInDb.setLocation(vendor.getLocation());
        vendorInDb.setWebsite(vendor.getWebsite());
        vendorInDb.setFbUrl(vendor.getFbUrl());
        vendorInDb.setGoogleUrl(vendor.getGoogleUrl());
        vendorInDb.setTwitterUrl(vendor.getTwitterUrl());
        vendorInDb.setLinkedinUrl(vendor.getLinkedinUrl());

        return vendorRepository.save(vendorInDb);
    }

    @Override
    public Vendor login(String user, String password) throws Exception {
        // user is the email or contact number of the user
        String encryptedPasswordGiven = Md5Encryption.encrypt(password);
        Vendor vendor;

        if (user.contains("@")) {
            vendor = vendorRepository.findOneByEmailAndPassword(user, encryptedPasswordGiven);
        } else {
            vendor = vendorRepository.findOneByContactNoAndPassword(user, encryptedPasswordGiven);
        }

        if (vendor == null) {
            throw new Exception(messages.getMessage("invalid.email.password"));
        }

        if (vendor.getVerified() != Verified.yes) {
            throw new Exception(messages.getMessage("vendor.not.verified"));
        }

        return vendor;
    }

    @Override
    public boolean isDuplicateEmail(String email) {
        return vendorRepository.findOneByEmail(email) != null;
    }

    @Override
    public boolean isDuplicateContactNo(String contactNo) {
        return vendorRepository.findOneByContactNo(contactNo) != null;
    }

    @Transactional
    @Override
    public Vendor updateLogo(Long id, MultipartFile file) throws Exception {
        Vendor vendorInDb = vendorRepository.findOne(id);
        if (vendorInDb == null) throw new Exception(messages.getMessage("vendor.not.found"));

        String fileName = fileUploadService.uploadFile(UploadType.VENDOR_LOGO, file, false, null);

        vendorInDb.setCompanyLogo(fileName);
        return vendorRepository.save(vendorInDb);
    }

    @Transactional
    @Override
    public Vendor updateAreas(Long vendorId, Set<Long> areaIds) throws Exception {
        Vendor vendorInDb = vendorRepository.findOne(vendorId);
        if (vendorInDb == null) throw new Exception(messages.getMessage("vendor.not.found"));

        Set<Area> areasToBeAdded = new HashSet<>();
        for (Long areaId : areaIds) {
            Area area = areaRepository.findOne(areaId);
            if (area == null)
                throw new Exception(MessageFormat.format(messages.getMessage("area.does.not.exist"), areaId));

            areasToBeAdded.add(area);
        }

        vendorInDb.setAreas(areasToBeAdded);
        return vendorRepository.save(vendorInDb);
    }

    @Transactional
    @Override
    public Vendor updateCategories(Long vendorId, Set<Long> categoryIds) throws Exception {
        Vendor vendorInDb = vendorRepository.findOne(vendorId);
        if (vendorInDb == null) throw new Exception(messages.getMessage("vendor.not.found"));

        Set<Category> categoriesToBeAdded = new HashSet<>();
        for (Long categoryId : categoryIds) {
            Category category = categoryRepository.findOne(categoryId);
            if (category == null)
                throw new Exception(MessageFormat.format(messages.getMessage("category.does.not.exist"), categoryId));

            categoriesToBeAdded.add(category);
        }

        vendorInDb.setCategories(categoriesToBeAdded);
        return vendorRepository.save(vendorInDb);
    }

    @Override
    public Page getAllItemsByVendor(Long vendorId, Pageable pageable) throws Exception {
        Vendor vendorInDb = vendorRepository.findOne(vendorId);
        if (vendorInDb == null) throw new Exception(messages.getMessage("vendor.not.found"));

        return itemRepository.findAllByCreatedByAndIsActiveIn(vendorInDb, Arrays.asList(State.active, State.inactive), pageable);
    }

    @Transactional
    @Override
    public boolean changePassword(Long vendorId, String oldPassword, String newPassword) throws Exception {
        oldPassword = Md5Encryption.encrypt(oldPassword);
        newPassword = Md5Encryption.encrypt(newPassword);

        Vendor vendorInDb = vendorRepository.findOneByIdAndPassword(vendorId, oldPassword);
        if (vendorInDb == null) throw new Exception(messages.getMessage("incorrect.old.password"));

        vendorInDb.setPassword(newPassword);
        vendorRepository.save(vendorInDb);

        return true;
    }

    private void addAreas(Vendor vendor) throws Exception {
        Set<Area> areaIds = vendor.getAreas();
        Set<Area> areasInDb = new HashSet<>();

        for (Area area : areaIds) {
            Area currentArea = areaRepository.findOne(area.getId());
            if (currentArea == null) {
                throw new Exception(MessageFormat.format(messages.getMessage("area.does.not.exist"), area.getId()));
            }

            areasInDb.add(currentArea);
        }

        vendor.getAreas().clear();
        vendor.setAreas(areasInDb);
    }

    private void addCategories(Vendor vendor) throws Exception {
        Set<Category> categoryIds = vendor.getCategories();
        Set<Category> categoriesInDb = new HashSet<>();

        for (Category category : categoryIds) {
            Category currentCategory = categoryRepository.findOne(category.getId());
            if (currentCategory == null) {
                throw new Exception(MessageFormat.format(messages.getMessage("category.does.not.exist"), category.getId()));
            }

            categoriesInDb.add(currentCategory);
        }

        vendor.getCategories().clear();
        vendor.setCategories(categoriesInDb);
    }

    @Transactional
    @Override
    public boolean sendVerificationCode(Long vendorId) throws Exception {
        Vendor vendorInDb = vendorRepository.findOne(vendorId);
        if (vendorInDb == null) throw new Exception(messages.getMessage("vendor.not.found"));

        vendorInDb.setVerificationCode(smsService.sendVerificationCode(SmsType.VENDOR, vendorInDb.getId()));
        vendorRepository.save(vendorInDb);
        return true;
    }

    @Transactional
    @Override
    public Vendor verifyCode(Long vendorId, Integer verificationCode) throws Exception {
        Vendor vendorInDb = vendorRepository.findOne(vendorId);
        if (vendorInDb == null) throw new Exception(messages.getMessage("vendor.not.found"));

        if ((verificationCode > 0) && (Objects.equals(vendorInDb.getVerificationCode(), verificationCode))) {
            vendorInDb.setVerified(Verified.yes);
            return vendorRepository.save(vendorInDb);
        } else {
            throw new Exception(messages.getMessage("vendor.not.verified"));
        }
    }
}
