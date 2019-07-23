package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.ProdutoVenda;

@Stateless
public class ProdutoVendaDao extends AbstractBaseDao<ProdutoVenda> {
    @PersistenceContext
    transient private EntityManager entityManager;
    
    @Override
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }
    private static final long serialVersionUID = -1109157795344801242L;

    public List<ProdutoVenda> buscarPorIdVenda(Integer IdVenda) {
        List<ProdutoVenda> produtoVenda = new ArrayList<>();

        produtoVenda = getEntityManager()
                .createQuery("SELECT pv FROM ProdutoVenda pv WHERE pv.venda.id = :pIdVenda",
                             ProdutoVenda.class)
                .setParameter("pIdVenda", IdVenda).getResultList();

        return produtoVenda;
    }

    @Override
    public List<ProdutoVenda> listarTodos(Boolean ativo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Class<?> getModelClass() {
        return ProdutoVenda.class;
    }

}
