package br.com.WebBakery.abstractClass;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import br.com.WebBakery.util.Faces_Util;

@SuppressWarnings("serial")
public abstract class AbstractBaseListMBean<T> extends AbstractBaseMBean<T> {

    public void setObjetoSessao(Integer id, String keyAtribute, String pageRedirect) {
        HttpSession session = Faces_Util.getHTTPSession();
        session.setAttribute(keyAtribute, id);
        try {
            getContext().getExternalContext().redirect(pageRedirect);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
