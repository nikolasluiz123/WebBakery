package br.com.WebBakery.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Foto;

@Stateless
public class FotoPerfilUsuarioDao extends AbstractBaseDao<Foto> {

    private static final long serialVersionUID = 2158380135942373657L;

    public FotoPerfilUsuarioDao(EntityManager em) {
        this.em = em;
    }

    public FotoPerfilUsuarioDao() {
    }

    public Foto getFotoUsuario(Integer idUsuario) {
        try {
            return em
                    .createQuery("SELECT f FROM Foto f WHERE f.usuario.id = :pidUsuario",
                                 Foto.class)
                    .setParameter("pidUsuario", idUsuario).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Foto> listarTodos(Boolean ativo) {
        // TODO Auto-generated method stub
        return null;
    }

}
