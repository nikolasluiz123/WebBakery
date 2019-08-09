package br.com.WebBakery.util;

public class String_Util {

    public static String formatarDoubleParaValorMonetario(Double value) {
        if (value != null) {
            String s = value.toString().replace(".", ",");
            return "R$ ".concat(s);
        }
        return "";
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
