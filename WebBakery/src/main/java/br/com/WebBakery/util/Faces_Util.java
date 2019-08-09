package br.com.WebBakery.util;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
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

    public static Object getBean(String ref) {
        FacesContext facesContext = Faces_Util.getFacesContext();
        if (facesContext != null) {
            ELContext elContext = facesContext.getELContext();
            if (elContext != null) {
                ExpressionFactory factory = facesContext.getApplication().getExpressionFactory();
                if (factory != null) {
                    return factory.createValueExpression(elContext, "#{" + ref + "}", Object.class)
                            .getValue(elContext);
                }
            }
        }
        return null;
    }

}
