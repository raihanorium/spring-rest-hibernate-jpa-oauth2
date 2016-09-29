package com.bitmakersbd.biyebari.server.service;

import com.bitmakersbd.biyebari.server.model.User;
import com.bitmakersbd.biyebari.server.repository.UserRepository;
import com.bitmakersbd.biyebari.server.util.*;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Random;

public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    Messages messages;

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    SmsService smsService;

    @Transactional(readOnly = true)
    @Override
    public Page getAll(Pageable pageable) throws Exception {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public User get(Long id) throws Exception {
        return userRepository.findOne(id);
    }

    @Transactional
    @Override
    public User create(User user) throws Exception {
        if (StringUtils.isNotEmpty(user.getEmail())) {
            if (isDuplicateEmail(user.getEmail())) {
                throw new Exception(messages.getMessage("email.already.registered"));
            }
        }
        if (isDuplicateContactNo(user.getContactNo())) {
            throw new Exception(messages.getMessage("contactNo.already.exists"));
        }

        // do not set profile pic when add or update. profile pic is added by upload method
        user.setProfilePic(null);
        user.setPassword(Md5Encryption.encrypt(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void delete(Long id) throws Exception {
        User userInDb = userRepository.findOne(id);
        if (userInDb == null) throw new Exception(messages.getMessage("user.not.found"));

        userRepository.delete(id);
    }

    @Transactional
    @Override
    public User update(User user) throws Exception {
        User userInDb = userRepository.findOne(user.getId());
        if (userInDb == null) throw new Exception(messages.getMessage("user.not.found"));

        // check for duplicate email.
        if (StringUtils.isNotEmpty(user.getEmail())) {
            if ((!user.getEmail().equals(userInDb.getEmail())) && isDuplicateEmail(user.getEmail())) {
                throw new Exception(messages.getMessage("email.already.registered"));
            }
        }

        // check for duplicate contact no.
        if ((!user.getContactNo().equals(userInDb.getContactNo())) && isDuplicateContactNo(user.getContactNo())) {
            throw new Exception(messages.getMessage("contactNo.already.exists"));
        }

        // update only fields which are allowed to update
        userInDb.setEmail(user.getEmail());
        userInDb.setFullName(user.getFullName());
        userInDb.setFirstName(user.getFirstName());
        userInDb.setLastName(user.getLastName());
        userInDb.setContactNo(user.getContactNo());
        userInDb.setNidOrPassportNo(user.getNidOrPassportNo());
        userInDb.setCompanyId(user.getCompanyId());
        userInDb.setAbout(user.getAbout());
        // do not set profile pic when add or update. profile pic is added by upload method
        userInDb.setPosition(user.getPosition());
        userInDb.setLocation(user.getLocation());
        userInDb.setWebsite(user.getWebsite());
        userInDb.setFbUrl(user.getFbUrl());
        userInDb.setGoogleUrl(user.getGoogleUrl());
        userInDb.setTwitterUrl(user.getTwitterUrl());
        userInDb.setLinkedinUrl(user.getLinkedinUrl());

        return userRepository.save(userInDb);
    }

    @Transactional(readOnly = true)
    @Override
    public User login(String user, String password) throws Exception {
        // user is the email or contact number of the user
        String encryptedPasswordGiven = Md5Encryption.encrypt(password);
        User userInDb;

        if (user.contains("@")) {
            userInDb = userRepository.findOneByEmailAndPassword(user, encryptedPasswordGiven);
        } else {
            userInDb = userRepository.findOneByContactNoAndPassword(user, encryptedPasswordGiven);
        }

        if (userInDb == null) {
            throw new Exception(messages.getMessage("invalid.email.password"));
        }

        if (userInDb.getVerified() != Verified.yes) {
            throw new Exception(messages.getMessage("user.not.verified"));
        }

        return userInDb;
    }

    @Transactional(readOnly = true)
    @Override
    public Page search(String criteria, Pageable pageable) {
        return userRepository.searchUsers(criteria, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isDuplicateEmail(String email) {
        return userRepository.findOneByEmail(email) != null;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isDuplicateContactNo(String contactNo) {
        return userRepository.findOneByContactNo(contactNo) != null;
    }

    @Transactional
    @Override
    public User updateProfilePic(Long id, MultipartFile file) throws Exception {
        User userInDb = userRepository.findOne(id);
        if (userInDb == null) throw new Exception(messages.getMessage("user.not.found"));

        String fileName = fileUploadService.uploadFile(UploadType.USER_PROFILE_PIC, file, false, null);

        userInDb.setProfilePic(fileName);
        return userRepository.save(userInDb);
    }

    @Transactional
    @Override
    public boolean changePassword(Long userId, String oldPassword, String newPassword) throws Exception {
        oldPassword = Md5Encryption.encrypt(oldPassword);
        newPassword = Md5Encryption.encrypt(newPassword);

        User userInDb = userRepository.findOneByIdAndPassword(userId, oldPassword);
        if (userInDb == null) throw new Exception(messages.getMessage("incorrect.old.password"));

        userInDb.setPassword(newPassword);
        userRepository.save(userInDb);

        return true;
    }

    @Transactional
    @Override
    public boolean sendVerificationCode(Long userId) throws Exception {
        User userInDb = userRepository.findOne(userId);
        if (userInDb == null) throw new Exception(messages.getMessage("user.not.found"));

        userInDb.setVerificationCode(smsService.sendVerificationCode(SmsType.USER, userInDb.getId()));
        userRepository.save(userInDb);
        return true;

    }

    @Transactional
    @Override
    public User verifyCode(Long userId, Integer verificationCode) throws Exception {
        User userInDb = userRepository.findOne(userId);
        if (userInDb == null) throw new Exception(messages.getMessage("user.not.found"));

        if ((verificationCode > 0) && (Objects.equals(userInDb.getVerificationCode(), verificationCode))) {
            userInDb.setVerified(Verified.yes);
            return userRepository.save(userInDb);
        } else {
            throw new Exception(messages.getMessage("user.not.verified"));
        }
    }
}
