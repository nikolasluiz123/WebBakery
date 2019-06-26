package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Cliente;

@Stateless
public class ClienteDao extends AbstractBaseDao<Cliente> {

    private static final long serialVersionUID = 3335677484935538227L;

    public ClienteDao(EntityManager em) {
        this.em = em;
    }

    public ClienteDao() {
    }

    public List<Cliente> listarTodos(Boolean ativo) {

        List<Cliente> clientes = new ArrayList<>();

        clientes = em
                .createQuery("SELECT c FROM Cliente c WHERE c.ativo = :pAtivo ORDER BY c.usuario.nome, c.usuario.sobrenome",
                             Cliente.class)
                .setParameter("pAtivo", ativo).getResultList();

        return clientes;
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

    public Cliente buscarPorIdUsuario(Integer idUsuario) {
        Cliente c = new Cliente();
        try {
            c = (Cliente) em
                    .createQuery("SELECT c.id, c.nome, c.sobrenome FROM Cliente c WHERE c.ativo = TRUE AND c.usuario.id = :pIdUsuario",
                                 Cliente.class)
                    .setParameter("pIdUsuario", idUsuario);
        } catch (NoResultException e) {
            e.addSuppressed(e);
        }
        return c;
    }
}
