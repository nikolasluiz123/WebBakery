package br.com.WebBakery.abstractClass;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBaker.interfaces.IBaseMBean;
import br.com.WebBakery.util.Faces_Util;

@SuppressWarnings("serial")
public abstract class AbstractBaseMBean<T> implements IBaseMBean {

    @PersistenceContext
    protected EntityManager em;
    @Inject
    transient protected FacesContext context;

    @PostConstruct
    public abstract void init();

    @Override
    public void initializer() {
        init();
    }
    
    public T getObjetoSessao(String keyAtribute, AbstractBaseDao<T> dao, AbstractBaseModel model) {
        Integer id = (Integer) Faces_Util.getHTTPSession().getAttribute(keyAtribute);
        if (id != null) {
            Faces_Util.getHTTPSession().removeAttribute("PaisID");
            return dao.buscarPorId(id);
        }
        return null;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public FacesContext getContext() {
        return context;
    }

    public void setContext(FacesContext context) {
        this.context = context;
    }

}
