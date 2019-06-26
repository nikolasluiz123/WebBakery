package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Usuario;

@Stateless
public class UsuarioDao extends AbstractBaseDao<Usuario> {

    private static final long serialVersionUID = -2545830489067942125L;

    public UsuarioDao(EntityManager em) {
        this.em = em;
    }

    public UsuarioDao() {
    }

    public List<Usuario> listarTodos(Boolean ativo) {
        List<Usuario> usuarios = new ArrayList<>();

        usuarios = em
                .createQuery("SELECT u FROM Usuario u WHERE u.ativo = :pAtivo ORDER BY u.nome, u.sobrenome",
                             Usuario.class)
                .setParameter("pAtivo", ativo).getResultList();

        return usuarios;
    }

    public boolean emailExiste(String email) {
        try {
            em.createQuery("SELECT u FROM Usuario u WHERE u.email = :pEmail", Usuario.class)
                    .setParameter("pEmail", email).getSingleResult();

        } catch (NoResultException e) {
            return false;
        }
        return true;
    }

    public Usuario usuarioExiste(Usuario usuario) {
        try {
            return em
                    .createQuery(" SELECT u FROM Usuario u "
                            + " WHERE u.email = :pEmail AND u.senha = :pSenha ", Usuario.class)
                    .setParameter("pSenha", usuario.getSenha())
                    .setParameter("pEmail", usuario.getEmail()).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
