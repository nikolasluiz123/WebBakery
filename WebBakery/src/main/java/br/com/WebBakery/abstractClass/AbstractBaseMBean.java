package br.com.WebBakery.abstractClass;

import javax.faces.context.FacesContext;

import br.com.WebBakery.interfaces.IBaseMBean;
import br.com.WebBakery.util.Faces_Util;

@SuppressWarnings("serial")
public abstract class AbstractBaseMBean implements IBaseMBean {

    protected abstract String getBeanName();

    @Override
    public FacesContext getContext() {
        return Faces_Util.getFacesContext();
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
