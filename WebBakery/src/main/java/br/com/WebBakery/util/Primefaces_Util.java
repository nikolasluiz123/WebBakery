package br.com.WebBakery.util;

import org.primefaces.PrimeFaces;

public class Primefaces_Util {

    public static void executeScript(String Script) {
        PrimeFaces.current().executeScript(Script);
    }

    public static void executeScriptShowDialog(String widgetVarDialog) {
        PrimeFaces.current().executeScript("PF('" + widgetVarDialog + "').show();");
    }

    public static void executeScriptHideDialog(String widgetVarDialog) {
        PrimeFaces.current().executeScript("PF('" + widgetVarDialog + "').hide();");
    }

    public static void update(String... strings) {
        PrimeFaces.current().ajax().update(strings);
    }

}
