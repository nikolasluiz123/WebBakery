package br.com.WebBakery.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class String_Util {

    public static String formatToMonetaryValue(Number value) {
        NumberFormat formater = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        if (value != null) {
            return formater.format(value);
        }
        return "";
    }
    
    public static String formatTwoDecimalPlaces(Number value) {
        DecimalFormat df = new DecimalFormat("#.##");
        
        String formatted = "";
        
        if (value != null) {
            formatted = df.format(value); 
        }
        
        return formatted;
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }
}
