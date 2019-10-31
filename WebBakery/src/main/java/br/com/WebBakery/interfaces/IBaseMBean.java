package br.com.WebBakery.interfaces;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import br.com.WebBakery.to.TOUsuario;

public interface IBaseMBean extends Serializable {

    public FacesContext getContext();
    
    public TOUsuario getUserSession();
    
}
