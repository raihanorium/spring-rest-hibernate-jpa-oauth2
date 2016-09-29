package com.bitmakersbd.biyebari.server.validation;

import com.bitmakersbd.biyebari.server.util.Messages;
import com.bitmakersbd.biyebari.server.util.SingleFileContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
public class SingleFileValidator implements Validator {
    @Autowired
    Messages messages;

    public boolean supports(Class<?> clazz) {
        return SingleFileContainer.class.isAssignableFrom(clazz);
    }

    public void validate(Object obj, Errors errors) {
        SingleFileContainer fileContainer = (SingleFileContainer) obj;

        if((fileContainer.getFile() == null) || (fileContainer.getFile().getSize() < 1)){
            errors.rejectValue("file", messages.getMessage("file.cannot.empty"));
        }else if(fileContainer.getFile().getSize() > Long.parseLong(messages.getMessage("file.upload.max.size"))){
            errors.rejectValue("file", messages.getMessage("file.size.limit.exceeded"));
        }else if(! checkExtension(fileContainer.getFile())){
            errors.rejectValue("file", messages.getMessage("file.extension.not.allowed"));
        }
    }

    private boolean checkExtension(MultipartFile file) {
        String ext = file.getOriginalFilename().split("\\.")[1];
        return getAllowedTypes().contains(ext.trim().toLowerCase());
    }

    private List<String> getAllowedTypes(){
        List<String> types = new ArrayList<>();

        for(String type : messages.getMessage("file.upload.allowed.types").split(",")){
            types.add(type.trim().toLowerCase());
        }

        return types;
    }
}