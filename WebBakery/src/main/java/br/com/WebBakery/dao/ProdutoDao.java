package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBaker.interfaces.IBaseDao;
import br.com.WebBakery.model.Produto;

@Stateless
public class ProdutoDao implements IBaseDao<Produto> {

    private static final long serialVersionUID = 8719276490177777230L;

    @PersistenceContext
    private EntityManager em;

    public ProdutoDao(EntityManager em) {
        this.em = em;
    }

    public ProdutoDao() {
    }

    @Override
    public void cadastrar(Produto model) {
        em.persist(model);
    }

    @Override
    public List<Produto> listarTodos(Boolean ativo) {
        List<Produto> produtos = new ArrayList<>();

        produtos = em.createQuery("SELECT p FROM Produto p WHERE p.ativo = :pAtivo", Produto.class)
                .setParameter("pAtivo", ativo).getResultList();

        return produtos;
    }

    @Override
    public Produto buscarPorId(Integer id) {
        return em.find(Produto.class, id);
    }

    @Override
    public void atualizar(Produto model) {
        em.merge(model);
    }
}
