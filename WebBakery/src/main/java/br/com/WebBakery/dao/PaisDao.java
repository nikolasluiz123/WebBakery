package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBaker.interfaces.IBaseDao;
import br.com.WebBakery.model.Pais;

@Stateless
public class PaisDao implements IBaseDao<Pais> {

    private static final long serialVersionUID = 1904464340270603917L;

    @PersistenceContext
    private EntityManager em;

    public PaisDao(EntityManager em) {
        this.em = em;
    }

    public PaisDao() {
    }

    @Override
    public void cadastrar(Pais model) {
        em.persist(model);
    }

    @Override
    public List<Pais> listarTodos(Boolean ativo) {
        List<Pais> paises = new ArrayList<>();

        paises = em.createQuery("SELECT p FROM Pais p WHERE p.ativo = :pAtivo", Pais.class)
                .setParameter("pAtivo", ativo).getResultList();

        return paises;
    }

    @Override
    public Pais buscarPorId(Integer id) {
        return em.find(Pais.class, id);
    }

    @Override
    public void atualizar(Pais model) {
        em.merge(model);
    }

}
