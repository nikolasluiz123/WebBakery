package br.com.WebBakery.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringJoiner;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.entitys.Funcionario;
import br.com.WebBakery.model.graphics.FuncionarioGraphicValues;
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

    public TOFuncionario buscarPorIdUsuario(Integer idUsuario, Integer idFuncionario) throws Exception {
       TOFuncionario to = new TOFuncionario();
       Funcionario f = new Funcionario();
       
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT f")
        .add("FROM ".concat(Funcionario.class.getName()).concat(" f "))
        .add("WHERE")
        .add("f.ativo = :pAtivo")
        .add("AND f.usuario.id = :pIdUsuario")
        .add("AND f.id != :pidFuncionario");
        
        if (idUsuario != null && idFuncionario != null) {
            try {
            f = getEntityManager().createQuery(sql.toString(), Funcionario.class)
                                  .setParameter("pIdUsuario", idUsuario)
                                  .setParameter("pAtivo", true)
                                  .setParameter("pidFuncionario", idFuncionario)
                                  .getSingleResult();
            
            } catch (NoResultException e) {
                return null;
            }
            getConverter().getTOFromModel(f, to);
        }

        return to;
    }
    
    public TOFuncionario buscarPorIdUsuario(Integer idUsuario) throws Exception {
         Funcionario f = new Funcionario();
         TOFuncionario to = new TOFuncionario();
         
         StringJoiner sql = new StringJoiner(QR_NL);
         sql
         .add("SELECT f")
         .add("FROM ".concat(Funcionario.class.getName()).concat(" f "))
         .add("WHERE")
         .add("f.ativo = :pAtivo")
         .add("AND f.usuario.id = :pIdUsuario");
         
         if (idUsuario != null) {
             try {
                 f = getEntityManager().createQuery(sql.toString(), Funcionario.class)
                                       .setParameter("pIdUsuario", idUsuario)
                                       .setParameter("pAtivo", true)
                                       .getSingleResult();
            } catch (NoResultException e) {
                return to;
            }
         }

         getConverter().getTOFromModel(f, to);
         return to;
     }

    public List<FuncionarioGraphicValues> getCincoFuncionariosQueMaisVendem(Calendar dataInicial, Calendar dataFinal) {
        List<FuncionarioGraphicValues> listGraphicValues = new ArrayList<>();
        
        StringBuilder sql  = new StringBuilder(QR_NL);
        sql
        .append("select                                                                                                 ")
        .append("u.nome_usuario ||' '|| u.sobrenome_usuario as nomeCompleto,                                            ")
        .append("count(v.id) as quantidadeVendas                                                                        ")
        .append("from venda v                                                                                           ")
        .append("inner join funcionario f on f.id = v.id_funcionario_venda                                              ")
        .append("inner join usuario u on u.id = f.id_usuario_funcionario                                                ")
        .append("where u.ativo and f.ativo and v.ativo and v.data_venda between :pDataInicial and :pDataFinal           ")
        .append("group by u.id                                                                                          ")
        .append("order by quantidadeVendas desc                                                                         ")
        .append("limit 5                                                                                                ");
        
        @SuppressWarnings("unchecked")
        List<Object[]> resultList = getEntityManager().createNativeQuery(sql.toString())
                                                      .setParameter("pDataInicial", dataInicial.getTime())
                                                      .setParameter("pDataFinal", dataFinal.getTime())
                                                      .getResultList();
        
        for (Object[] obj : resultList) {
            FuncionarioGraphicValues graphicValues = new FuncionarioGraphicValues((String) obj[0], (BigInteger) obj[1]);
            listGraphicValues.add(graphicValues);
        }
        
        return listGraphicValues;
    }

}
