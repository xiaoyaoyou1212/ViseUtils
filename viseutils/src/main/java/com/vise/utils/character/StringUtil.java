package com.vise.utils.character;

import android.content.Context;
import android.os.Build;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class StringUtil {

    public static final String PHONE_FORMAT = "^((17[0-9])|(13[0-9])|(15[0-3,5-9])|(18[0-9])|(145)|(147))\\d{8}$";
    public static final String EMAIL_FORMAT = "^[0-9a-zA-Z][_.0-9a-zA-Z-]{0,43}@([0-9a-zA-Z][0-9a-zA-Z-]{0," +
            "30}[0-9a-zA-Z].){1,4}[a-zA-Z]{2,4}$";
    public static final String VERIFY_CODE_FORMAT = "^\\d{4}$";
    public static final String PASSWORD_LEGAL_CHARACTERS = "[a-zA-Z0-9]{6,20}";

    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    private static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 判断字符串是否为空
     *
     * @param str null、“ ”、“null”都返回true
     * @return boolean
     */
    public static boolean isNullString(String str) {
        return (null == str || isBlank(str.trim()) || "null".equals(str.trim()
                .toLowerCase())) ? true : false;
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 格式化字符串 如果为空，返回“”
     *
     * @param str
     * @return String
     */
    public static String formatString(String str) {
        if (isNullString(str)) {
            return "";
        } else {
            return str;
        }
    }

    /**
     * 获得文件名称
     *
     * @param path
     * @return String
     */
    public static String getFileName(String path) {
        if (isNullString(path))
            return null;
        int bingindex = path.lastIndexOf("/");
        int endindex = path.lastIndexOf(".");
        return path.substring(bingindex + 1, endindex);
    }

    private static Pattern numericPattern = Pattern.compile("^[0-9\\-]+$");

    /**
     * 判断字符串是否是数字
     *
     * @param src
     * @return boolean
     */
    public static boolean isNumeric(String src) {
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = numericPattern.matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }

    /**
     * 自动命名文件,命名文件格式如：IP地址+时间戳+三位随机数 .doc
     *
     * @param ip       ip地址
     * @param fileName 文件名
     * @return String
     */
    public static String getIPTimeRandName(String ip, String fileName) {
        StringBuffer buf = new StringBuffer();
        if (ip != null) {
            String str[] = ip.split("\\.");
            for (int i = 0; i < str.length; i++) {
                buf.append(addZero(str[i], 3));
            }
        }// 加上IP地址
        buf.append(getTimeStamp());// 加上日期
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            buf.append(random.nextInt(10));// 取三个随机数追加到StringBuffer
        }
        buf.append("." + getFileExt(fileName));// 加上扩展名
        return buf.toString();

    }

    /**
     * 自动命名文件,命名文件格式如：时间戳+三位随机数 .doc
     *
     * @param fileName
     * @return String
     */
    public static String getTmeRandName(String fileName) {
        return getIPTimeRandName(null, fileName);
    }

    public static String addZero(String str, int len) {
        StringBuffer s = new StringBuffer();
        s.append(str);
        while (s.length() < len) {
            s.insert(0, "0");
        }
        return s.toString();
    }

    /**
     * 获得时间戳 也可以用 ：commons-lang.rar 下的：DateFormatUtils类 更为简单
     *
     * @return String
     */
    public static String getTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date());
    }

    /**
     * 获得文件扩展名
     *
     * @param filename
     * @return String
     */
    public static String getFileExt(String filename) {
        int i = filename.lastIndexOf(".");// 返回最后一个点的位置
        String extension = filename.substring(i + 1);// 取出扩展名
        return extension;
    }

    /**
     * 将url进行utf-8编码
     *
     * @param url
     * @return String
     */
    public static final String encodeURL(String url) {
        try {
            return URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将url进行utf-8解码
     *
     * @param url
     * @return String
     */
    public static final String decodeURL(String url) {
        try {
            return URLDecoder.decode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 格式化日期字符串 也可以用 ：commons-lang.rar 下的：DateFormatUtils类 更为简单
     *
     * @param date
     * @param pattern
     * @return String
     */
    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 格式化日期字符串 也可以用 ：commons-lang.rar 下的：DateFormatUtils类 更为简单
     */
    public static String formatDate(Date date) {
        return formatDate(date, DEFAULT_DATE_PATTERN);
    }

    /**
     * 获取当前时间 也可以用 ：commons-lang.rar 下的：DateFormatUtils类 更为简单
     *
     * @return String
     */
    public static String getDate() {
        return formatDate(new Date(), DEFAULT_DATE_PATTERN);
    }

    /**
     * 获取当前时间
     *
     * @return String
     */
    public static String getDateTime() {
        return formatDate(new Date(), DEFAULT_DATETIME_PATTERN);
    }

    /**
     * 格式化日期时间字符串
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, DEFAULT_DATETIME_PATTERN);
    }

    /**
     * 格式化json格式日期
     *
     * @param date
     * @return String
     */
    public static String formatJsonDateTime(JSONObject date) {
        Date result = null;
        try {
            result = new Date(date.getInt("year"), date.getInt("month"),
                    date.getInt("date"), date.getInt("hours"),
                    date.getInt("minutes"), date.getInt("seconds"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result == null ? "" : formatDateTime(result);
    }

    /**
     * 将字符串集合 变为以 separator 分割的字符串
     *
     * @param array     字符串集合
     * @param separator 分隔符
     * @return String
     */
    public static String join(final ArrayList<String> array, String separator) {
        StringBuffer result = new StringBuffer();
        if (array != null && array.size() > 0) {
            for (String str : array) {
                result.append(str);
                result.append(separator);
            }
            result.delete(result.length() - 1, result.length());
        }
        return result.toString();
    }

    /**
     * 压缩字符串
     *
     * @param str
     * @return String
     * @throws IOException
     */
    public static String compress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();
        return out.toString("ISO-8859-1");
    }

    /**
     * 解压缩字符串
     *
     * @param str
     * @return String
     * @throws IOException
     */
    public static String uncompress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(
                str.getBytes("ISO-8859-1"));
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        return out.toString("UTF-8");
    }

    /**
     * <b>description :</b> 去除特殊字符或将所有中文标号替换为英文标号
     *
     * @param input
     * @return String
     */
    public static String stringFilter(String input) {
        if (input == null)
            return null;
        input = input.replaceAll("【", "[").replaceAll("】", "]")
                .replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(input);
        return m.replaceAll("").trim();
    }

    /**
     * <b>description :</b> 半角字符转全角字符
     *
     * @param input
     * @return String
     */
    public static String ToDBC(String input) {
        if (input == null)
            return null;
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 判断字符串"oldString"是否为null
     *
     * @param oldString 需要判断的字符串
     * @return String 如果"oldString"为null返回空值"",否则返回"oldString"
     */
    public static String getString(String oldString) {
        if (oldString == null || "null".equals(oldString)) {
            return "";
        } else {
            return oldString.trim();
        }
    }

    /**
     * 将一实数转换成字符串并返回
     *
     * @param d 实数
     * @return String
     */
    public static String getString(double d) {
        return String.valueOf(d);
    }

    /**
     * 得到文件的后缀名(扩展名)
     *
     * @param name
     * @return String 后缀名
     */
    public static String getAfterPrefix(String name) throws Exception {
        return name.substring(name.lastIndexOf(".") + 1, name.length());
    }

    /**
     * 分割字符串
     *
     * @param values 要分割的内容
     * @param limit  分隔符 例：以“,”分割
     * @return String[] 返回数组，没有返回null
     */
    public static String[] spilctMoreSelect(String values, String limit) {
        if (isNullOrEmpty(values)) {
            return null;
        }
        return values.trim().split(limit);
    }

    /**
     * 将字符串数组转化为字符串
     *
     * @param needvalue
     * @return String 返回字符串，否则返回null
     */
    public static String arr2Str(String[] needvalue) {
        String str = "";
        if (needvalue != null) {
            int len = needvalue.length;
            for (int i = 0; i < len; i++) {
                if (i == len - 1) {
                    str += needvalue[i];
                } else {
                    str += needvalue[i] + ",";
                }
            }
            return str;
        } else {
            return null;
        }
    }

    public static int arr2int(String[] arr) {
        if (arr != null && arr.length > 0) {
            return Integer.parseInt(arr[1]);
        }
        return -1;
    }

    /**
     * 判断字符串是否为空或空符串。
     *
     * @param str 要判断的字符串。
     * @return String 返回判断的结果。如果指定的字符串为空或空符串，则返回true；否则返回false。
     */
    public static boolean isNullOrEmpty(String str) {

        return (str == null) || (str.trim().length() == 0);
    }

    /**
     * 去掉字符串两端的空白字符。因为String类里边的trim()方法不能出现null.trim()的情况，因此这里重新写一个工具方法。
     *
     * @param str 要去掉空白的字符串。
     * @return String 返回去掉空白后的字符串。如果字符串为null，则返回null；否则返回str.trim()。 *
     */
    public static String trim(String str) {

        return str == null ? str : str.trim();
    }

    /**
     * 更具配置的string.xml 里的id，得到内容
     *
     * @param context
     * @param id
     * @return String
     */
    public static String getValueById(Context context, int id) {
        return context.getResources().getString(id);
    }

    /**
     * 用于文中强制换行的处理
     *
     * @param oldstr
     * @return String
     */
    public static String replaceStr(String oldstr) {
        oldstr = oldstr.replaceAll("\n", "<br>");// 替换换行
        oldstr = oldstr.replaceAll("\r\n", "<br>");// 替换回车换行
        oldstr = oldstr.replaceAll(" ", "&nbsp;" + " ");// 替换空格
        return oldstr;
    }

    /**
     * 判断是否是数字
     *
     * @param c
     * @return boolean
     */
    public static boolean isNum(char c) {
        if (c >= 48 && c <= 57) {
            return true;
        }
        return false;
    }

    /**
     * 获得题号 例如：2.本文选自哪篇文章？ 提取题号中的数字 2
     *
     * @param content
     * @return int
     */
    public static int getThemeNum(String content) {
        int tnum = -1;
        if (isNullOrEmpty(content))
            return tnum;
        int a = content.indexOf(".");
        if (a > 0) {
            String num = content.substring(0, a);
            try {
                tnum = Integer.parseInt(num);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return tnum;
            }
        }
        return tnum;
    }

    // 添加自己的字符串操作

    public static String dealDigitalFlags(String str) {
        String result = "";
        if (str == null || str.length() < 0) {
            return null;
        } else {
            int len = str.length();
            for (int i = 0; i < len; i++) {
                String tmp = str.substring(i, i + 1);
                if (tmp.equals("+") || tmp.equals("*") || tmp.equals("=")) {
                    tmp = " " + tmp + " ";
                }
                result += tmp;
            }
        }
        return result;
    }

    /**
     * 截取序号 例如：01026---->26
     *
     * @param oldnum
     * @return String
     */
    public static String detailNum(String oldnum) {
        if (isNullOrEmpty(oldnum))
            return oldnum;
        int newnum = Integer.parseInt(oldnum);
        return newnum + ".";
    }

    public static String[] getStoreArr(String[] arr) throws Exception {
        String temp;
        for (int i = 0; i < arr.length; i++) {
            for (int j = arr.length - 1; j > i; j--) {
                int a = Integer.parseInt(arr[i]);
                int b = Integer.parseInt(arr[j]);
                if (a > b) {
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }

    /**
     * 给数字字符串排序 如：3，1，2 --->1，2，3
     *
     * @param str
     * @return String
     * @throws Exception
     */
    public static String resetStoreNum(String str) {
        String value = "";
        try {
            if (str == null || str.length() < 1)
                return value;
            String[] results = str.split(",");
            String[] newarr = getStoreArr(results);
            for (int i = 0; i < newarr.length; i++) {
                value += newarr[i] + ",";
            }
            value = value.substring(0, value.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 判断数组中是否包含某个值
     *
     * @param srcValue
     * @param values
     * @return boolean
     */
    public static boolean arrIsValue(String srcValue, String[] values) {
        if (values == null) {
            return false;
        }
        for (String value : values) {
            if (value.equals(srcValue)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获得"."之后的所有内容
     *
     * @param content 原字符串
     * @return String
     */
    public static String DeleteOriNumber(String content) {
        if (content.trim().length() > 1) {
            int index = content.indexOf(".");
            String AfterStr = content.substring(index + 1, content.length());
            return AfterStr;
        } else {
            return content;
        }
    }

    /**
     * GBK编码
     *
     * @param content
     * @return String
     */
    public static String convertToGBK(String content) {
        if (!isEmpty(content)) {
            try {
                content = new String(content.getBytes(), "GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    private static String trimSpaces(String IP) {// 去掉IP字符串前后所有的空格
        while (IP.startsWith(" ")) {
            IP = IP.substring(1, IP.length()).trim();
        }
        while (IP.endsWith(" ")) {
            IP = IP.substring(0, IP.length() - 1).trim();
        }
        return IP;
    }

    /**
     * 判断是否是一个IP
     *
     * @param IP
     * @return boolean
     */
    public static boolean isIp(String IP) {
        boolean b = false;
        IP = trimSpaces(IP);
        if (IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            String s[] = IP.split("\\.");
            if (Integer.parseInt(s[0]) < 255)
                if (Integer.parseInt(s[1]) < 255)
                    if (Integer.parseInt(s[2]) < 255)
                        if (Integer.parseInt(s[3]) < 255)
                            b = true;
        }
        return b;
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return Date
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 方法: distanceSize
     * 描述: 计算距离
     *
     * @param distance 距离数 单位千米
     * @return String  转换后的距离
     */
    public static String distanceSize(double distance) {
        if (distance < 1.0) return (int) (distance * 1000) + "m";
        String dd = "0";
        try {
            DecimalFormat fnum = new DecimalFormat("##0.00");
            dd = fnum.format(distance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dd + "km";
    }


    /**
     * 方法: replaceResult
     * 描述: 替换结果字符串
     *
     * @param content
     * @return String    返回类型
     */
    public static String replaceResult(String content) {
        if (!isEmpty(content))
            return content = content.replace("\\", "").replace("\"{", "{").replace("}\"", "}");
        return content;
    }

    /**
     * 方法: checkPhone
     * 描述: 提取电话号码
     *
     * @param content
     * @return ArrayList<String>    返回类型
     */
    public static ArrayList<String> checkPhone(String content) {
        ArrayList<String> list = new ArrayList<String>();
        if (isEmpty(content)) return list;
        Pattern p = Pattern.compile("1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");
        Matcher m = p.matcher(content);
        while (m.find()) {
            list.add(m.group());
        }
        return list;
    }

    /**
     * <p>描述:保留一位小数</p>
     *
     * @param value
     * @return 设定文件
     */
    public static String parseStr(String value) {
        if (StringUtil.isNullString(value)) return "0.0";
        DecimalFormat df = new DecimalFormat("######0.0");
        double mvalue = Double.parseDouble(value);
        return df.format(mvalue);
    }

    public static String parseStr2(String value) {
        if (StringUtil.isNullString(value)) return "--";
        DecimalFormat df = new DecimalFormat("######0.0");
        double mvalue = Double.parseDouble(value);
        String mStr = df.format(mvalue);
        if (mStr.equals("0") || mStr.equals("0.0")) {
            return "--";
        }
        return mStr;
    }

    public static String parseStr(double value) {
        if (value == 0) return "0.0";
        DecimalFormat df = new DecimalFormat("######0.0");
        return df.format(Double.parseDouble(String.valueOf(value)));
    }

    private StringUtil() {
    } // don't instantiate

    /**
     * 处理自动换行问题
     *
     * @param input 字符串
     * @return 设定文件
     */
    public static String ToWrap(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 时间显示转换
     *
     * @param duration   时间区间 0-59
     * @param isShowZero 小于10是否显示0 如：09
     * @return
     */
    public static String durationShow(int duration, boolean isShowZero) {
        String showStr = "";
        if (isShowZero) {
            if (duration < 10) {
                showStr = "0" + String.valueOf(duration);
            } else {
                showStr = String.valueOf(duration);
            }
        } else {
            showStr = String.valueOf(duration);
        }
        return showStr;
    }

    public static long fromTimeString(String s) {
        if (s.lastIndexOf(".") != -1) {
            s = s.substring(0, s.lastIndexOf("."));
        }
        String[] split = s.split(":");
        if (split.length == 3) {
            return Long.parseLong(split[0]) * 3600L + Long.parseLong(split[1]) * 60L + Long.parseLong(split[2]);
        } else if (split.length == 2) {
            return Long.parseLong(split[0]) * 60L + Long.parseLong(split[0]);
        } else {
            throw new IllegalArgumentException("Can\'t parse time string: " + s);
        }
    }

    public static String toTimeString(long seconds) {
        seconds = seconds / 1000;
        long hours = seconds / 3600L;
        long remainder = seconds % 3600L;
        long minutes = remainder / 60L;
        long secs = remainder % 60L;
        if (hours == 0) {
            return (minutes < 10L ? "0" : "") + minutes + ":" + (secs < 10L ? "0" : "") + secs;
        }
        return (hours < 10L ? "0" : "") + hours + ":" + (minutes < 10L ? "0" : "") + minutes + ":" + (secs < 10L ?
                "0" : "") + secs;
    }

    /**
     * 是否含有表情符
     * false 为含有表情符
     */
    public static boolean checkFace(String checkString) {
        String reg = "^([a-z]|[A-Z]|[0-9]|[\u0000-\u00FF]|[\u2000-\uFFFF]){1,}$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(checkString.replaceAll(" ", ""));
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 字节数组转换成Mac地址
     */
    public static String byteToMac(byte[] resBytes) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < resBytes.length; i++) {
            String hex = Integer.toHexString(resBytes[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            buffer.append(hex.toUpperCase());
        }
        return buffer.toString();
    }

    /**
     * 字节数据转换成十六进制字符串
     *
     * @param b
     * @return
     */
    public static String toHexString(byte[] b) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; ++i) {
            String s = Integer.toHexString(b[i] & 0xFF);
            if (s.length() == 1) {
                s = "0" + s;
            }
            buffer.append(s + " ");
        }
        return buffer.toString();
    }

    /**
     * 获取设备类型
     *
     * @param str
     * @return
     */
    public static String[] getDeviceType(String str) {
        String[] type = new String[]{"-1", "-1"};
        if (str == null)
            return type;
        if (str.length() >= 3) {
            String sub = str.substring(str.length() - 3, str.length());
            if (sub.contains("-")) {
                type = sub.split("-");
                if (!isNum(type[0])) {
                    type[0] = "-1";
                }
                if (!isNum(type[1])) {
                    type[1] = "-1";
                }
            } else {
                return null;
            }
        }
        return type;
    }

    public static String[] getDeviceType1(String str) {
        String[] type = new String[]{"-1", "-1"};
        if (isNull(str))
            return type;
        if (str.contains("-")) {
            String[] tmp = str.split("-");
            int pos1 = tmp.length - 2;
            if (pos1 >= 0) {
                if (isNum(tmp[pos1])) {
                    type[0] = tmp[pos1];
                }
            }
            int pos2 = tmp.length - 1;
            if (pos2 > 0) {
                if (isNum(tmp[pos2])) {
                    type[1] = tmp[pos2];
                }
            }
        } else {
            return null;
        }
        return type;
    }

    /**
     * 字节数组转为16进制字符串
     *
     * @param bytes 字节数组
     * @return 16进制字符串
     */
    public static String byteArrayToHexString(byte[] bytes) {
        @SuppressWarnings("resource")
        Formatter fmt = new Formatter(new StringBuilder(bytes.length * 2));
        for (byte b : bytes) {
            fmt.format("%02x", b);
        }
        return fmt.toString();
    }

    /**
     * 判断字符串是否为空或空字符
     *
     * @param strSource 源字符串
     * @return true表示为空，false表示不为空
     */
    public static boolean isNull(final String strSource) {
        return strSource == null || "".equals(strSource.trim());
    }

    /**
     * 判断是否为正确的邮箱格式
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))" +
                "([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 判断参数是否为数字
     *
     * @param strNum 待判断的数字参数
     */
    public static boolean isNum(final String strNum) {
        return strNum.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    /**
     * 判断参数是否为手机号.
     */
    public static boolean isPhoneNum(final String strPhoneNum) {
        return Pattern.matches(PHONE_FORMAT, strPhoneNum);
    }

    /**
     * 判断给定字符是Ascill字符还是其它字符(如汉，日，韩文字符)
     */
    public static boolean isLetter(final char c) {
        int k = 0xFF;
        if (c / k == 0) {
            return true;
        }
        return false;
    }

    /**
     * 计算字符的长度  Ascii字符算一个长度 非Ascii字符算两个长度
     */
    public static int getCharLength(final char c) {
        if (StringUtil.isLetter(c)) {
            return 1;
        }
        return 2;
    }

    /**
     * 获取字符串的长度,
     */
    public static int getStringLength(final String strSource) {
        int iSrcLen = 0;
        char[] arrChars = strSource.toCharArray();
        for (char arrChar : arrChars) {
            iSrcLen += StringUtil.getCharLength(arrChar);
        }
        return iSrcLen;
    }

    /**
     * 截取字符串，若参数strSuffix不为null，则加上该参数作为后缀
     *
     * @param strSource 原始字符串
     * @param iSubLen   截取的长度
     * @param strSuffix 后缀字符串，null表示不需要后缀
     * @return 截取后的字符串
     */
    public static String sub(final String strSource, final int iSubLen,
                             final String strSuffix) {
        if (StringUtil.isNull(strSource)) {
            return strSource;
        }
        String strFilter = strSource.trim(); // 过滤首尾空字符
        int iLength = StringUtil.getStringLength(strFilter); // 字符的长度
        if (iLength <= iSubLen) {
            return strFilter; // 字符长度小于待截取的长度
        }
        int iNum = iSubLen; // 可截取字符的数量
        int iSubIndex = 0; // 截取位置的游标
        char[] arrChars = strFilter.toCharArray();
        int iArrLength = arrChars.length;
        char c = arrChars[iSubIndex];
        StringBuffer sbContent = new StringBuffer();
        iNum -= StringUtil.getCharLength(c);
        while (iNum > -1 && iSubIndex < iArrLength) {
            ++iSubIndex;
            sbContent.append(c);
            if (iSubIndex < iArrLength) {
                c = arrChars[iSubIndex];
                iNum -= StringUtil.getCharLength(c);
            }
        }
        strFilter = sbContent.toString();
        if (!StringUtil.isNull(strSuffix)) {
            strFilter += strSuffix;
        }
        return strFilter;
    }

    /**
     * 截取字符串，长度超出的部分用省略号替代
     *
     * @param strSource 原始字符串
     * @param iSubLen   截取的长度
     * @return 截取后的字符串
     */
    public static String subWithDots(final String strSource, final int iSubLen) {
        return StringUtil.sub(strSource, iSubLen, "...");
    }

    public static String object2Str(Object obj) {
        String result = null;
        if (obj != null) {
            result = (String) obj;
        }

        return result;
    }

    public static byte[] getBytes(String src, Charset charSet) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            try {
                return src.getBytes(charSet.name());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return src.getBytes(charSet);
        }
    }

}
