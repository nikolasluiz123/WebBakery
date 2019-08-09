package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.FotoIngrediente;
import br.com.WebBakery.to.TOFotoIngrediente;

@Stateless
public class FotoIngredienteDao extends AbstractBaseDao<TOFotoIngrediente> {

    private static final long serialVersionUID = 7731961468170386624L;
    
//    @PersistenceContext
//    transient private EntityManager entityManager;
//
//    @Override
//    protected EntityManager getEntityManager() {
//        return this.entityManager;
//    }

    @Override
    public void cadastrar(TOFotoIngrediente to) throws Exception {
        FotoIngrediente fotoIngrediente = new FotoIngrediente();
        getConverter().getModelFromTO(to, fotoIngrediente);
        getEntityManager().persist(fotoIngrediente);
    }

    @Override
    public TOFotoIngrediente buscarPorId(Integer id) throws Exception {
        FotoIngrediente fotoIngrediente = getEntityManager().find(FotoIngrediente.class, id);
        TOFotoIngrediente to = new TOFotoIngrediente();
        getConverter().getTOFromModel(fotoIngrediente, to);
        
        return to;
    }

    @Override
    public void atualizar(TOFotoIngrediente to) throws Exception {
        FotoIngrediente fotoIngrediente = new FotoIngrediente();
        getConverter().getModelFromTO(to, fotoIngrediente);
        
    }
    
    public List<TOFotoIngrediente> listarTodos(Boolean ativo) throws Exception {
        List<FotoIngrediente> fotosIngredientes = new ArrayList<>();
        List<TOFotoIngrediente> toFotosIngredientes = new ArrayList<>();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT fi")
        .add("FROM ".concat(FotoIngrediente.class.getName()).concat("fi"))
        .add("WHERE fi.ativo = :pAtivo");

        fotosIngredientes = getEntityManager().createQuery(sql.toString(), FotoIngrediente.class)
                                              .setParameter("pAtivo", ativo)
                                              .getResultList();

        for (FotoIngrediente fi : fotosIngredientes) {
            TOFotoIngrediente to = new TOFotoIngrediente();
            getConverter().getTOFromModel(fi, to);
            toFotosIngredientes.add(to);
        }
        
        return toFotosIngredientes;
    }

    public List<TOFotoIngrediente> listarFotosIngrediente(Integer idIngrediente) throws Exception {
        List<FotoIngrediente> fotosIngredientes = new ArrayList<>();
        List<TOFotoIngrediente> toFotosIngredientes = new ArrayList<>();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT fi")
        .add("FROM ".concat(FotoIngrediente.class.getName()).concat("fi"))
        .add("WHERE")
        .add("fi.ativo = :pAtivo")
        .add("AND fi.ingrediente.id = :pIdIngrediente");
        
        fotosIngredientes = getEntityManager().createQuery(sql.toString(), FotoIngrediente.class)
                                              .setParameter("pAtivo", true)
                                              .setParameter("pIdIngrediente", idIngrediente)
                                              .getResultList();
        
        for (FotoIngrediente fi : fotosIngredientes) {
            TOFotoIngrediente to = new TOFotoIngrediente();
            getConverter().getTOFromModel(fi, to);
            toFotosIngredientes.add(to);
        }

        return toFotosIngredientes;
    }

    public void inativarFotos(Integer idIngrediente) {
       StringJoiner selectFotosIngrediente = new StringJoiner("\n");
       selectFotosIngrediente
       .add("(SELECT ")
       .add("fi.id")
       .add("FROM ".concat(FotoIngrediente.class.getName()) + " fi ")
       .add("WHERE fi.ativo = :pAtivo")
       .add("AND fi.ingrediente.id = :pIdIngrediente)");
       
       StringJoiner updateFotosProduto = new StringJoiner("\n");
       updateFotosProduto
       .add("UPDATE ".concat(FotoIngrediente.class.getName()) + " fi ")
       .add("SET fi.ativo = false")
       .add("WHERE fi.id IN ".concat(selectFotosIngrediente.toString()));
       
       Query query = getEntityManager().createQuery(updateFotosProduto.toString());
       
       query
       .setParameter("pAtivo", true)
       .setParameter("pIdIngrediente", idIngrediente);
       
       query.executeUpdate();
    }

}