package com.lenovo.research.se.aiexchange.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MD5Util {
    private final static Logger logger = LoggerFactory.getLogger(MD5Util.class);

    public static String sign(String toSign, byte[] sc) {
        String md5val = MD5Util.md5(toSign, StandardCharsets.UTF_8);
        String signStr = hashHmac(md5val, sc).toUpperCase();
        return URLEncoder.encode(signStr);
    }

    public static boolean verify(String toSign, byte[] sc, String sign) {
        String md5val = MD5Util.md5(toSign, StandardCharsets.UTF_8);
        String expectSign = hashHmac(md5val, sc).toUpperCase();
        return MessageDigest.isEqual(expectSign.getBytes(), sign.getBytes());
    }

    public static String getFileMD5String(String file) {
        try (InputStream fis = new FileInputStream(file)) {
            return DigestUtils.md5DigestAsHex(fis);
        } catch (Exception e) {
            logger.error("Generate Md5 code for File: " + file + " error.", e);
        }
        return null;
    }

    public static String md5(String str, Charset chartSet) {
        return org.springframework.util.DigestUtils.md5DigestAsHex(str.getBytes(chartSet));
    }

    public static String hashHmac(String text, byte[] sc) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(sc, "HmacSHA256");
            sha256_HMAC.init(secret_key);
            return Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(text.getBytes()));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("SHA256 encode error.", e);
        }
    }

}
