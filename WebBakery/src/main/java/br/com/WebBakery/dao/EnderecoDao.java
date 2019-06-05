package br.com.WebBakery.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBakery.model.Endereco;

@Stateless
public class EnderecoDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    public EnderecoDao(EntityManager em) {
        this.em = em;
    }

    public EnderecoDao() {
    }

    public void cadastrar(Endereco endereco) {
        em.persist(endereco);
    }

    public List<Endereco> listarTodos(Boolean ativo) {

        List<Endereco> enderecos = new ArrayList<>();

        enderecos = em
                .createQuery("SELECT e FROM Endereco e WHERE e.ativo = :pAtivo ORDER BY e.pais.nome",
                             Endereco.class)
                .setParameter("pAtivo", ativo).getResultList();

        return enderecos;
    }

    public List<Endereco> listarTodos() {
        List<Endereco> enderecos = new ArrayList<>();

        enderecos = em.createQuery("SELECT e FROM Endereco e WHERE 1=1 ORDER BY e.pais.nome",
                                   Endereco.class)
                .getResultList();

        return enderecos;
    }

    public Endereco buscarPelaId(Integer id) {
        return em.find(Endereco.class, id);
    }

    public void atualizar(Endereco endereco) {
        em.merge(endereco);
    }
}
