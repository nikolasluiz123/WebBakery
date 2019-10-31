package br.com.WebBakery.abstractClass;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.WebBakery.interfaces.IBaseMBean;
import br.com.WebBakery.to.TOUsuario;
import br.com.WebBakery.util.Faces_Util;

@SuppressWarnings("serial")
public abstract class AbstractBaseMBean implements IBaseMBean {

    private static final String USER_IDENTIFIER_SESSION_KEY = "usuarioLogado";
    
    protected abstract String getBeanName();

    @Override
    public FacesContext getContext() {
        return Faces_Util.getFacesContext();
    }

    @Override
    public TOUsuario getUserSession() {
        HttpSession session = Faces_Util.getHTTPSession();
        return (TOUsuario) session.getAttribute(USER_IDENTIFIER_SESSION_KEY);
    }
    
    protected String getKeyAtribute() {
        String beanName = getBeanName().substring(0, 1).toUpperCase()
                .concat(getBeanName().substring(1));
        int length = beanName.length();
        int lengthSemBean = length - 4;
        String name = beanName.substring(0, lengthSemBean);
        return name + "ID";
    }

}
