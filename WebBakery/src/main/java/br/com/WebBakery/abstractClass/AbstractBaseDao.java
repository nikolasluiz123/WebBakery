package br.com.WebBakery.abstractClass;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBakery.core.TransferObjectConverter;
import br.com.WebBakery.interfaces.IBaseDao;

@SuppressWarnings("serial")
public abstract class AbstractBaseDao<T> implements IBaseDao<T> {

    @PersistenceContext
    EntityManager entityManager;

    private TransferObjectConverter converter;
    
    protected static final String QR_NL = "\n";

    public AbstractBaseDao() {
        this.converter = new TransferObjectConverter();
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected TransferObjectConverter getConverter() {
        return converter;
    }
}
