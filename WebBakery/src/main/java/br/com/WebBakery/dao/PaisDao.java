package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Pais;

@Stateless
public class PaisDao extends AbstractBaseDao<Pais> {

    private static final long serialVersionUID = 1904464340270603917L;

    public PaisDao(EntityManager em) {
        this.em = em;
    }

    public PaisDao() {
    }

    public List<Pais> listarTodos(Boolean ativo) {
        List<Pais> paises = new ArrayList<>();

        paises = em.createQuery("SELECT p FROM Pais p WHERE p.ativo = :pAtivo", Pais.class)
                .setParameter("pAtivo", ativo).getResultList();

        return paises;
    }

}
