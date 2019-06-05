package br.com.WebBakery.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBakery.model.EstoqueProduto;

@Stateless
public class EstoqueProdutoDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    public EstoqueProdutoDao(EntityManager em) {
        this.em = em;
    }

    public EstoqueProdutoDao() {
    }

    public void cadastrar(EstoqueProduto estoqueProduto) {
        em.persist(estoqueProduto);
    }

    public List<EstoqueProduto> listarTodos() {
        List<EstoqueProduto> estoque = new ArrayList<>();

        estoque = em
                .createQuery("SELECT ep FROM EstoqueProduto ep WHERE 1=1 ORDER BY ep.produto.descricao",
                             EstoqueProduto.class)
                .getResultList();

        return estoque;
    }

    public EstoqueProduto buscarPelaId(Integer id) {
        return em.find(EstoqueProduto.class, id);
    }

    public void atualizar(EstoqueProduto estoque, Integer qtd) {
        estoque.setQuantidade(estoque.getQuantidade() + qtd);
        em.merge(estoque);
    }

}
