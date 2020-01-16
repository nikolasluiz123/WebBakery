package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.ejb.Stateless;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.entitys.FotoPerfil;
import br.com.WebBakery.to.TOFotoPerfil;

@Stateless
public class FotoPerfilUsuarioDao extends AbstractBaseDao<TOFotoPerfil> {

    private static final long serialVersionUID = 2158380135942373657L;

    @Override
    public void salvar(TOFotoPerfil to) throws Exception {
        FotoPerfil fp = null;
        if (to.getId() == null) {
            fp = new FotoPerfil();
        } else {
            fp = getEntityManager().find(FotoPerfil.class, to.getId());
        }
        
        getConverter().getModelFromTO(to, fp);            
        
        getEntityManager().persist(fp);
    }

    @Override
    public TOFotoPerfil buscarPorId(Integer id) throws Exception {
        FotoPerfil fotoPerfil = getEntityManager().find(FotoPerfil.class, id);
        TOFotoPerfil to = new TOFotoPerfil();
        getConverter().getTOFromModel(fotoPerfil, to);

        return to;
    }
    
    public TOFotoPerfil getToFotoPerfil(Integer idUsuario) throws Exception {
        List<FotoPerfil> fotos = new ArrayList<>();
        List<TOFotoPerfil> toFotos = new ArrayList<>();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT f")
        .add("FROM ".concat(FotoPerfil.class.getName()).concat(" f "))
        .add("WHERE")
        .add("f.usuario.id = :pidUsuario")
        .add("AND f.ativo = :pAtivo");
        
        fotos = getEntityManager().createQuery(sql.toString(), FotoPerfil.class)
                                  .setParameter("pidUsuario", idUsuario)
                                  .setParameter("pAtivo", true)
                                  .getResultList();

        for (FotoPerfil fotoPerfil : fotos) {
            TOFotoPerfil to = new TOFotoPerfil();
            getConverter().getTOFromModel(fotoPerfil, to);
            toFotos.add(to);
        }
        
        if (toFotos != null && !toFotos.isEmpty()) {
            return toFotos.get(0);
        }
        return null;
    }

    @Override
    public List<TOFotoPerfil> listarTodos(Boolean ativo) throws Exception {
        List<TOFotoPerfil> toFotosPerfil = new ArrayList<>();
        List<FotoPerfil> fotosPerfil = new ArrayList<>();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT fp")
        .add("FROM ".concat(FotoPerfil.class.getName()).concat(" fp "))
        .add("WHERE fp.ativo = :pAtivo");
        
        fotosPerfil = getEntityManager().createQuery(sql.toString(), FotoPerfil.class)
                                        .setParameter("pAtivo", ativo)
                                        .getResultList();
        
        for (FotoPerfil fotoPerfil : fotosPerfil) {
            TOFotoPerfil to = new TOFotoPerfil();
            getConverter().getTOFromModel(fotoPerfil, to);
            toFotosPerfil.add(to);
        }
        
        return toFotosPerfil;
    }

}
