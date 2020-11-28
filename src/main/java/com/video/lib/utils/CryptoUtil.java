package com.video.lib.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

@Slf4j
public class CryptoUtil {

    public static final String ALGORITHM = "AES";

    public static String encrypt(String toEncrypt, String salt) {
        StringBuilder sb = new StringBuilder();
        try {
            Key aesKey = new SecretKeySpec(salt.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(toEncrypt.getBytes());

            for (byte b : encrypted) {
                sb.append((char) b);
            }
        }catch (Exception e){
            log.info("Unable to encrypt the data", e);
        }
        return sb.toString();
    }

    public static String decrypt(String toDecrypt, String salt) {
        try {
            byte[] dataAsBytes = new byte[toDecrypt.length()];
            for (int i = 0; i < toDecrypt.length(); i++) {
                dataAsBytes[i] = (byte) toDecrypt.charAt(i);
            }
            Key aesKey = new SecretKeySpec(salt.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance("AES");
            // decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            return new String(cipher.doFinal(dataAsBytes));
        }catch (Exception e){
            log.error("Unable to decrypt the data", e);
        }
        return null;
    }

}
