package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.ejb.Stateless;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Tarefa;
import br.com.WebBakery.to.TOTarefa;

@Stateless
public class TarefaDao extends AbstractBaseDao<TOTarefa> {
//    @PersistenceContext
//    transient private EntityManager entityManager;
//    
//    @Override
//    protected EntityManager getEntityManager() {
//        return this.entityManager;
//    }
    private static final long serialVersionUID = -8579725218176379779L;

    @Override
    public void cadastrar(TOTarefa to) throws Exception {
        Tarefa t = new Tarefa();
        getConverter().getModelFromTO(to, t);
        getEntityManager().persist(t);
    }

    @Override
    public TOTarefa buscarPorId(Integer id) throws Exception {
        Tarefa t = getEntityManager().find(Tarefa.class, id);
        TOTarefa to = new TOTarefa();
        getConverter().getTOFromModel(t, to);
        
        return to;
    }

    @Override
    public void atualizar(TOTarefa to) throws Exception {
        Tarefa t = new Tarefa();
        getConverter().getModelFromTO(to, t);
        getEntityManager().merge(t);
    }
    
    public List<TOTarefa> listarTodos(Boolean ativo) throws Exception {
        List<Tarefa> tarefas = new ArrayList<>();
        List<TOTarefa> toTarefas = new ArrayList<>();
        
        StringJoiner sql = new StringJoiner(QR_NL);
        sql
        .add("SELECT t")
        .add("FROM ".concat(Tarefa.class.getName()).concat(" t "))
        .add("WHERE t.ativo = :pAtivo");

        tarefas = getEntityManager().createQuery(sql.toString(), Tarefa.class)
                                    .setParameter("pAtivo", ativo)
                                    .getResultList();
        
        for (Tarefa t : tarefas) {
            TOTarefa to = new TOTarefa();
            getConverter().getTOFromModel(t, to);
            toTarefas.add(to);
        }

        return toTarefas;
    }

}
