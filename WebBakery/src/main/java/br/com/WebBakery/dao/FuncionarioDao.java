package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.ejb.Stateless;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Funcionario;
import br.com.WebBakery.to.TOFuncionario;

@Stateless
public class FuncionarioDao extends AbstractBaseDao<TOFuncionario> {
    
    private static final long serialVersionUID = -3829525203324472005L;
    
    @Override
    public void salvar(TOFuncionario to) throws Exception {
        Funcionario f = null;
        if (to.getId() == null) {
            f = new Funcionario();
        } else {
            f = getEntityManager().find(Funcionario.class, to.getId());
        }
        
        getConverter().getModelFromTO(to, f);            
        
        getEntityManager().persist(f);
    }

    @Override
    public TOFuncionario buscarPorId(Integer id) throws Exception {
        Funcionario f = getEntityManager().find(Funcionario.class, id);
        TOFuncionario to = new TOFuncionario();
        getConverter().getTOFromModel(f, to);
        
        return to;
    }

    public List<TOFuncionario> listarTodos(Boolean ativo) throws Exception {
        List<Funcionario> funcionarios = new ArrayList<>();
        List<TOFuncionario> toFuncionarios = new ArrayList<>();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT f")
        .add("FROM ".concat(Funcionario.class.getName()).concat(" f "))
        .add("WHERE f.ativo = :pAtivo")
        .add("ORDER BY f.usuario.nome, f.usuario.sobrenome");

        funcionarios = getEntityManager().createQuery(sql.toString(), Funcionario.class)
                                         .setParameter("pAtivo", ativo)
                                         .getResultList();

        for (Funcionario f : funcionarios) {
            TOFuncionario to = new TOFuncionario();
            getConverter().getTOFromModel(f, to);
            toFuncionarios.add(to);
        }
        
        return toFuncionarios;
    }

    public TOFuncionario buscarPorIdUsuario(Integer idUsuario) throws Exception {
       TOFuncionario to = new TOFuncionario();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT f")
        .add("FROM ".concat(Funcionario.class.getName()).concat(" f "))
        .add("WHERE")
        .add("f.ativo = :pAtivo")
        .add("AND f.usuario.id = :pIdUsuario");
        
        if (idUsuario != null) {
            Funcionario f = getEntityManager().createQuery(sql.toString(), Funcionario.class)
                    .setParameter("pIdUsuario", idUsuario)
                    .setParameter("pAtivo", true)
                    .getSingleResult();
            
            getConverter().getTOFromModel(f, to);
        }

        return to;
    }

}
