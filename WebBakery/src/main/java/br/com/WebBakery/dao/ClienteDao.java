package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import br.com.WebBaker.interfaces.IBaseDao;
import br.com.WebBakery.model.Cliente;

@Stateless
public class ClienteDao implements IBaseDao<Cliente> {

    private static final long serialVersionUID = 3335677484935538227L;

    @PersistenceContext
    private EntityManager em;

    public ClienteDao(EntityManager em) {
        this.em = em;
    }

    public ClienteDao() {
    }

    @Override
    public void cadastrar(Cliente model) {
        em.persist(model);
    }

    @Override
    public List<Cliente> listarTodos(Boolean ativo) {

        List<Cliente> clientes = new ArrayList<>();

        clientes = em
                .createQuery("SELECT c FROM Cliente c WHERE c.ativo = :pAtivo ORDER BY c.nome, c.sobrenome",
                             Cliente.class)
                .setParameter("pAtivo", ativo).getResultList();

        return clientes;
    }

    @Override
    public Cliente buscarPorId(Integer id) {
        return em.find(Cliente.class, id);
    }

    @Override
    public void atualizar(Cliente model) {
        em.merge(model);
    }

    public boolean cpfExiste(String cpf, Boolean ativo) {
        try {
            em.createQuery("SELECT c FROM Cliente c WHERE c.ativo = :pAtivo AND c.cpf = :pCpf",
                           Cliente.class)
                    .setParameter("pCpf", cpf).setParameter("pAtivo", ativo).getSingleResult();

        } catch (NoResultException e) {
            return false;
        }
        return true;
    }
}
