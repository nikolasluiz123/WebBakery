package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Usuario;
import br.com.WebBakery.to.TOUsuario;

@Stateless
public class UsuarioDao extends AbstractBaseDao<TOUsuario> {

    private static final long serialVersionUID = -2545830489067942125L;

    @Override
    public void salvar(TOUsuario to) throws Exception {
        Usuario u = new Usuario();
        getConverter().getModelFromTO(to, u);
        getEntityManager().merge(u);
    }

    @Override
    public TOUsuario buscarPorId(Integer id) throws Exception {
        Usuario u = getEntityManager().find(Usuario.class, id);
        TOUsuario to = new TOUsuario();
        getConverter().getTOFromModel(u, to);
        
        return to;
    }

    @Override
    public List<TOUsuario> listarTodos(Boolean ativo) throws Exception {
        List<Usuario> usuarios = new ArrayList<>();
        List<TOUsuario> toUsuarios = new ArrayList<>();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT u")
        .add("FROM ".concat(Usuario.class.getName()).concat(" u "))
        .add("WHERE u.ativo = :pAtivo")
        .add("ORDER BY u.nome, u.sobrenome");

        usuarios = getEntityManager().createQuery(sql.toString(), Usuario.class)
                                     .setParameter("pAtivo", ativo)
                                     .getResultList();

        for (Usuario u : usuarios) {
            TOUsuario to = new TOUsuario();
            getConverter().getTOFromModel(u, to);
            toUsuarios.add(to);
        }
        
        return toUsuarios;
    }

    public boolean emailExiste(String email) {
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT u")
        .add("FROM ".concat(Usuario.class.getName()).concat(" u "))
        .add("WHERE")
        .add("u.ativo = :pAtivo")
        .add("AND u.email = :pEmail");
        
        Usuario u = getEntityManager().createQuery(sql.toString(), Usuario.class)
                                      .setParameter("pEmail", email)
                                      .setParameter("pAtivo", true)
                                      .getSingleResult();
        
        return u != null;

    }

    public TOUsuario usuarioExiste(String email) throws Exception {
        TOUsuario to = new TOUsuario();
        Usuario u = new Usuario();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT u")
        .add("FROM ".concat(Usuario.class.getName()).concat(" u "))
        .add("WHERE u.email = :pEmail");
        
        try {
            u = getEntityManager().createQuery(sql.toString(), Usuario.class)
                                  .setParameter("pEmail", email)
                                  .getSingleResult();
        } catch (NoResultException e) {
            return to;
        }
        
        getConverter().getTOFromModel(u, to);
        
        return to;
    }

}
