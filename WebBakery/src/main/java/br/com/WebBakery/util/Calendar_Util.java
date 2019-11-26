package br.com.WebBakery.util;

import java.util.Calendar;
import java.util.Locale;

public class Calendar_Util {

    private static Locale locale = Locale.getDefault();
    
    public static Calendar getInstanceWhithLocale() {
        return Calendar.getInstance(locale);
    }
    
    public static Calendar getInstanceWhithLocale(Integer day, Integer month, Integer year) {
        Calendar instance = Calendar.getInstance(locale);
        instance.set(year, month, day);
        return instance;
    }
}
