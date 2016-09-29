package com.bitmakersbd.biyebari.server.validation;

import com.bitmakersbd.biyebari.server.util.Messages;
import com.bitmakersbd.biyebari.server.util.MultipleFileContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
public class MultipleFileValidator implements Validator {
    @Autowired
    Messages messages;

    public boolean supports(Class<?> clazz) {
        return MultipleFileContainer.class.isAssignableFrom(clazz);
    }

    public void validate(Object obj, Errors errors) {
        MultipleFileContainer filesContainer = (MultipleFileContainer) obj;
        if ((filesContainer.getFile() == null) || (filesContainer.getFile().size() < 1)) {
            errors.rejectValue("file", messages.getMessage("file.cannot.empty"));
        }

        int i = 0;
        for (MultipartFile file : filesContainer.getFile()) {
            if ((file == null) || (file.getSize() < 1)) {
                errors.rejectValue("file[" + i + "]", messages.getMessage("file.cannot.empty"));
            } else if (file.getSize() > Long.parseLong(messages.getMessage("file.upload.max.size"))) {
                errors.rejectValue("file[" + i + "]", messages.getMessage("file.size.limit.exceeded"));
            } else if (!checkExtension(file)) {
                errors.rejectValue("file[" + i + "]", messages.getMessage("file.extension.not.allowed"));
            }

            i++;
        }
    }

    private boolean checkExtension(MultipartFile file) {
        String ext = file.getOriginalFilename().split("\\.")[1];
        return getAllowedTypes().contains(ext.trim().toLowerCase());
    }

    private List<String> getAllowedTypes() {
        List<String> types = new ArrayList<>();

        for (String type : messages.getMessage("file.upload.allowed.types").split(",")) {
            types.add(type.trim().toLowerCase());
        }

        return types;
    }
}