package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.WebBaker.interfaces.IBaseDao;
import br.com.WebBakery.model.Usuario;

@Stateless
public class UsuarioDao implements IBaseDao<Usuario> {

    private static final long serialVersionUID = -2545830489067942125L;

    @PersistenceContext
    private EntityManager em;

    public UsuarioDao(EntityManager em) {
        this.em = em;
    }

    public UsuarioDao() {
    }

    @Override
    public void cadastrar(Usuario model) {
        em.persist(model);
    }

    @Override
    public Usuario buscarPorId(Integer id) {
        return em.find(Usuario.class, id);
    }

    @Override
    public void atualizar(Usuario model) {
        em.merge(model);
    }

    @Override
    public List<Usuario> listarTodos(Boolean ativo) {
        List<Usuario> usuarios = new ArrayList<>();

        usuarios = em
                .createQuery("SELECT u FROM Usuario u WHERE u.ativo = :pAtivo ORDER BY u.email",
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

    public boolean usuarioExiste(Usuario usuario) {
        TypedQuery<Usuario> query = em.createQuery(" SELECT u FROM Usuario u "
                + " WHERE u.email = :pEmail ", Usuario.class);

        query.setParameter("pEmail", usuario.getEmail());
        try {
            @SuppressWarnings("unused")
            Usuario resultado = query.getSingleResult();
        } catch (NoResultException ex) {
            return false;
        }
        return true;
    }
}
