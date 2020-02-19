package dao.abstractclasses;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dao.interfaces.IBaseDao;
import transferobject.converter.TransferObjectConverter;

@SuppressWarnings("serial")
public abstract class AbstractBaseDao<T> implements IBaseDao<T> {

    @PersistenceContext
    EntityManager entityManager;

    private TransferObjectConverter converter;
    
    protected static final String QR_NL = "\n";

    public AbstractBaseDao() {
        this.converter = new TransferObjectConverter();
    }
    
    public AbstractBaseDao(EntityManager entityManger) {
        this.converter = new TransferObjectConverter();
        this.entityManager = entityManger;
    }


    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected TransferObjectConverter getConverter() {
        return converter;
    }
}
