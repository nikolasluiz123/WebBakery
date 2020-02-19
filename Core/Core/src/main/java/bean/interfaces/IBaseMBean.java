package bean.interfaces;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import transferobject.TOUsuario;

public interface IBaseMBean extends Serializable {

    public FacesContext getContext();
    
    public TOUsuario getUserSession();
    
}
