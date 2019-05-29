package br.com.WebBakery.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import br.com.WebBakery.dao.TarefaDao;
import br.com.WebBakery.model.Tarefa;
import br.com.WebBakery.util.FacesUtil;

@Named
@ViewScoped
public class ListaTarefasBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;
    @Inject
    transient private FacesContext context;

    private TarefaDao tarefaDao;
    private List<Tarefa> tarefasPendentes;
    private List<Tarefa> tarefasPendentesFiltradas;
    private List<Tarefa> tarefasConcluidas;
    private List<Tarefa> tarefasConcuidasFiltradas;

    @PostConstruct
    private void init() {
        this.tarefaDao = new TarefaDao(this.em);
        this.tarefasPendentes = new ArrayList<>();
        this.tarefasConcluidas = new ArrayList<>();
        initTarefasPendentes();
        initTarefasConcluidas();
    }

    public void carregar(Integer tarefaID) throws Exception {
        HttpSession session = FacesUtil.getHTTPSession();
        session.setAttribute("TarefaID", tarefaID);
        context.getExternalContext().redirect("cadastroTarefa.xhtml");
    }

    @Transactional
    public void concluir(Tarefa tarefa) {
        tarefa.setPendente(false);
        this.tarefaDao.atualizar(tarefa);
        this.tarefasPendentes.remove(tarefa);
        this.tarefasConcluidas.add(tarefa);
    }

    private void initTarefasPendentes() {
        this.tarefasPendentes = this.tarefaDao.listarTodos(true);
    }

    private void initTarefasConcluidas() {
        this.tarefasConcluidas = this.tarefaDao.listarTodos(false);
    }

    public List<Tarefa> getTarefasPendentes() {
        return tarefasPendentes;
    }

    public void setTarefasPendentes(List<Tarefa> tarefasPendentes) {
        this.tarefasPendentes = tarefasPendentes;
    }

    public List<Tarefa> getTarefasConcluidas() {
        return tarefasConcluidas;
    }

    public void setTarefasConcluidas(List<Tarefa> tarefasConcuidas) {
        this.tarefasConcluidas = tarefasConcuidas;
    }

    public List<Tarefa> getTarefasPendentesFiltradas() {
        return tarefasPendentesFiltradas;
    }

    public void setTarefasPendentesFiltradas(List<Tarefa> tarefasPendentesFiltradas) {
        this.tarefasPendentesFiltradas = tarefasPendentesFiltradas;
    }

    public List<Tarefa> getTarefasConcuidasFiltradas() {
        return tarefasConcuidasFiltradas;
    }

    public void setTarefasConcuidasFiltradas(List<Tarefa> tarefasConcuidasFiltradas) {
        this.tarefasConcuidasFiltradas = tarefasConcuidasFiltradas;
    }

}
