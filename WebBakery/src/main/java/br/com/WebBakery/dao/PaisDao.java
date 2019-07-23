package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Pais;

@Stateless
public class PaisDao extends AbstractBaseDao<Pais> {
    @PersistenceContext
    transient private EntityManager entityManager;
    
    @Override
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }
    private static final long serialVersionUID = 1904464340270603917L;

    public List<Pais> listarTodos(Boolean ativo) {
        List<Pais> paises = new ArrayList<>();

        paises = getEntityManager().createQuery("SELECT p FROM Pais p WHERE p.ativo = :pAtivo", Pais.class)
                .setParameter("pAtivo", ativo).getResultList();

        return paises;
    }

    @Override
    public Class<?> getModelClass() {
        return Pais.class;
    }

}
