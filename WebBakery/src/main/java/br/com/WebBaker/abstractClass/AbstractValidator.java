package br.com.WebBaker.abstractClass;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.WebBaker.interfaces.IValidator;

public abstract class AbstractValidator implements IValidator {

    protected List<String> messages = new ArrayList<>();

    public abstract void chamarValidacoes();

    @Override
    public boolean isValid() {

        chamarValidacoes();

        if (!this.getMessages().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void showMessages() {
        this.getMessages().forEach(message -> {
            FacesContext.getCurrentInstance()
                    .addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
        });
    }

    @Override
    public void clearMessages() {
        this.getMessages().clear();
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
