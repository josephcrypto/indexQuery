package cn.coding.com.indexquery.encryption.util;

import cn.coding.com.indexquery.encryption.enums.SHAEncryptType;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SHAEncryptUtil {


    /**
     * SHA加密公共方法
     * @param string 目标字符串
     * @param type   加密类型 {@link SHAEncryptType}
     */
    public static String encrypt(String string, SHAEncryptType type) {
        if (string==null || "".equals(string.trim())) return "";
        if (type==null) type = SHAEncryptType.SHA256;
        try {
            MessageDigest md5 = MessageDigest.getInstance(type.value);
            byte[] bytes = md5.digest((string).getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
