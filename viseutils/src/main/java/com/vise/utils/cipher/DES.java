package com.vise.utils.cipher;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * @Description: DES加密解密
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2017-01-12 11:20
 */
public class DES {

    /**
     * 生成密钥
     *
     * @return
     * @throws Exception
     */
    public static String initKey() throws Exception {
        return initKey(null);
    }

    /**
     * 生成密钥
     *
     * @param seed
     * @return
     * @throws Exception
     */
    public static String initKey(String seed) throws Exception {
        SecureRandom secureRandom = null;
        if (seed != null) {
            secureRandom = new SecureRandom(BASE64.decode(seed));
        } else {
            secureRandom = new SecureRandom();
        }
        KeyGenerator kg = KeyGenerator.getInstance(CipherType.DES.getType());
        kg.init(secureRandom);
        SecretKey secretKey = kg.generateKey();
        return BASE64.encodeToString(secretKey.getEncoded());
    }

    /**
     * 转换秘钥
     *
     * @param key
     * @return
     * @throws Exception
     */
    private static Key toKey(byte[] key) throws Exception {
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(CipherType.DES.getType());
        SecretKey secretKey = keyFactory.generateSecret(dks);
        return secretKey;
    }

    /**
     * 解密
     *
     * @param plain
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] plain, String key) throws Exception {
        Key k = toKey(BASE64.decode(key));
        Cipher cipher = Cipher.getInstance(CipherType.DES.getType());
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher.doFinal(plain);
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, String key) throws Exception {
        Key k = toKey(BASE64.decode(key));
        Cipher cipher = Cipher.getInstance(CipherType.DES.getType());
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return cipher.doFinal(data);
    }
}
