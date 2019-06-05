package br.com.WebBakery.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBakery.model.Cliente;

@Stateless
public class ClienteDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    public ClienteDao(EntityManager em) {
        this.em = em;
    }

    public ClienteDao() {
    }

    public void cadastrar(Cliente cliente) {
        em.persist(cliente);
    }

    public List<Cliente> listarTodos(Boolean ativo) {

        List<Cliente> clientes = new ArrayList<>();

        clientes = em
                .createQuery("SELECT c FROM Cliente c WHERE c.ativo = :pAtivo ORDER BY c.nome, c.sobrenome",
                             Cliente.class)
                .setParameter("pAtivo", ativo).getResultList();

        return clientes;
    }

    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();

        clientes = em.createQuery("SELECT c FROM Cliente e WHERE 1=1 ORDER BY c.nome, c.sobrenome",
                                  Cliente.class)
                .getResultList();

        return clientes;
    }

    public Cliente buscarPelaId(Integer id) {
        return em.find(Cliente.class, id);
    }

    public void atualizar(Cliente cliente) {
        em.merge(cliente);
    }
}
