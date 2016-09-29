package com.bitmakersbd.biyebari.server.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class MultipleFileContainer {
    private List<MultipartFile> file = new ArrayList<>();

    public MultipleFileContainer() {
    }

    public List<MultipartFile> getFile() {
        return file;
    }

    public void setFile(List<MultipartFile> file) {
        this.file = file;
    }
}
