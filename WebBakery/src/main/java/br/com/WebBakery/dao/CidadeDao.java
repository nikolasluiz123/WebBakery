package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBaker.interfaces.IBaseDao;
import br.com.WebBakery.model.Cidade;

@Stateless
public class CidadeDao implements IBaseDao<Cidade> {

    private static final long serialVersionUID = -8347345341892955875L;

    @PersistenceContext
    private EntityManager em;

    public CidadeDao(EntityManager em) {
        this.em = em;
    }

    public CidadeDao() {
    }

    @Override
    public void cadastrar(Cidade model) {
        em.persist(model);
    }

    @Override
    public List<Cidade> listarTodos(Boolean ativo) {
        List<Cidade> cidades = new ArrayList<>();

        cidades = em.createQuery("SELECT c FROM Cidade c WHERE c.ativo = :pAtivo", Cidade.class)
                .setParameter("pAtivo", ativo).getResultList();

        return cidades;
    }

    public List<Cidade> listarTodos(boolean ativo, Integer estadoId) {
        List<Cidade> cidades = new ArrayList<>();

        cidades = em
                .createQuery("SELECT c FROM Cidade c WHERE c.ativo = :pAtivo AND c.estado.id = :pEstadoId",
                             Cidade.class)
                .setParameter("pAtivo", ativo).setParameter("pEstadoId", estadoId).getResultList();

        return cidades;
    }

    @Override
    public Cidade buscarPorId(Integer id) {
        return em.find(Cidade.class, id);
    }

    @Override
    public void atualizar(Cidade model) {
        em.merge(model);
    }

}
