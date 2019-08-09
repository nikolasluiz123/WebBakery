package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.ejb.Stateless;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Receita;
import br.com.WebBakery.to.TOReceita;

@Stateless
public class ReceitaDao extends AbstractBaseDao<TOReceita> {
//    @PersistenceContext
//    transient private EntityManager entityManager;
//    
//    @Override
//    protected EntityManager getEntityManager() {
//        return this.entityManager;
//    }
    private static final long serialVersionUID = -6670901759193719186L;

    @Override
    public void cadastrar(TOReceita to) throws Exception {
        Receita r = new Receita();
        getConverter().getModelFromTO(to, r);
        getEntityManager().persist(r);
    }

    @Override
    public TOReceita buscarPorId(Integer id) throws Exception {
        Receita r = getEntityManager().find(Receita.class, id);
        TOReceita to = new TOReceita();
        getConverter().getTOFromModel(r, to);
        
        return to;
    }

    @Override
    public void atualizar(TOReceita to) throws Exception {
        Receita r = new Receita();
        getConverter().getModelFromTO(to, r);
        getEntityManager().merge(r);
    }
    
    public List<TOReceita> listarTodos(Boolean ativo) throws Exception {
        List<Receita> receitas = new ArrayList<>();
        List<TOReceita> toReceitas = new ArrayList<>();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT r")
        .add("FROM ".concat(Receita.class.getName()).concat(" r "))
        .add("WHERE r.ativo = :pAtivo");

        receitas = getEntityManager().createQuery(sql.toString(), Receita.class)
                                     .setParameter("pAtivo", ativo)
                                     .getResultList();
        
        for (Receita r : receitas) {
            TOReceita to = new TOReceita();
            getConverter().getTOFromModel(r, to);
            toReceitas.add(to);
        }

        return toReceitas;
    }

}
