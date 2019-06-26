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

    public boolean existe(Integer id) {
        try {
            em.createQuery("SELECT ep FROM EstoqueProduto ep WHERE ep.produto.id = :pId ORDER BY ep.produto.descricao",
                           EstoqueProduto.class)
                    .setParameter("pId", id).getSingleResult();
        } catch (NoResultException e) {
            return false;
        }
        return true;
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

}
