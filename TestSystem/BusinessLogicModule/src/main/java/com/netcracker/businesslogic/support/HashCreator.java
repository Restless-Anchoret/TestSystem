package com.netcracker.businesslogic.support;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashCreator {

    private static final int DEFAULT_SALT_BYTES = 20;
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private static final String DEFAULT_HASH_ALGORITHM = "SHA-256";
    
    private static HashCreator defaultHashCreator;
    
    public static HashCreator getDefault() {
        if (defaultHashCreator == null) {
            defaultHashCreator = new HashCreator();
        }
        return defaultHashCreator;
    }
    
    public static byte[] convertStringToBytes(String line) {
        if (line.length() % 2 != 0) {
            throw new IllegalArgumentException();
        }
        byte[] bytes = new byte[line.length() / 2];
        for (int i = 0; i < line.length() / 2; i++) {
            String hexStringFirst = line.substring(i * 2, i * 2 + 1);
            String hexStringSecond = line.substring(i * 2 + 1, i * 2 + 2);
            bytes[i] = (byte)(Byte.valueOf(hexStringFirst, 16) * 16 +
                    Byte.valueOf(hexStringSecond, 16));
        }
        return bytes;
    }
    
    public static String convertBytesToString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte nextByte: bytes) {
            int intByte = nextByte & 0xFF;
            if (intByte < 16) {
                builder.append('0');
            }
            builder.append(Integer.toHexString(intByte));
        }
        return builder.toString();
    }
    
    public static byte[] getRandomBytes(int bytesQuantity) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[bytesQuantity];
        secureRandom.nextBytes(bytes);
        return bytes;
    }
    
    private int saltBytes = DEFAULT_SALT_BYTES;
    private Charset charset = DEFAULT_CHARSET;
    private String hashAlgorithm = DEFAULT_HASH_ALGORITHM;

    public HashCreator() { }

    public int getSaltBytes() {
        return saltBytes;
    }

    public void setSaltBytes(int saltBytes) {
        this.saltBytes = saltBytes;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public String getHashAlgorithm() {
        return hashAlgorithm;
    }

    public void setHashAlgorithm(String hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }
    
    public Result getHash(String password)
            throws NoSuchAlgorithmException {
        byte[] bytes = getRandomBytes(saltBytes);
        return getHash(password, bytes);
    }
    
    public Result getHash(String password, String salt)
            throws NoSuchAlgorithmException {
        byte[] bytes = convertStringToBytes(salt);
        return getHash(password, bytes);
    }
    
    public Result getHash(String password, byte[] salt)
            throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(hashAlgorithm);
        byte[] passwordBytes = password.getBytes(charset);
        digest.update(passwordBytes);
        byte[] tempHashBytes = digest.digest();
        digest.reset();
        digest.update(tempHashBytes);
        digest.update(salt);
        byte[] hashBytes = digest.digest();
        String hashString = convertBytesToString(hashBytes);
        String saltString = convertBytesToString(salt);
        return new Result(hashBytes, hashString, salt, saltString);
    }
    
    public static class Result {
        private byte[] passwordHashBytes;
        private String passwordHashString;
        private byte[] saltBytes;
        private String saltString;

        public Result(byte[] passwordHashBytes, String passwordHashString, byte[] saltBytes, String saltString) {
            this.passwordHashBytes = passwordHashBytes;
            this.passwordHashString = passwordHashString;
            this.saltBytes = saltBytes;
            this.saltString = saltString;
        }

        public byte[] getPasswordHashBytes() {
            return passwordHashBytes;
        }

        public String getPasswordHashString() {
            return passwordHashString;
        }

        public byte[] getSaltBytes() {
            return saltBytes;
        }

        public String getSaltString() {
            return saltString;
        }
    }

}
