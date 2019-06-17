package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBaker.interfaces.IBaseDao;
import br.com.WebBakery.model.EstoqueProduto;

@Stateless
public class EstoqueProdutoDao implements IBaseDao<EstoqueProduto> {

    private static final long serialVersionUID = -2808765073810346192L;

    @PersistenceContext
    private EntityManager em;

    public EstoqueProdutoDao(EntityManager em) {
        this.em = em;
    }

    public EstoqueProdutoDao() {
    }

    @Override
    public void cadastrar(EstoqueProduto model) {
        em.persist(model);
    }

    @Override
    public EstoqueProduto buscarPorId(Integer id) {
        return em.find(EstoqueProduto.class, id);
    }

    public void atualizar(EstoqueProduto estoque, Integer qtd) {
        estoque.setQuantidade(estoque.getQuantidade() + qtd);
        em.merge(estoque);
    }

    public List<EstoqueProduto> listarTodos() {
        List<EstoqueProduto> estoque = new ArrayList<>();

        estoque = em
                .createQuery("SELECT ep FROM EstoqueProduto ep WHERE 1=1 ORDER BY ep.produto.descricao",
                             EstoqueProduto.class)
                .getResultList();

        return estoque;
    }

    @Deprecated
    @Override
    public void atualizar(EstoqueProduto model) {
        // TODO Auto-generated method stub
    }

    @Deprecated
    @Override
    public List<EstoqueProduto> listarTodos(Boolean ativo) {
        // TODO Auto-generated method stub
        return null;
    }

}
