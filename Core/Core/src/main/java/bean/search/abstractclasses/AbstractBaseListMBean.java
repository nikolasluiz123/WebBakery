package bean.search.abstractclasses;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import bean.abstractclasses.AbstractBaseMBean;
import utils.FacesUtil;

@SuppressWarnings("serial")
public abstract class AbstractBaseListMBean extends AbstractBaseMBean {

    protected static final String RECORD_INATIVATED_SUCCESSFULLY = "Registro inativado com sucesso!";
    
    public void carregar(Integer id, String keyAtribute, String pageRedirect) {
        setObjetoSessao(id, keyAtribute, pageRedirect);
    }
    
    public void setObjetoSessao(Integer id, String keyAtribute, String pageRedirect) {
        try {
            HttpSession session = FacesUtil.getHTTPSession();
            session.setAttribute(keyAtribute, id);
            getContext().getExternalContext().redirect(pageRedirect);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getRequestParameter(String key) {
        return FacesUtil.getExternalContext().getRequestParameterMap().get(key);
    }
}
