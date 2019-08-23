package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.ReceitaIngrediente;
import br.com.WebBakery.to.TOReceitaIngrediente;

public class ReceitaIngredienteDao extends AbstractBaseDao<TOReceitaIngrediente> {

    private static final long serialVersionUID = -1876815868569221645L;

    @Override
    public void salvar(TOReceitaIngrediente to) throws Exception {
        ReceitaIngrediente ri = null;
        if (to.getId() == null) {
            ri = new ReceitaIngrediente();
        } else {
            ri = getEntityManager().find(ReceitaIngrediente.class, to.getId());
        }
        
        getConverter().getModelFromTO(to, ri);            
        
        getEntityManager().persist(ri);
        
        getEntityManager().flush();
        to.setId(ri.getId());
    }

    @Override
    public List<TOReceitaIngrediente> listarTodos(Boolean ativo) throws Exception {
        List<TOReceitaIngrediente> toReceitasIngredientes = new ArrayList<>();
        List<ReceitaIngrediente> receitasIngredientes = new ArrayList<>();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT ri")
        .add("FROM ".concat(ReceitaIngrediente.class.getName()).concat(" ri "))
        .add("WHERE ri.ativo = :pAtivo");
        
        receitasIngredientes = getEntityManager().createQuery(sql.toString(), ReceitaIngrediente.class)
                                                 .setParameter("pAtivo", ativo)
                                                 .getResultList();
   
        for (ReceitaIngrediente receitaIngrediente : receitasIngredientes) {
           TOReceitaIngrediente to = new TOReceitaIngrediente();
           getConverter().getTOFromModel(receitaIngrediente, to);
           toReceitasIngredientes.add(to);
        }
        
        return toReceitasIngredientes;
    }

    @Override
    public TOReceitaIngrediente buscarPorId(Integer id) throws Exception {
        ReceitaIngrediente receitaIngrediente = getEntityManager().find(ReceitaIngrediente.class, id);
        TOReceitaIngrediente to = new TOReceitaIngrediente();
        getConverter().getTOFromModel(receitaIngrediente, to);
        
        return to;
    }

    public List<TOReceitaIngrediente> listarTodos(boolean ativo, Integer idReceita) throws Exception {
        List<TOReceitaIngrediente> toReceitasIngredientes = new ArrayList<>();
        List<ReceitaIngrediente> receitasIngredientes = new ArrayList<>();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT ri")
        .add("FROM ".concat(ReceitaIngrediente.class.getName()).concat(" ri "))
        .add("WHERE ri.ativo = :pAtivo")
        .add("AND ri.receita.id = :pIdReceita");
        
        receitasIngredientes = getEntityManager().createQuery(sql.toString(), ReceitaIngrediente.class)
                                                 .setParameter("pAtivo", ativo)
                                                 .setParameter("pIdReceita", idReceita)
                                                 .getResultList();
   
        for (ReceitaIngrediente receitaIngrediente : receitasIngredientes) {
           TOReceitaIngrediente to = new TOReceitaIngrediente();
           getConverter().getTOFromModel(receitaIngrediente, to);
           toReceitasIngredientes.add(to);
        }
        
        return toReceitasIngredientes;
    }

}
