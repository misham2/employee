package com.jdbctemplate.employee.config;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AES256TextEncrypter
{
    private static final String ALGORITHM = "AES";
    private static final String CHARSET = "UTF-8";

    private final SecretKeySpec secretKey;

    public AES256TextEncrypter(String key) {
        this.secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
    }

    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(CHARSET));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

//    public String decrypt(String encryptedText) throws Exception {
//        Cipher cipher = Cipher.getInstance(ALGORITHM);
//        cipher.init(Cipher.DECRYPT_MODE, secretKey);
//        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
//        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
//        return new String(decryptedBytes, CHARSET);
//    }

    public String decrypt(String encryptedText) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            return new String(decryptedBytes, CHARSET);
        } catch (Exception e) {
            throw new Exception("Error decrypting text: " + e.getMessage(), e);
        }
    }

}

