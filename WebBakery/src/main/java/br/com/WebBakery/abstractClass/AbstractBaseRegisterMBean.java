package br.com.WebBakery.abstractClass;

import br.com.WebBakery.util.Faces_Util;

@SuppressWarnings("serial")
public abstract class AbstractBaseRegisterMBean<T> extends AbstractBaseMBean<T> {

    public T getObjetoSessao(String keyAtribute, AbstractBaseDao<T> dao, AbstractBaseModel model) {
        Integer id = (Integer) Faces_Util.getHTTPSession().getAttribute(keyAtribute);
        if (id != null) {
            Faces_Util.getHTTPSession().removeAttribute(keyAtribute);
            return dao.buscarPorId(id);
        }
        return null;
    }

}
