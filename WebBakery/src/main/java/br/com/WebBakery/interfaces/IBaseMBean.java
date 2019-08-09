package br.com.WebBakery.interfaces;

import java.io.Serializable;

import javax.faces.context.FacesContext;

public interface IBaseMBean extends Serializable {

    public FacesContext getContext();
    
}
