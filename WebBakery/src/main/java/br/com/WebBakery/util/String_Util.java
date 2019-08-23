package br.com.WebBakery.util;

public class String_Util {

    public static String formatDoubleToValueMonetary(Double value) {
        if (value != null) {
            String s = value.toString().replace(".", ",");
            return "R$ ".concat(s);
        }
        return "";
    }

    public static String formatDoubleToValueDecimalBR(Double value) {
        if (value != null) {
            String s = value.toString().replace(".", ",");
            return s;
        }
        return "";
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
