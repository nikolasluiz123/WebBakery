package br.com.WebBakery.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBakery.model.Produto;

@Stateless
public class ProdutoDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    public ProdutoDao(EntityManager em) {
        this.em = em;
    }

    public ProdutoDao() {
    }

    public void cadastrar(Produto produto) {
        em.persist(produto);
    }

    public List<Produto> listarTodos(Boolean ativo) {

        List<Produto> produtos = new ArrayList<>();

        produtos = em
                .createQuery("SELECT p FROM Produto p WHERE p.ativo = :pAtivo ORDER BY p.descricao",
                             Produto.class)
                .setParameter("pAtivo", ativo).getResultList();

        return produtos;
    }

    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();

        produtos = em.createQuery("SELECT p FROM Produto p WHERE 1=1 ORDER BY p.descricao",
                                  Produto.class)
                .getResultList();

        return produtos;
    }

    public Produto buscarPelaId(Integer id) {
        return em.find(Produto.class, id);
    }

    public void atualizar(Produto produto) {
        em.merge(produto);
    }

}
