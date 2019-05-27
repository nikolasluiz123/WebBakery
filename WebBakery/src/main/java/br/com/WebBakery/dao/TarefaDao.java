package br.com.WebBakery.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBakery.model.Tarefa;

@Stateless
public class TarefaDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    public TarefaDao(EntityManager em) {
        this.em = em;
    }

    public TarefaDao() {
    }

    public void cadastrar(Tarefa tarefa) {
        em.persist(tarefa);
    }

    public List<Tarefa> listarTodos(Boolean pendente) {

        List<Tarefa> tarefas = new ArrayList<>();

        tarefas = em
                .createQuery("SELECT t FROM Tarefa t WHERE t.pendente = :pPendente ORDER BY t.dataInicio",
                             Tarefa.class)
                .setParameter("pPendente", pendente).getResultList();

        return tarefas;
    }

    public Tarefa buscarPelaId(Integer id) {
        return em.find(Tarefa.class, id);
    }

    public void atualizar(Tarefa tarefa) {
        em.merge(tarefa);
    }
}
