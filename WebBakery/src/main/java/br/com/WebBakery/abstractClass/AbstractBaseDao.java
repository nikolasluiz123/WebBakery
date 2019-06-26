package br.com.WebBakery.abstractClass;

import javax.persistence.EntityManager;

import br.com.WebBaker.interfaces.IBaseDao;

@SuppressWarnings("serial")
public abstract class AbstractBaseDao<T> implements IBaseDao<T>{

    protected EntityManager em;
    
    @Override
    public void cadastrar(T model) {
        em.persist(model);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T buscarPorId(Class<?> clazz, Integer id) {
        return (T) em.find(clazz, id);
    }

    @Override
    public void atualizar(T model) {
        em.merge(model);
    }
}