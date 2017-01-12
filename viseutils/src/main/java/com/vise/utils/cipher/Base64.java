package com.vise.utils.cipher;

import android.util.Base64;

/**
 * @Description: Base64加密解密
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2017-01-12 10:58
 */
public class BASE64 {

    public static byte[] encode(byte[] plain) {
        return Base64.encode(plain, Base64.DEFAULT);
    }

    public static String encodeToString(byte[] plain) {
        return Base64.encodeToString(plain, Base64.DEFAULT);
    }

    public static byte[] decode(String text) {
        return Base64.decode(text, Base64.DEFAULT);
    }

    public static byte[] decode(byte[] text) {
        return Base64.decode(text, Base64.DEFAULT);
    }
}
