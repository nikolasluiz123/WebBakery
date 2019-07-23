package br.com.WebBakery.abstractClass;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import br.com.WebBaker.interfaces.IBaseMBean;
import br.com.WebBakery.util.Faces_Util;

@SuppressWarnings("serial")
public abstract class AbstractBaseMBean<T> implements IBaseMBean {

    @PostConstruct
    public abstract void init();

    @Override
    public void initializer() {
        init();
    }
    
    public FacesContext getContext() {
        return Faces_Util.getFacesContext();
    }
}
