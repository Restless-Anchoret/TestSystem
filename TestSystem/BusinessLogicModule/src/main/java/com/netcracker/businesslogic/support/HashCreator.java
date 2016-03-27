package com.netcracker.businesslogic.support;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class HashCreator {

    private HashCreator() { }

    public static String getHash(String password, Date registrationDate)
            throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA");
        byte[] passwordBytes = password.getBytes();
        digest.update(passwordBytes);
        byte[] saltBytes = Long.toString(registrationDate.getTime()).getBytes();
        digest.update(saltBytes);
        byte[] hashBytes = digest.digest();
        StringBuilder builder = new StringBuilder();
        for (byte nextByte: hashBytes) {
            int intByte = nextByte & 0xFF;
            if (intByte < 16) {
                builder.append('0');
            }
            builder.append(Integer.toHexString(intByte));
        }
        return builder.toString();
    }

}
