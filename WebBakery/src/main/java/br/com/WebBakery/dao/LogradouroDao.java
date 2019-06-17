package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBaker.interfaces.IBaseDao;
import br.com.WebBakery.model.Logradouro;

@Stateless
public class LogradouroDao implements IBaseDao<Logradouro> {

    private static final long serialVersionUID = -3918599275005523240L;

    @PersistenceContext
    private EntityManager em;

    public LogradouroDao(EntityManager em) {
        this.em = em;
    }

    public LogradouroDao() {
    }

    @Override
    public void cadastrar(Logradouro model) {
        em.persist(model);
    }

    @Override
    public List<Logradouro> listarTodos(Boolean ativo) {
        List<Logradouro> logradouros = new ArrayList<>();

        logradouros = em
                .createQuery("SELECT l FROM Logradouro l WHERE l.ativo = :pAtivo", Logradouro.class)
                .setParameter("pAtivo", ativo).getResultList();

        return logradouros;
    }

    @Override
    public Logradouro buscarPorId(Integer id) {
        return em.find(Logradouro.class, id);
    }

    @Override
    public void atualizar(Logradouro model) {
        em.merge(model);
    }

}
