package com.bitmakersbd.biyebari.server.service;

import com.bitmakersbd.biyebari.server.model.ApplicationSettings;
import com.bitmakersbd.biyebari.server.util.Messages;
import com.bitmakersbd.biyebari.server.util.UploadType;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FileUploadServiceImpl implements FileUploadService {
    private static final Logger logger = Logger.getLogger(FileUploadServiceImpl.class);

    @Autowired
    Messages messages;

    @Autowired
    ApplicationSettingsService applicationSettingsService;

    @Override
    public String uploadFile(UploadType uploadType, MultipartFile file, boolean createThumbnail, String additionalPath) throws Exception {
        // TODO: Implement thumbnail feature for single file

        try {
            String path = getPath(uploadType, additionalPath);

            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Create the file on server
            Random random = new Random();
            String fileName = String.valueOf(System.currentTimeMillis()) + "-" + Math.abs(random.nextInt());
            String fileExt = file.getOriginalFilename().split("\\.")[1];
            File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName + "." + fileExt);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            byte[] bytes = file.getBytes();
            stream.write(bytes);
            stream.close();

            changeFileOwnership(serverFile.getAbsolutePath());

            return serverFile.getName();
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            throw e;
        }
    }

    @Override
    public List<String> uploadFile(UploadType uploadType, List<MultipartFile> files, boolean createThumbnail, String additionalPath) throws Exception {
        try {
            String path = getPath(uploadType, additionalPath);

            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            List<String> uploadedFileNames = new ArrayList<>();
            for (MultipartFile file : files) {
                // Create the file on server
                Random random = new Random();
                String fileName = String.valueOf(System.currentTimeMillis()) + "-" + Math.abs(random.nextInt());
                String fileExt = file.getOriginalFilename().split("\\.")[1];
                File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName + "." + fileExt);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                byte[] bytes = file.getBytes();
                stream.write(bytes);
                stream.close();
                uploadedFileNames.add(serverFile.getName());

                changeFileOwnership(serverFile.getAbsolutePath());

                // create thumbnails
                if (createThumbnail) {
                    int thumbSizeX = Integer.parseInt(messages.getMessage("image.thumb.size.x"));
                    int thumbSizeY = Integer.parseInt(messages.getMessage("image.thumb.size.y"));

                    String thumbPath = applicationSettingsService.getByKey(messages.getMessage("item.image.thumb.abs.path.dbkey")).getValue();

                    if (StringUtils.isNotEmpty(additionalPath)) {
                        thumbPath += (File.separator + additionalPath);
                    }

                    File thumbFile = new File(thumbPath);
                    if (!thumbFile.exists()) {
                        thumbFile.mkdirs();
                    }

                    String outFilePath = thumbFile.getAbsolutePath() + File.separator + fileName + "." + fileExt;
                    Thumbnails.of(file.getInputStream()).size(thumbSizeX, thumbSizeY).toFile(outFilePath);

                    changeFileOwnership(outFilePath);
                }
            }

            return uploadedFileNames;
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            throw e;
        }
    }

    @Override
    public void deleteFile(UploadType uploadType, String fileName, boolean deleteThumbnail, String additionalPath) throws Exception {
        // TODO: Implement thumbnail delete for single file
        String path = getPath(uploadType, additionalPath);
        fileName = getCorrectedFileName(fileName);

        File file = new File(path + File.separator + fileName);

        if (!file.delete()) {
            throw new Exception(MessageFormat.format(messages.getMessage("file.could.not.delete.physical"), fileName));
        }
    }

    @Override
    public void deleteFile(UploadType uploadType, List<String> fileNames, boolean deleteThumbnail, String additionalPath) throws Exception {
        String path = getPath(uploadType, additionalPath);
        String thumbsPath = applicationSettingsService.getByKey(messages.getMessage("item.image.thumb.abs.path.dbkey")).getValue();

        if (StringUtils.isNotEmpty(additionalPath)) {
            thumbsPath += (File.separator + additionalPath);
        }

        File thumbsFile = new File(thumbsPath);

        for (String fileName : fileNames) {
            fileName = getCorrectedFileName(fileName);
            File file = new File(path + File.separator + fileName);

            if (!file.delete()) {
                throw new Exception(MessageFormat.format(messages.getMessage("file.could.not.delete.physical"), fileName));
            }

            if (deleteThumbnail) {
                File thumb = new File((thumbsFile.getAbsolutePath()) + File.separator + fileName);
                if (!thumb.delete()) {
                    throw new Exception(MessageFormat.format(messages.getMessage("file.could.not.delete.thumb.physical"), fileName));
                }
            }
        }
    }

    private String getPath(UploadType uploadType, String additionalPath) throws Exception {
        String dbKey = null;
        ApplicationSettings settings = null;
        String path = "/";

        switch (uploadType) {
            case VENDOR_LOGO:
                dbKey = messages.getMessage("vendor.logo.abs.path.dbkey");
                settings = applicationSettingsService.getByKey(dbKey);
                path = settings.getValue();
                break;
            case USER_PROFILE_PIC:
                dbKey = messages.getMessage("user.pic.abs.path.dbkey");
                settings = applicationSettingsService.getByKey(dbKey);
                path = settings.getValue();
                break;
            case ITEM_IMAGE:
                dbKey = messages.getMessage("item.image.abs.path.dbkey");
                settings = applicationSettingsService.getByKey(dbKey);
                path = settings.getValue();

                if (StringUtils.isNotEmpty(additionalPath)) {
                    path += (File.separator + additionalPath);
                }
                break;
        }

        return path;
    }

    private String getCorrectedFileName(String filePath) {
        return filePath.substring(filePath.lastIndexOf('/') + 1, filePath.length());
    }

    private void changeFileOwnership(String fileAbsPath) throws IOException {
        if (messages.getMessage("linux.image.upload.change.ownership").equals("true")) {
            Process p = Runtime.getRuntime().exec(new String[]{"sh /home/biyebari/script.sh " + fileAbsPath});
        }
    }
}
