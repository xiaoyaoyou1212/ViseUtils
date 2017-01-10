package com.vise.utils.convert;

public class NumberUtil {

    public static int convertToint(String intStr, int defValue) {
        try {
            return Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defValue;
    }

    public static long convertTolong(String longStr, long defValue) {
        try {
            return Long.parseLong(longStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defValue;
    }

    public static float convertTofloat(String fStr, float defValue) {
        try {
            return Float.parseFloat(fStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defValue;
    }

    public static double convertTodouble(String dStr, double defValue) {
        try {
            return Double.parseDouble(dStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defValue;
    }


    public static Integer convertToInteger(String intStr) {
        try {
            return Integer.valueOf(intStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Long convertToLong(String longStr) {
        try {
            return Long.valueOf(longStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Float convertToFloat(String fStr) {
        try {
            return Float.valueOf(fStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Double convertToDouble(String dStr) {
        try {
            return Double.valueOf(dStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

}
