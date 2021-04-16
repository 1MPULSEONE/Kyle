package com.lovejazz.kyle;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class CryptographyService {

    private static final String AES = "AES";


    public String encryptText (String text, String password)
            throws NoSuchAlgorithmException, NoSuchPaddingException {
        SecretKeySpec key = generateKey(password);
        Cipher cipher = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decValueArray = Base64.getDecoder().decode(text);
        byte[] decValue = c.doFinal(decValueArray);
        return new String(decValue);
    }

    public String decryptText (String encryptingText, String password)
            throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException {
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encValueArray = c.doFinal(text.getBytes());
        String encValue = Base64.getEncoder().encodeToString(encValueArray);
        return encValue;
    }

    private SecretKeySpec generateKey (String password) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes(StandardCharsets.UTF_8);
        digest.update(bytes, 0 , bytes.length);
        return new SecretKeySpec(digest.digest(), AES);
    }

}
