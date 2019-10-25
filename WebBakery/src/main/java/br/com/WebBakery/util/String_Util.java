package br.com.WebBakery.util;

import java.text.NumberFormat;
import java.util.Locale;

public class String_Util {

    public static String formatDoubleToMonetaryValue(Double value) {
        NumberFormat formater = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        if (value != null) {
            return formater.format(value);
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
        return value == null || value.trim().length() == 0;
    }
}
