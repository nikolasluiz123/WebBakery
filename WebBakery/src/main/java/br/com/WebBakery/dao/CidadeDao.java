package br.com.WebBakery.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBakery.model.Cidade;
import br.com.WebBakery.model.Estado;

@Stateless
public class CidadeDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    public CidadeDao(EntityManager em) {
        this.em = em;
    }

    public CidadeDao() {
    }

    public void cadastrar(Cidade cidade) {
        em.persist(cidade);
    }

    public List<Cidade> listarTodos(Boolean ativo) {

        List<Cidade> cidades = new ArrayList<>();

        cidades = em.createQuery("SELECT c FROM Cidade c WHERE c.ativo = :pAtivo ORDER BY c.nome",
                                 Cidade.class)
                .setParameter("pAtivo", ativo).getResultList();

        return cidades;
    }

    public List<Cidade> listarTodos(boolean ativo, Integer estadoId) {
        List<Cidade> cidades = new ArrayList<>();

        cidades = em
                .createQuery("SELECT c FROM Cidade c WHERE c.estado.id = :pEstadoId AND c.ativo = :pAtivo ORDER BY c.nome",
                             Cidade.class)
                .setParameter("pEstadoId", estadoId).setParameter("pAtivo", ativo).getResultList();

        return cidades;
    }

    public List<Cidade> listarTodos() {
        List<Cidade> cidades = new ArrayList<>();

        cidades = em.createQuery("SELECT c FROM Cidade c WHERE 1=1 ORDER BY c.nome", Cidade.class)
                .getResultList();

        return cidades;
    }

    public Cidade buscarPelaId(Integer id) {
        return em.find(Cidade.class, id);
    }

    public void atualizar(Cidade cidade) {
        em.merge(cidade);
    }
}
