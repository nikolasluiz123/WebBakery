package br.com.WebBakery.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Date_Util {

    public static String formatar(String pattern, Date data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(data);
    }

}
