package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Venda;

@Stateless
public class VendaDao extends AbstractBaseDao<Venda> {
    @PersistenceContext
    transient private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    private static final long serialVersionUID = 3684593379387389702L;

    public List<Venda> listarTodos() {
        List<Venda> vendas = new ArrayList<>();
        vendas = getEntityManager().createQuery("SELECT v FROM Venda v", Venda.class)
                .getResultList();
        return vendas;
    }

    @Override
    public List<Venda> listarTodos(Boolean ativo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Class<?> getModelClass() {
        return Venda.class;
    }
}
