package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.EstoqueProduto;

@Stateless
public class EstoqueProdutoDao extends AbstractBaseDao<EstoqueProduto> {

    private static final long serialVersionUID = -2808765073810346192L;

    public EstoqueProdutoDao(EntityManager em) {
        this.em = em;
    }

    public EstoqueProdutoDao() {
    }

    public void atualizar(EstoqueProduto estoque, Integer qtd) {
        estoque.setQuantidade(estoque.getQuantidade() + qtd);
        em.merge(estoque);
    }

    public List<EstoqueProduto> listarTodos() {
        List<EstoqueProduto> estoque = new ArrayList<>();

        estoque = em
                .createQuery("SELECT ep FROM EstoqueProduto ep WHERE ep.quantidade >= 1 ORDER BY ep.produto.descricao",
                             EstoqueProduto.class)
                .getResultList();

        return estoque;
    }

    public EstoqueProduto existe(Integer produtoId) {
        try {
            return em
                    .createQuery("SELECT ep FROM EstoqueProduto ep WHERE ep.produto.id = :pId ",
                                 EstoqueProduto.class)
                    .setParameter("pId", produtoId).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public EstoqueProduto buscarEstoqueProduto(Integer produtoId) {
        EstoqueProduto estoque = new EstoqueProduto();

        estoque = em
                .createQuery("SELECT ep FROM EstoqueProduto ep WHERE ep.produto.id = :pProdutoId",
                             EstoqueProduto.class)
                .setParameter("pProdutoId", produtoId).getSingleResult();

        return estoque;
    }

    @Override
    public List<EstoqueProduto> listarTodos(Boolean ativo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Class<?> getModelClass() {
        return EstoqueProduto.class;
    }

}
