package com.vise.utils.cipher;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * @Description: MD5加密
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2017-01-12 11:04
 */
public class MD5 {

    /**
     * 十六进制
     *
     * @param buffer
     * @return
     */
    public static String getMessageDigest(byte[] buffer) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance(CipherType.MD5.getType());
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param buffer
     * @return
     */
    public static byte[] getRawDigest(byte[] buffer) {
        try {
            MessageDigest mdTemp = MessageDigest.getInstance(CipherType.MD5.getType());
            mdTemp.update(buffer);
            return mdTemp.digest();

        } catch (Exception e) {
            return null;
        }
    }


    private static String getMD5(final InputStream is, final int bufLen) {
        if (is == null || bufLen <= 0) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance(CipherType.MD5.getType());
            StringBuilder md5Str = new StringBuilder(32);

            byte[] buf = new byte[bufLen];
            int readCount = 0;
            while ((readCount = is.read(buf)) != -1) {
                md.update(buf, 0, readCount);
            }

            byte[] hashValue = md.digest();

            for (int i = 0; i < hashValue.length; i++) {
                md5Str.append(Integer.toString((hashValue[i] & 0xff) + 0x100, 16).substring(1));
            }
            return md5Str.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 对文件进行md5
     *
     * @param filePath 文件路径
     * @return
     */
    public static String getMD5(final String filePath) {
        if (filePath == null) {
            return null;
        }

        File f = new File(filePath);
        if (f.exists()) {
            return getMD5(f, 1024 * 100);
        }
        return null;
    }

    /**
     * 文件md5
     *
     * @param file
     * @return
     */
    public static String getMD5(final File file) {
        return getMD5(file, 1024 * 100);
    }


    private static String getMD5(final File file, final int bufLen) {
        if (file == null || bufLen <= 0 || !file.exists()) {
            return null;
        }

        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
            String md5 = getMD5(fin, (int) (bufLen <= file.length() ? bufLen : file.length()));
            fin.close();
            return md5;

        } catch (Exception e) {
            return null;

        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }
            } catch (IOException e) {

            }
        }
    }
}
