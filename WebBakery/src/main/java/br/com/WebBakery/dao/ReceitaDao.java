package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Receita;

@Stateless
public class ReceitaDao extends AbstractBaseDao<Receita> {

    private static final long serialVersionUID = -6670901759193719186L;

    public ReceitaDao(EntityManager em) {
        this.em = em;
    }

    public ReceitaDao() {
    }

    public List<Receita> listarTodos(Boolean ativo) {
        List<Receita> receitas = new ArrayList<>();

        receitas = em.createQuery("SELECT r FROM Receita r WHERE r.ativo = :pAtivo", Receita.class)
                .setParameter("pAtivo", ativo).getResultList();

        return receitas;
    }
}
