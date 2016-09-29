package com.bitmakersbd.biyebari.server.service;

import com.bitmakersbd.biyebari.server.util.HttpUtil;
import com.bitmakersbd.biyebari.server.util.Messages;
import com.bitmakersbd.biyebari.server.util.SmsType;
import org.apache.commons.httpclient.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.Random;

/**
 * Created by Prokash Sarkar on 9/29/2016.
 */
public class SmsServiceImpl implements SmsService {

    @Autowired
    Messages messages;

    @Override
    public int sendVerificationCode(SmsType type, Long id) throws Exception {
        int code = generateVerificationCode();
        int responseStatus = HttpUtil.sendGet(MessageFormat.format(messages.getMessage("sms.url"), id, Integer.toString(code)));
        if (responseStatus == HttpStatus.SC_OK) {
            return code;
        } else {
            throw new Exception(messages.getMessage("sms.cannot.send"));
        }
    }

    private int generateVerificationCode() {
        // generate a 6 digit random integer
        Random rnd = new Random();
        return 100000 + rnd.nextInt(900000);
    }
}
