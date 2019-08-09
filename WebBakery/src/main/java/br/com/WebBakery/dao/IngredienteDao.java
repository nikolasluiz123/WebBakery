package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.ejb.Stateless;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.FotoIngrediente;
import br.com.WebBakery.model.Ingrediente;
import br.com.WebBakery.to.TOFotoIngrediente;
import br.com.WebBakery.to.TOIngrediente;
import br.com.WebBakery.to.TOIngredienteComFotos;

@Stateless
public class IngredienteDao extends AbstractBaseDao<TOIngrediente> {

    private static final long serialVersionUID = -3222599814430071085L;

//    @PersistenceContext
//    transient private EntityManager entityManager;
//
//    @Override
//    protected EntityManager getEntityManager() {
//        return this.entityManager;
//    }
    
    @Override
    public void cadastrar(TOIngrediente to) throws Exception {
        Ingrediente i = new Ingrediente();
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
    public void atualizar(TOIngrediente to) throws Exception {
        Ingrediente i = new Ingrediente();
        getConverter().getModelFromTO(to, i);
        getEntityManager().persist(i);
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
    
    public List<TOIngredienteComFotos> listarTodosIngredienteComFotos() throws Exception {
        List<Ingrediente> ingredientes = new ArrayList<>();
        List<FotoIngrediente> fotosIngredientes = new ArrayList<>();
        
        List<TOIngrediente> toIngredientes = new ArrayList<>();
        List<TOFotoIngrediente> toFotosIngrediete = new ArrayList<>();
        
        List<TOIngredienteComFotos> toIngredienteComFotos = new ArrayList<>();
        
        StringJoiner sqlIngredientes = new StringJoiner(QR_NL);
        sqlIngredientes
        .add("SELECT i")
        .add("FROM ".concat(Ingrediente.class.getName()).concat(" i "))
        .add("WHERE i.ativo = :pAtivo");
        
        StringJoiner sqlFotos = new StringJoiner(QR_NL);
        sqlFotos
        .add("SELECT fi")
        .add("FROM ".concat(FotoIngrediente.class.getName()).concat(" fi "))
        .add("WHERE")
        .add("fi.ativo = :pAtivo");
        
        ingredientes = getEntityManager().createQuery(sqlIngredientes.toString(), Ingrediente.class)
                                     .setParameter("pAtivo", true)
                                     .getResultList();
        
        fotosIngredientes = getEntityManager().createQuery(sqlFotos.toString(), FotoIngrediente.class)
                                          .setParameter("pAtivo", true)
                                          .getResultList();            
        
        for (Ingrediente i : ingredientes) {
            TOIngrediente to = new TOIngrediente();
            getConverter().getTOFromModel(i, to);
            toIngredientes.add(to);
        }
        
        for (FotoIngrediente fi : fotosIngredientes) {
            TOFotoIngrediente to = new TOFotoIngrediente();
            getConverter().getTOFromModel(fi, to);
            toFotosIngrediete.add(to);
        }
        
        List<TOFotoIngrediente> toFotos = new ArrayList<>();
        for (TOIngrediente to : toIngredientes) {
            TOIngredienteComFotos toIngredienteComFoto = new TOIngredienteComFotos();
            toIngredienteComFoto.setToIngrediente(to);

            for (TOFotoIngrediente toFotoIngrediente : toFotosIngrediete) {
                if (toFotoIngrediente.getToIngrediente().getId() == to.getId()) {
                    toFotos.add(toFotoIngrediente);
                }
            }
            
            toIngredienteComFoto.setToFotos(toFotos);
            toIngredienteComFotos.add(toIngredienteComFoto);
        }
        
        return toIngredienteComFotos;
    }

}
