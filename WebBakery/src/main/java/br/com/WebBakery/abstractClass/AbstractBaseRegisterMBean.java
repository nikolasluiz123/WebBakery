package br.com.WebBakery.abstractClass;

import br.com.WebBakery.util.Faces_Util;

@SuppressWarnings("serial")
public abstract class AbstractBaseRegisterMBean<T extends AbstractBaseTO>
        extends AbstractBaseMBean {

    private T to;
    protected AbstractValidator validator;

    protected abstract AbstractBaseDao<T> getDao();

    protected abstract T getNewInstaceTO();

    protected abstract String getMsgInsert();

    protected abstract String getMsgUpdate();

    protected void resetTo() {
        this.to = getNewInstaceTO();
    }

    protected void atualizarTela() {
        resetTo();
        this.validator.showMessages();
        this.validator.clearMessages();
    }

    public T getObjetoSessao(String keyAtribute, AbstractBaseDao<T> dao) throws Exception {
        Integer id = (Integer) Faces_Util.getHTTPSession().getAttribute(keyAtribute);
        if (id != null) {
            Faces_Util.getHTTPSession().removeAttribute(keyAtribute);
            return dao.buscarPorId(id);
        }
        return null;
    }

    public T getTo() {
        return to;
    }

}
