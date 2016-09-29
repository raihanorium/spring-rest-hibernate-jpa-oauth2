package com.bitmakersbd.biyebari.server.util;

import org.springframework.web.multipart.MultipartFile;

public class SingleFileContainer {
    private MultipartFile file;

    public SingleFileContainer() {
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
