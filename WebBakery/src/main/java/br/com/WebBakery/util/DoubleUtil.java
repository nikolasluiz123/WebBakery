package br.com.WebBakery.util;

import java.text.DecimalFormat;

public class DoubleUtil {

    public static Double format(Double value) {
        DecimalFormat df = new DecimalFormat("#.##");

        Double formatted = 0.0;

        if (value != null) {
            formatted = Double.parseDouble(df.format(value.toString()));
        }

        return formatted;
    }
}
