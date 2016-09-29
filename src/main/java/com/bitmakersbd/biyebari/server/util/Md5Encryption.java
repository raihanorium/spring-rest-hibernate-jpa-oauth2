package com.bitmakersbd.biyebari.server.util;

import java.security.MessageDigest;

public abstract class Md5Encryption {
    public static String encrypt(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (str == null || str.length() == 0) {
                throw new IllegalArgumentException("String to encrypt cannot be null or zero length");
            }

            md.update(str.getBytes());
            byte[] hash = md.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
