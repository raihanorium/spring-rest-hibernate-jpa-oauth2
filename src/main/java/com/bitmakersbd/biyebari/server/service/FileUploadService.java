package com.bitmakersbd.biyebari.server.service;

import com.bitmakersbd.biyebari.server.util.UploadType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {
    public String uploadFile(UploadType uploadType, MultipartFile file, boolean createThumbnail, String additionalPath) throws Exception;

    public List<String> uploadFile(UploadType uploadType, List<MultipartFile> files, boolean createThumbnail, String additionalPath) throws Exception;

    public void deleteFile(UploadType uploadType, String fileName, boolean deleteThumbnail, String additionalPath) throws Exception;

    public void deleteFile(UploadType uploadType, List<String> fileNames, boolean deleteThumbnails, String additionalPath) throws Exception;
}
