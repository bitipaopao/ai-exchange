package com.lenovo.research.se.aiexchange.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Objects;

public class RASUtil {
    private final static Logger logger = LoggerFactory.getLogger(RASUtil.class);

    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    public static final Integer RSA_KEY_SIZE = 1024;

    public static String sign(String toSign, String privateKey) {
        String defaultResult = "";
        try {
            byte[] keyBytes = decryptBASE64(privateKey);
            if (Objects.isNull(keyBytes)) {
                return defaultResult;
            }

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey key = keyFactory.generatePrivate(keySpec);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(key);
            signature.update(toSign.getBytes(StandardCharsets.UTF_8));
            byte[] sign = signature.sign();
            return Base64.getEncoder().encodeToString(sign);
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException | InvalidKeySpecException e) {
            logger.error("Sign error on message: " + toSign, e);
        }
        return defaultResult;
    }

    public static boolean verify(String toVerify, String publicKey, String sign) {
        try {
            byte[] keyBytes = decryptBASE64(publicKey);
            if (Objects.isNull(keyBytes)) {
                return false;
            }

            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey key = keyFactory.generatePublic(keySpec);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(key);
            signature.update(toVerify.getBytes(StandardCharsets.UTF_8));
            return signature.verify(Base64.getDecoder().decode(sign.getBytes()));
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException | InvalidKeySpecException e) {
            logger.error("Verify sign error on toVerify: " + toVerify + " and sign: " + sign, e);
        }
        return false;
    }

    public static RASKeyPair createRASKey() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGen.initialize(RSA_KEY_SIZE);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            return new RASKeyPair(encryptBASE64(privateKey.getEncoded()), encryptBASE64(publicKey.getEncoded()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Create public key error.", e);
        }
    }

    private static byte[] decryptBASE64(String key) {
        try {
            return Base64.getDecoder().decode(key);
        } catch (Exception e) {
            logger.info("Decode base64 error for str:" + key, e);
            return null;
        }
    }

    public static String encryptRAS( String str, String publicKey ){
        try {
            byte[] decoded = Base64.getDecoder().decode(publicKey);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(KEY_ALGORITHM)
                    .generatePublic(new X509EncodedKeySpec(decoded));

            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException("Encrypt error with message: " + str, e);
        }
    }

    public static String decryptRAS(String str, String privateKey){
        try {
            byte[] inputByte = Base64.getDecoder().decode(str.getBytes(StandardCharsets.UTF_8));
            byte[] privateKeyDecoded = Base64.getDecoder().decode(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(KEY_ALGORITHM)
                    .generatePrivate(new PKCS8EncodedKeySpec(privateKeyDecoded));

            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            return new String(cipher.doFinal(inputByte));
        } catch (Exception e) {
            throw new RuntimeException("DecryptRAS error with message: " + str, e);
        }

    }

    private static String encryptBASE64(byte[] key) {
        return Base64.getEncoder().encodeToString(key);
    }

    public static class RASKeyPair {

        private final String privateKey;
        private final String publicKey;

        public RASKeyPair(String privateKey, String publicKey) {
            this.privateKey = privateKey;
            this.publicKey = publicKey;
        }

        public String getPrivateKey() {
            return privateKey;
        }

        public String getPublicKey() {
            return publicKey;
        }
    }
}
