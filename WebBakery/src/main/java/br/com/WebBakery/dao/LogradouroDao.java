package br.com.WebBakery.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBakery.model.Logradouro;

@Stateless
public class LogradouroDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    public LogradouroDao(EntityManager em) {
        this.em = em;
    }

    public LogradouroDao() {
    }

    public void cadastrar(Logradouro logradouro) {
        em.persist(logradouro);
    }

    public List<Logradouro> listarTodos(Boolean ativo) {

        List<Logradouro> logradouros = new ArrayList<>();

        logradouros = em
                .createQuery("SELECT l FROM Logradouro l WHERE l.ativo = :pAtivo ORDER BY l.bairro",
                             Logradouro.class)
                .setParameter("pAtivo", ativo).getResultList();

        return logradouros;
    }

    public List<Logradouro> listarTodos() {
        List<Logradouro> logradouros = new ArrayList<>();

        logradouros = em.createQuery("SELECT l FROM Logradouro l WHERE 1=1 ORDER BY l.bairro",
                                     Logradouro.class)
                .getResultList();

        return logradouros;
    }

    public Logradouro buscarPelaId(Integer id) {
        return em.find(Logradouro.class, id);
    }

    public void atualizar(Logradouro logradouro) {
        em.merge(logradouro);
    }
}
