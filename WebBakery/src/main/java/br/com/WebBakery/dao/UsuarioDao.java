package br.com.WebBakery.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBakery.model.Usuario;

@Stateless
public class UsuarioDao implements Serializable {

    private static final long serialVersionUID = 1L;
    @PersistenceContext
    transient private EntityManager em;

    public UsuarioDao(EntityManager em) {
        this.em = em;
    }

    public UsuarioDao() {
    }

    public void cadastrar(Usuario usuario) {
        em.persist(usuario);
    }

    public List<Usuario> listarTodos(Boolean ativo) {

        List<Usuario> usuarios = new ArrayList<>();

        usuarios = em
                .createQuery("SELECT u FROM Usuario u WHERE u.ativo = :pAtivo ORDER BY u.email",
                             Usuario.class)
                .setParameter("pAtivo", ativo).getResultList();

        return usuarios;
    }

    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();

        usuarios = em
                .createQuery("SELECT u FROM Usuario u WHERE 1=1 ORDER BY u.email", Usuario.class)
                .getResultList();

        return usuarios;
    }

    public Usuario buscarPelaId(Integer id) {
        return em.find(Usuario.class, id);
    }

    public void atualizar(Usuario usuario) {
        em.merge(usuario);
    }
}
