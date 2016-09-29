package com.bitmakersbd.biyebari.server.service;

import com.bitmakersbd.biyebari.server.util.SmsType;

/**
 * Created by Prokash Sarkar on 9/29/2016.
 */
public interface SmsService {
    int sendVerificationCode(SmsType type, Long id) throws Exception;
}
