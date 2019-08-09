package br.com.WebBakery.abstractClass;

import javax.faces.context.FacesContext;

import br.com.WebBakery.interfaces.IBaseMBean;
import br.com.WebBakery.util.Faces_Util;

@SuppressWarnings("serial")
public abstract class AbstractBaseMBean implements IBaseMBean {

    @Override
    public FacesContext getContext() {
        return Faces_Util.getFacesContext();
    }
    
    
}
