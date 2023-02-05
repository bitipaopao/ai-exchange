package com.lenovo.research.se.aiexchange.common.utils;

import com.lenovo.research.se.aiexchange.model.exception.InternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class AESUtil {
    private static final Logger logger = LoggerFactory.getLogger(AESUtil.class);

    public static String decryptCBC(String key, String encrypted) {
        try {
            byte[] messageBytes = Base64.getDecoder().decode(encrypted);
            byte[] ivBytes = Arrays.copyOf(messageBytes, 16);
            byte[] data = Arrays.copyOfRange(messageBytes, 16, messageBytes.length);
            IvParameterSpec iv = new IvParameterSpec(ivBytes);
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(data);
            original = Zlib.decompress(original);
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("AES CBC decrypt error", e);
            throw new InternalException(500, "AES CBC decrypt error", e);
        }
    }

    private static final String initVector = "encryptionIntVec";

    public static String encryptCBC(String key, byte[] dataEncode) {
        try {
            byte[] ivBytes = initVector.getBytes(StandardCharsets.UTF_8);
            IvParameterSpec iv = new IvParameterSpec(ivBytes);
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(dataEncode);
            byte[] target = new byte[encrypted.length + ivBytes.length];
            System.arraycopy(ivBytes, 0, target, 0, ivBytes.length);
            System.arraycopy(encrypted, 0, target, ivBytes.length, encrypted.length);
            return new String(Base64.getEncoder().encode(target),
                    StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("AES CBC encryptCBC error", e);
            throw new InternalException(500, "AES CBC encryptCBC error", e);
        }
    }
}
