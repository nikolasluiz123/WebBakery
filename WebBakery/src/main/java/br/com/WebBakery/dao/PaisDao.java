package br.com.WebBakery.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBakery.model.Pais;

@Stateless
public class PaisDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    public PaisDao(EntityManager em) {
        this.em = em;
    }

    public PaisDao() {
    }

    public void cadastrar(Pais pais) {
        em.persist(pais);
    }

    public List<Pais> listarTodos(Boolean ativo) {

        List<Pais> paises = new ArrayList<>();

        paises = em.createQuery("SELECT p FROM Pais p WHERE p.ativo = :pAtivo ORDER BY p.nome",
                                Pais.class)
                .setParameter("pAtivo", ativo).getResultList();

        return paises;
    }

    public List<Pais> listarTodos() {
        List<Pais> paises = new ArrayList<>();

        paises = em.createQuery("SELECT p FROM Pais p WHERE 1=1 ORDER BY p.nome", Pais.class)
                .getResultList();

        return paises;
    }

    public Pais buscarPelaId(Integer id) {
        return em.find(Pais.class, id);
    }

    public void atualizar(Pais pais) {
        em.merge(pais);
    }
}
