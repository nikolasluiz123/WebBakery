package br.com.WebBakery.abstractClass;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;

import br.com.WebBakery.util.Faces_Util;

@SuppressWarnings("serial")
public abstract class AbstractBaseRegisterMBean<T extends AbstractBaseTO>
        extends AbstractBaseMBean {

    protected static final String RECORD_UPDATED_SUCCESSFULLY = "Registro atualizado com sucesso!";;
    protected static final String RECORD_REGISTERED_SUCCESSFULLY = "Registro cadastrado com sucesso!";

    private List<AbstractValidator> validatorChain = new ArrayList<>();

    private T to;

    protected abstract AbstractBaseDao<T> getDao();

    protected abstract T getNewInstaceTO();

    protected void resetTo() {
        this.to = getNewInstaceTO();
    }

    protected void verificaObjetoSessao() {
        try {
            setTo(getObjetoSessao(getKeyAtribute(), getDao()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void atualizarTela() {
        boolean messagesIsEmpty = true;

        for (AbstractValidator validator : validatorChain) {
            if (!validator.messages.isEmpty()) {
                messagesIsEmpty = false;
            }
        }

        if (messagesIsEmpty) {
            resetTo();
        } else {
            showMessagesValidatorChain();
        }
    }

    public T getObjetoSessao(String keyAtribute, AbstractBaseDao<T> dao) throws Exception {
        Integer id = (Integer) Faces_Util.getHTTPSession().getAttribute(keyAtribute);
        
        if (id != null && dao != null) {
            Faces_Util.getHTTPSession().removeAttribute(keyAtribute);
            return dao.buscarPorId(id);
        }
        return null;
    }

    protected void showMessageSuccess() {
        if (getTo().getId() == null) {
            getContext().addMessage(null, new FacesMessage(RECORD_REGISTERED_SUCCESSFULLY));
        } else {
            getContext().addMessage(null, new FacesMessage(RECORD_UPDATED_SUCCESSFULLY));
        }
    }

    protected void showMessage(String message) {
        getContext().addMessage(null, new FacesMessage(message));
    }

    public T getTo() {
        return to;
    }

    public void setTo(T to) {
        this.to = to;
    }

    protected void addValidator(AbstractValidator validator) {
        if (!validatorChain.contains(validator)) {
            this.validatorChain.add(validator);
        }
    }
    
    protected void addValidators(AbstractValidator... validators) {
        for (AbstractValidator validator : validators) {
            if (!validatorChain.contains(validator)) {
                this.validatorChain.add(validator);
            }
        }
    }

    protected void showMessagesValidatorChain() {
        for (AbstractValidator validator : validatorChain) {
            if (!validator.messages.isEmpty()) {
                validator.showMessages();
            }
        }

        removeValidatorsOfValidatorChain();
    }

    private void removeValidatorsOfValidatorChain() {
        validatorChain.clear();
    }

    protected boolean isValid() {
        boolean isValid = true;
        for (AbstractValidator validator : validatorChain) {
            if (!validator.isValid()) {
                isValid = false;
            }
        }
        return isValid;
    }
}
