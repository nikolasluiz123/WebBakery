package br.com.WebBakery.abstractClass;

import javax.faces.application.FacesMessage;

import br.com.WebBakery.util.Faces_Util;

@SuppressWarnings("serial")
public abstract class AbstractBaseRegisterMBean<T extends AbstractBaseTO>
        extends AbstractBaseMBean {

    protected static final String RECORD_UPDATED_SUCCESSFULLY = "Registro atualizado com sucesso!";; 
    protected static final String RECORD_REGISTERED_SUCCESSFULLY = "Registro cadastrado com sucesso!";

    private T to;

    protected abstract AbstractBaseDao<T> getDao();

    public abstract AbstractValidator getValidator();
    
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
        if (getValidator().messages.isEmpty()) {
            resetTo();
        }
        getValidator().showMessages();
        getValidator().clearMessages();
    }

    public T getObjetoSessao(String keyAtribute, AbstractBaseDao<T> dao) throws Exception {
        Integer id = (Integer) Faces_Util.getHTTPSession().getAttribute(keyAtribute);
        if (id != null) {
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

    public T getTo() {
        return to;
    }

    public void setTo(T to) {
        this.to = to;
    }

}
