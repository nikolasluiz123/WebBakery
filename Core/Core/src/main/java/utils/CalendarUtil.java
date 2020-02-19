package utils;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class CalendarUtil {

    private static Locale locale = Locale.getDefault();
    private static TimeZone zone = TimeZone.getDefault();
    
    public static Calendar getInstanceWhithLocale() {
        return Calendar.getInstance(zone, locale);
    }
    
    public static Calendar getInstanceWhithLocale(Integer day, Integer month, Integer year) {
        int monthAjusted = month - 1;
        Calendar instance = Calendar.getInstance(zone, locale);
        instance.set(year, monthAjusted, day);
        return instance;
    }
}
