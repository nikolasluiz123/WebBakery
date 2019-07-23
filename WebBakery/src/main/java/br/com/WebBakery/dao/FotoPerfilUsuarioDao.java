package br.com.WebBakery.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.WebBakery.abstractClass.AbstractArquivo;
import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Foto;

@Stateless
public class FotoPerfilUsuarioDao extends AbstractBaseDao<Foto> {
    @PersistenceContext
    transient private EntityManager entityManager;
    
    @Override
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }
    private static final long serialVersionUID = 2158380135942373657L;

    public Foto getFotoUsuario(Integer idUsuario) {
        String sql = "SELECT f FROM Foto f WHERE f.usuario.id = :pidUsuario";
        Query query = getEntityManager().createQuery(sql, AbstractArquivo.class);
        query.setParameter("pidUsuario", idUsuario);
        @SuppressWarnings("unchecked")
        List<Foto> fotos = query.getResultList();
        if (fotos != null && !fotos.isEmpty()) {
            return fotos.get(0);
        }
        return null;
    }

    @Override
    public List<Foto> listarTodos(Boolean ativo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Class<?> getModelClass() {
        return Foto.class;
    }

}
