package br.com.WebBakery.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBakery.model.Estado;

@Stateless
public class EstadoDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    public EstadoDao(EntityManager em) {
        this.em = em;
    }

    public EstadoDao() {
    }

    public void cadastrar(Estado estado) {
        em.persist(estado);
    }

    public List<Estado> listarTodos(Boolean ativo) {

        List<Estado> estados = new ArrayList<>();

        estados = em.createQuery("SELECT e FROM Estado e WHERE e.ativo = :pAtivo ORDER BY e.nome",
                                 Estado.class)
                .setParameter("pAtivo", ativo).getResultList();

        return estados;
    }

    public List<Estado> listarTodos() {
        List<Estado> estados = new ArrayList<>();

        estados = em.createQuery("SELECT e FROM Estado e WHERE 1=1 ORDER BY e.nome", Estado.class)
                .getResultList();

        return estados;
    }

    public Estado buscarPelaId(Integer id) {
        return em.find(Estado.class, id);
    }

    public void atualizar(Estado estado) {
        em.merge(estado);
    }

    public List<Estado> listarTodos(boolean ativo, Integer paisId) {
        List<Estado> estados = new ArrayList<>();

        estados = em
                .createQuery("SELECT e FROM Estado e WHERE e.pais.id = :pPaisId AND e.ativo = :pAtivo ORDER BY e.nome",
                             Estado.class)
                .setParameter("pPaisId", paisId).setParameter("pAtivo", ativo).getResultList();

        return estados;
    }
}
