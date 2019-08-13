package br.com.WebBakery.abstractClass;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import br.com.WebBakery.util.Faces_Util;

@SuppressWarnings("serial")
public abstract class AbstractBaseListMBean extends AbstractBaseMBean {

    protected static final String RECORD_INATIVATED_SUCCESSFULLY = "Registro inativado com sucesso!";
    
    public void carregar(Integer id, String keyAtribute, String pageRedirect) {
        setObjetoSessao(id, keyAtribute, pageRedirect);
    }
    
    public void setObjetoSessao(Integer id, String keyAtribute, String pageRedirect) {
        try {
            HttpSession session = Faces_Util.getHTTPSession();
            session.setAttribute(keyAtribute, id);
            getContext().getExternalContext().redirect(pageRedirect);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
