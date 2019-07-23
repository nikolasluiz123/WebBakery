package br.com.WebBakery.abstractClass;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBaker.interfaces.IBaseMBean;

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
