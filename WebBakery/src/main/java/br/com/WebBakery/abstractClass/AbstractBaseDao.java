package br.com.WebBakery.abstractClass;

import javax.persistence.EntityManager;

import br.com.WebBaker.interfaces.IBaseDao;

@SuppressWarnings("serial")
public abstract class AbstractBaseDao<T> implements IBaseDao<T> {

    protected abstract EntityManager getEntityManager();
    
    public abstract Class<?> getModelClass();

    @Override
    public void cadastrar(T model) {
        getEntityManager().persist(model);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T buscarPorId(Integer id) {
        return (T) getEntityManager().find(getModelClass(), id);
    }

    @Override
    public void atualizar(T model) {
        getEntityManager().merge(model);
    }
}
