package br.com.WebBakery.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Date_Util {

    public static String formatToString(String pattern, Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());

        return dateFormat.format(date);
    }

    public static Date formatDateToTime(String pattern, Date date) throws ParseException {
        DateFormat timeInstance = SimpleDateFormat.getTimeInstance(DateFormat.DEFAULT);
        
        if (date != null) {
            String timeFormated = timeInstance.format(date);
            return timeInstance.parse(timeFormated);
        }

        return null;
    }
    
    public static Date sum(Date data1, Date data2) throws ParseException {
        Calendar dataInicio = Calendar.getInstance(Locale.getDefault());
        dataInicio.setTime(data1);
        
        Calendar dataFim = Calendar.getInstance(Locale.getDefault());
        dataFim.setTime(data2);
        
        dataInicio.add(Calendar.HOUR, dataFim.get(Calendar.HOUR));
        dataInicio.add(Calendar.MINUTE, dataFim.get(Calendar.MINUTE));
        
        return dataInicio.getTime();
    }

}
