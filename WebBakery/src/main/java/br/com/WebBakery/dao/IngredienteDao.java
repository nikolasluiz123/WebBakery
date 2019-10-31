package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.ejb.Stateless;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.entitys.Ingrediente;
import br.com.WebBakery.to.TOIngrediente;

@Stateless
public class IngredienteDao extends AbstractBaseDao<TOIngrediente> {

    private static final long serialVersionUID = -3222599814430071085L;

    @Override
    public void salvar(TOIngrediente to) throws Exception {
        Ingrediente i = null;
        if (to.getId() == null) {
            i = new Ingrediente();
        } else {
            i = getEntityManager().find(Ingrediente.class, to.getId());
        }
        
        getConverter().getModelFromTO(to, i);            
        
        getEntityManager().persist(i);
    }

    @Override
    public TOIngrediente buscarPorId(Integer id) throws Exception {
        Ingrediente i = getEntityManager().find(Ingrediente.class, id);
        TOIngrediente to = new TOIngrediente();
        getConverter().getTOFromModel(i, to);
        
        return to;
    }

    @Override
    public List<TOIngrediente> listarTodos(Boolean ativo) throws Exception {
        List<Ingrediente> ingredientes = new ArrayList<>();
        List<TOIngrediente> toIngredientes = new ArrayList<>();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT i")
        .add("FROM ".concat(Ingrediente.class.getName()).concat(" i "))
        .add("WHERE i.ativo = :pAtivo");
        
        ingredientes = getEntityManager().createQuery(sql.toString(), Ingrediente.class)
                                         .setParameter("pAtivo", ativo)
                                         .getResultList();
        
        for (Ingrediente i : ingredientes) {
            TOIngrediente to = new TOIngrediente();
            getConverter().getTOFromModel(i, to);
            toIngredientes.add(to);
        }
        
        return toIngredientes;
    }
    
}
