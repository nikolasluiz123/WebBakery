package br.com.WebBakery.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cep_Util {

    public static boolean EhValido(String cep) {
        String expression = "\\d{5}\\d{3}";

        if (cep.contains("-")) {
            expression = "\\d{5}[-]\\d{3}";
        } 
        
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(cep);
        
        return matcher.matches();
    }
}
