package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBaker.interfaces.IBaseDao;
import br.com.WebBakery.model.Receita;

@Stateless
public class ReceitaDao implements IBaseDao<Receita> {
    private static final long serialVersionUID = -6670901759193719186L;

    @PersistenceContext
    private EntityManager em;

    public ReceitaDao(EntityManager em) {
        this.em = em;
    }

    public ReceitaDao() {
    }

    @Override
    public void cadastrar(Receita model) {
        em.persist(model);
    }

    @Override
    public List<Receita> listarTodos(Boolean ativo) {
        List<Receita> receitas = new ArrayList<>();

        receitas = em.createQuery("SELECT r FROM Receita r WHERE r.ativo = :pAtivo", Receita.class)
                .setParameter("pAtivo", ativo).getResultList();

        return receitas;
    }

    @Override
    public Receita buscarPorId(Integer id) {
        return em.find(Receita.class, id);
    }

    @Override
    public void atualizar(Receita model) {
        em.merge(model);
    }
}
