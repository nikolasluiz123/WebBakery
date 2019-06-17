package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBaker.interfaces.IBaseDao;
import br.com.WebBakery.model.Estado;

@Stateless
public class EstadoDao implements IBaseDao<Estado> {

    private static final long serialVersionUID = -783763888391855583L;

    @PersistenceContext
    private EntityManager em;

    public EstadoDao(EntityManager em) {
        this.em = em;
    }

    public EstadoDao() {
    }

    @Override
    public void cadastrar(Estado model) {
        em.persist(model);
    }

    @Override
    public List<Estado> listarTodos(Boolean ativo) {
        List<Estado> estados = new ArrayList<>();

        estados = em.createQuery("SELECT e FROM Estado e WHERE e.ativo = :pAtivo", Estado.class)
                .setParameter("pAtivo", ativo).getResultList();

        return estados;
    }

    public List<Estado> listarTodos(boolean ativo, Integer paisId) {
        List<Estado> estados = new ArrayList<>();

        estados = em
                .createQuery("SELECT e FROM Estado e WHERE e.ativo = :pAtivo AND e.pais.id = :pPaisID",
                             Estado.class)
                .setParameter("pAtivo", ativo).setParameter("pPaisID", paisId).getResultList();

        return estados;
    }

    @Override
    public Estado buscarPorId(Integer id) {
        return em.find(Estado.class, id);
    }

    @Override
    public void atualizar(Estado model) {
        em.merge(model);
    }

}
