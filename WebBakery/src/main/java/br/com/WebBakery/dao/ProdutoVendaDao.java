package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.ProdutoVenda;

@Stateless
public class ProdutoVendaDao extends AbstractBaseDao<ProdutoVenda> {

    private static final long serialVersionUID = -1109157795344801242L;

    public ProdutoVendaDao(EntityManager em) {
        this.em = em;
    }

    public ProdutoVendaDao() {
    }

    public List<ProdutoVenda> buscarPorIdVenda(Integer IdVenda) {
        List<ProdutoVenda> produtoVenda = new ArrayList<>();

        produtoVenda = em
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

}
