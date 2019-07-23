package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.model.Tarefa;

@Stateless
public class TarefaDao extends AbstractBaseDao<Tarefa> {

    private static final long serialVersionUID = -8579725218176379779L;

    public List<Tarefa> listarTodos(Boolean pendente) {
        List<Tarefa> tarefas = new ArrayList<>();

        tarefas = getEntityManager()
                .createQuery("SELECT t FROM Tarefa t WHERE t.pendente = :pPendente", Tarefa.class)
                .setParameter("pPendente", pendente).getResultList();

        return tarefas;
    }

    @Override
    public Class<?> getModelClass() {
        return Tarefa.class;
    }
}
