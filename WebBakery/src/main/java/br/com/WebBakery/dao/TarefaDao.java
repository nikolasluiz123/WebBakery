package br.com.WebBakery.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBaker.interfaces.IBaseDao;
import br.com.WebBakery.model.Tarefa;

@Stateless
public class TarefaDao implements IBaseDao<Tarefa> {

    private static final long serialVersionUID = -8579725218176379779L;

    @PersistenceContext
    private EntityManager em;

    public TarefaDao(EntityManager em) {
        this.em = em;
    }

    public TarefaDao() {
    }

    @Override
    public void cadastrar(Tarefa model) {
        em.persist(model);
    }

    @Override
    public List<Tarefa> listarTodos(Boolean pendente) {
        List<Tarefa> tarefas = new ArrayList<>();

        tarefas = em
                .createQuery("SELECT t FROM Tarefa t WHERE t.pendente = :pPendente", Tarefa.class)
                .setParameter("pPendente", pendente).getResultList();

        return tarefas;
    }

    @Override
    public Tarefa buscarPorId(Integer id) {
        return em.find(Tarefa.class, id);
    }

    @Override
    public void atualizar(Tarefa model) {
        em.merge(model);
    }
}
