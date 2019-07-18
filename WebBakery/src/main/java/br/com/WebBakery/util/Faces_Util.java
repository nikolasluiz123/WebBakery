package br.com.WebBakery.util;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class Faces_Util {

    public static HttpSession getHTTPSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
                .getSession(false);
    }

    public static FacesContext getFacesContext() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return facesContext;
    }

    public static ExternalContext getExternalContext() {
        FacesContext facesContext = Faces_Util.getFacesContext();
        if (facesContext != null) {
            return facesContext.getExternalContext();
        }
        return null;
    }
}
