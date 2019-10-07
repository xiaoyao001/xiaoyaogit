package com.boot.org.rsa;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
/**
 * 自制rsa加解密
 * @author xiaoyao
 *
 */
public class RSAUtil {

	 /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 255;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 255;

    /**
     * 获取密钥对
     * 
     * @return 密钥对
     */
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        return generator.generateKeyPair();
    }

    /**
     * 获取私钥
     * 
     * @param privateKey 私钥字符串
     * @return
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decodeBase64(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     * 
     * @param publicKey 公钥字符串
     * @return
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decodeBase64(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }
    
    /**
     * RSA加密
     * 
     * @param data 待加密数据
     * @param publicKey 公钥
     * @return
     */
    public static String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        // 加密后的字符串
        return new String(Base64.encodeBase64String(encryptedData));
    }

    /**
     * RSA解密
     * 
     * @param data 待解密数据
     * @param privateKey 私钥
     * @return
     */
    public static String decrypt(String data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] dataBytes = Base64.decodeBase64(data);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        // 解密后的内容 
        return new String(decryptedData, "UTF-8");
    }

    /**
     * 签名
     * 
     * @param data 待签名数据
     * @param privateKey 私钥
     * @return 签名
     */
    public static String sign(String data, PrivateKey privateKey) throws Exception {
        byte[] keyBytes = privateKey.getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(key);
        signature.update(data.getBytes());
        return new String(Base64.encodeBase64(signature.sign()));
    }

    /**
     * 验签
     * 
     * @param srcData 原始字符串
     * @param publicKey 公钥
     * @param sign 签名
     * @return 是否验签通过
     */
    public static boolean verify(String srcData, PublicKey publicKey, String sign) throws Exception {
        byte[] keyBytes = publicKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(Base64.decodeBase64(sign.getBytes()));
    }

    
    
    
    public static void main(String[] args) {
        try {
            // 生成密钥对
            KeyPair keyPair = getKeyPair();
            String privateKey = new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded()));
            String publicKey = new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));
//            String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALmEP4lEitsEZVP6\r\n" + 
//            		"jdLP8XyVuHoMRysTCanZWeEk8VB3IjkJX+WKPPcplDJ4J8wRWYDyWif+UzcSRlmp\r\n" + 
//            		"yFG5QH1JIO7ro8OSEwvOf7tGqOsuD66sjyf0P3XO8+kE0cFNYcVSMNfSVtvywA5H\r\n" + 
//            		"ThEOUa1fpGqivgQZjIkxzGNaVg4fAgMBAAECgYAJ5iWhxHeIUka54W+55QCkvjR2\r\n" + 
//            		"xrrxcHWei4uBOQBrY7/+vSJR1DuGkMBopQKyn+ZfvA7VfXuhP6djV2zp93GaZo7y\r\n" + 
//            		"L5ufuCEZEUFj6ydd684ckZXlOrws2YCPVRxrIZYUyG4BiNOh//c/Eh+8nsa4MZVk\r\n" + 
//            		"y/LkzNLSZCTp1WbCUQJBAN1yOCCCzvSVoGSPuy0lrvdHqRLCy8nm/gO5NtCjgysO\r\n" + 
//            		"EvZ1I/U12AuqiQUOLQMBmv79GJBtS4NgSxZP5330GbkCQQDWds29TonmRQlwWJk0\r\n" + 
//            		"uWbhWRaIj5zn/d6r3geoq0KZarq9VOyu/0e4bx8cH6A33sBwNBEvsYRpZg8vYDpk\r\n" + 
//            		"WvKXAkEAxvrDBDOF0rBAFxwvjDm4Jen2Oi1fAGTXCnF6l67VRfK3h01qqgNfxdvO\r\n" + 
//            		"kwRR3cmrhEPXtq7+k2YLEllqIPsdwQJABqVx5M79SQyUqU2lmdOxE4S+RQcmVq4w\r\n" + 
//            		"a2UbGagNDVIYbWunVhkFMPsInMeCIJkfn5qNecvnjZijGdXCmX+UCwJAMUEym3Gv\r\n" + 
//            		"+RWwEqmH5YbdHELt/EjUT3Tf+hblHsHhVj2/Ug7BpRhzutyFQwlgtzFpkd42AcgJ\r\n" + 
//            		"p1lPNo9zPNkuSA==";
//            String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC5hD+JRIrbBGVT+o3Sz/F8lbh6\r\n" + 
//            		"DEcrEwmp2VnhJPFQdyI5CV/lijz3KZQyeCfMEVmA8lon/lM3EkZZqchRuUB9SSDu\r\n" + 
//            		"66PDkhMLzn+7RqjrLg+urI8n9D91zvPpBNHBTWHFUjDX0lbb8sAOR04RDlGtX6Rq\r\n" + 
//            		"or4EGYyJMcxjWlYOHwIDAQAB";
            System.out.println("私钥:" + privateKey);
            System.out.println("公钥:" + publicKey);
            // RSA加密
            String data = "简单来说，签名主要包含两个过程：简单来说，签名主要包含两个过程：简单来说，签名主要包含两个过程：简单来说，签名主要包含两个过程：简单来说，签名主要包含两个过程：简单来说，签名主要包含两个过程：简单来说，签名主要包含两个过程：简单来说，签名主要包含两个过程：简单来说，签名主要包含两个过程：简单来说，签名主要包含两个过程：";
            String encryptData = encrypt(data, getPublicKey(publicKey));
            System.out.println("加密后内容:" + encryptData);
            // RSA解密
            String decryptData = decrypt(encryptData, getPrivateKey(privateKey));
            System.out.println("解密后内容:" + decryptData);
            
            // RSA签名
            String sign = sign(decryptData, getPrivateKey(privateKey));
            System.out.println("获得的签名为："+sign);
            // RSA验签
            boolean result = verify(decryptData, getPublicKey(publicKey), sign);
            System.out.print("验签结果:" + result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("加解密异常");
        }
    }
}
