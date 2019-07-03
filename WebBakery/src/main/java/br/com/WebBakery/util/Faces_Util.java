package br.com.WebBakery.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class Faces_Util {

    public static HttpSession getHTTPSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
                .getSession(false);
    }
}
