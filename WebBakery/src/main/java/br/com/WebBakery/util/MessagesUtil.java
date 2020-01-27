package br.com.WebBakery.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

public class MessagesUtil {

    public static void showMessage(Severity severity, String message) {
        if (severity == null) {
            throw new IllegalArgumentException("A severidade não pode ser null."); 
        }
        
        if (StringUtil.isNullOrEmpty(message)) {
           throw new IllegalArgumentException("A mensagem não pode ser vazia ou null."); 
        }
        
        FacesMessage facesMessage = new FacesMessage(severity, message, message);
        FacesUtil.getFacesContext().addMessage(null, facesMessage);
    }
}
