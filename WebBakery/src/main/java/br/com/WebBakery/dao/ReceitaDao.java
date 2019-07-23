package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Receita;

@Stateless
public class ReceitaDao extends AbstractBaseDao<Receita> {
    @PersistenceContext
    transient private EntityManager entityManager;
    
    @Override
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }
    private static final long serialVersionUID = -6670901759193719186L;

    public List<Receita> listarTodos(Boolean ativo) {
        List<Receita> receitas = new ArrayList<>();

        receitas = getEntityManager()
                .createQuery("SELECT r FROM Receita r WHERE r.ativo = :pAtivo", Receita.class)
                .setParameter("pAtivo", ativo).getResultList();

        return receitas;
    }

    @Override
    public Class<?> getModelClass() {
        return Receita.class;
    }
}
