package br.com.WebBakery.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email_Util {

    public static boolean EhValido(String email) {

        String expression = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        }

        return false;
    }
}
