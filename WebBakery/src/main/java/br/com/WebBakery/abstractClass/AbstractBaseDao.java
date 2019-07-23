package br.com.WebBakery.abstractClass;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBaker.interfaces.IBaseDao;

@SuppressWarnings("serial")
public abstract class AbstractBaseDao<T> implements IBaseDao<T> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void cadastrar(T model) {
        entityManager.persist(model);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T buscarPorId(Integer id) {
        return (T) entityManager.find(getModelClass(), id);
    }

    @Override
    public void atualizar(T model) {
        entityManager.merge(model);
    }

    public abstract Class<?> getModelClass();

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager em) {
        this.entityManager = em;
    }

}
