package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Logradouro;

@Stateless
public class LogradouroDao extends AbstractBaseDao<Logradouro> {

    private static final long serialVersionUID = -3918599275005523240L;

    public LogradouroDao(EntityManager em) {
        this.em = em;
    }

    public LogradouroDao() {
    }

    public List<Logradouro> listarTodos(Boolean ativo) {
        List<Logradouro> logradouros = new ArrayList<>();

        logradouros = em
                .createQuery("SELECT l FROM Logradouro l WHERE l.ativo = :pAtivo", Logradouro.class)
                .setParameter("pAtivo", ativo).getResultList();

        return logradouros;
    }

}
