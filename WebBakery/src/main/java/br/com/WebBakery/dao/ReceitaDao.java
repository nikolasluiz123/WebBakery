package br.com.WebBakery.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBakery.model.Receita;

@Stateless
public class ReceitaDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    public ReceitaDao(EntityManager em) {
        this.em = em;
    }

    public ReceitaDao() {
    }

    public void cadastrar(Receita receita) {
        em.persist(receita);
    }

    public List<Receita> listarTodos(Boolean ativo) {

        List<Receita> receitas = new ArrayList<>();

        receitas = em
                .createQuery("SELECT r FROM Receita r WHERE r.ativo = :pAtivo ORDER BY r.descricao",
                             Receita.class)
                .setParameter("pAtivo", ativo).getResultList();

        return receitas;
    }

    public List<Receita> listarTodos() {
        List<Receita> receitas = new ArrayList<>();

        receitas = em.createQuery("SELECT r FROM Receita r WHERE 1=1 ORDER BY r.descricao",
                                  Receita.class)
                .getResultList();

        return receitas;
    }

    public Receita buscarPelaId(Integer id) {
        return em.find(Receita.class, id);
    }

    public void atualizar(Receita receita) {
        em.merge(receita);
    }
}
