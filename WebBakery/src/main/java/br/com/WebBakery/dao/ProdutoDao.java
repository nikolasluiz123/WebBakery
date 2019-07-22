package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Produto;

@Stateless
public class ProdutoDao extends AbstractBaseDao<Produto> {

    private static final long serialVersionUID = 8719276490177777230L;

    public ProdutoDao(EntityManager em) {
        this.em = em;
    }

    public ProdutoDao() {
    }

    public List<Produto> listarTodos(Boolean ativo) {
        List<Produto> produtos = new ArrayList<>();

        produtos = em.createQuery("SELECT p FROM Produto p WHERE p.ativo = :pAtivo", Produto.class)
                .setParameter("pAtivo", ativo).getResultList();

        return produtos;
    }

    @Override
    public Class<?> getModelClass() {
        return Produto.class;
    }
}
