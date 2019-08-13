package br.com.WebBakery.util;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class Faces_Util {

    public static HttpSession getHTTPSession() {
        return (HttpSession) getExternalContext().getSession(false);
    }

    public static ExternalContext getExternalContext() {
        FacesContext facesContext = getFacesContext();
        if (facesContext != null) {
            return facesContext.getExternalContext();
        }
        return null;
    }

    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

}
