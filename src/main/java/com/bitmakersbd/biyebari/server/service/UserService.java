package com.bitmakersbd.biyebari.server.service;

import com.bitmakersbd.biyebari.server.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    Page getAll(Pageable pageable) throws Exception;

    User get(Long id) throws Exception;

    User create(User user) throws Exception;

    void delete(Long id) throws Exception;

    User update(User user) throws Exception;

    User login(String user, String password) throws Exception;

    Page search(String criteria, Pageable pageable);

    boolean isDuplicateEmail(String email);

    boolean isDuplicateContactNo(String contactNo);

    User updateProfilePic(Long id, MultipartFile file) throws Exception;

    boolean changePassword(Long userId, String oldPassword, String newPassword) throws Exception;

    boolean resetPassword(String user) throws Exception;

    boolean sendVerificationCode(Long userId) throws Exception;

    User verifyCode(Long userId, Integer verificationCode) throws Exception;
}
