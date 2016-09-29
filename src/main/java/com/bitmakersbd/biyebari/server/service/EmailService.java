package com.bitmakersbd.biyebari.server.service;

import com.bitmakersbd.biyebari.server.util.Messages;
import com.bitmakersbd.biyebari.server.util.SpringUtil;

public interface EmailService {
    public static final String SMTP_HOST = SpringUtil.bean(Messages.class).getMessage("smtp.host");
    public static final String SMTP_PORT = SpringUtil.bean(Messages.class).getMessage("smtp.port");
    public static final String SMTP_USERNAME = SpringUtil.bean(Messages.class).getMessage("smtp.username");
    public static final String SMTP_PASSWORD = SpringUtil.bean(Messages.class).getMessage("smtp.password");

    public boolean send(String to, String subject, String body) throws Exception;
}
