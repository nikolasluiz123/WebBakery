package br.com.WebBakery.util;

public class String_Util {

    public static String formatarDoubleParaValorMonetario(Double valor) {
        if (valor != null) {
            String s = valor.toString().replace(".", ",");
            return "R$ ".concat(s);
        }
        return "";
    }
}
