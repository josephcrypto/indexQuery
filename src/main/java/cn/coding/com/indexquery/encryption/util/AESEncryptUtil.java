package cn.coding.com.indexquery.encryption.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p>AES加密处理工具类</p>
 * @author licoy.cn
 * @version 2018/9/5
 */
public class AESEncryptUtil {

    /**
     * AES加密
     * @param content  字符串内容
     * @param password 密钥
     */
    public static String encrypt(String content, String password, String ivKey){
        return aes(content,password,Cipher.ENCRYPT_MODE, ivKey);
    }


    /**
     * AES解密
     * @param content  字符串内容
     * @param password 密钥
     */
    public static String decrypt(String content, String password, String ivKey){
        return aes(content,password,Cipher.DECRYPT_MODE, ivKey);
    }

    /**
     * AES加密/解密 公共方法
     * @param content  字符串
     * @param password 密钥
     * @param type     加密：{@link Cipher#ENCRYPT_MODE}，解密：{@link Cipher#DECRYPT_MODE}
     */
    private static String aes(String content, String password, int type, String ivKey) {
        try {
            IvParameterSpec iv = new IvParameterSpec(ivKey.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(password.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

            if (type == Cipher.ENCRYPT_MODE) {
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
                byte[] encrypted = cipher.doFinal(content.getBytes());
                return Base64.encodeBase64String(encrypted);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
                byte[] original = cipher.doFinal(Base64.decodeBase64(content));
                return new String(original);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
