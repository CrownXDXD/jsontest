package com.example.demo.utils;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Base64;

public class DesUtils {


  /**
   * CBS填充模式
   */
  private static final String IV_STRING = "abc0123456789abc";

  /**
   * <p>Description: 加密，AES对称加密，
   * 填充模式:AES/CBC/PKCS5Padding，
   * 偏移量：abc0123456789abc</p>
   * @param content content
   * @param key key
   * @return String
   */
  public static String encrypt(String content, String key) {
    try {
      byte[] byteContent = content.getBytes("UTF-8");
      byte[] enCodeFormat = key.getBytes("UTF-8");
      SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(enCodeFormat, 16), "AES");
      byte[] initParam = IV_STRING.getBytes();
      IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
      // 指定加密的算法、工作模式和填充方式
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
      byte[] encryptedBytes = cipher.doFinal(byteContent);
      // 同样对加密后数据进行 base64 编码
      Base64.Encoder baseEncoder = Base64.getEncoder();
      return baseEncoder.encodeToString(encryptedBytes);
    } catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
      | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   *
   * <p>Description: 解密：AES对称解密
   * 填充模式:AES/CBC/PKCS5Padding，
   * 偏移量：abc0123456789abc</p>
   * @param content content
   * @param key key
   * @return String
   */
  public static String decrypt(String content, String key) {
    // base64 解码
    try {
      Base64.Decoder baseDecoder = Base64.getDecoder();
      byte[] strBytes = baseDecoder.decode(content);
      byte[] enCodeFormat = key.getBytes("UTF-8");
      SecretKeySpec secretKey = new SecretKeySpec(Arrays.copyOf(enCodeFormat, 16), "AES");
      byte[] initParam = IV_STRING.getBytes("UTF-8");
      IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
      byte[] result = cipher.doFinal(strBytes);
      return new String(result, "UTF-8");
    } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
      | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | IOException e) {

      e.printStackTrace();
    }
    return null;
  }





}
