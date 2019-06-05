package br.com.WebBakery.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import br.com.WebBakery.dao.EstoqueProdutoDao;
import br.com.WebBakery.dao.TarefaDao;
import br.com.WebBakery.model.EstoqueProduto;
import br.com.WebBakery.model.Tarefa;
import br.com.WebBakery.util.FacesUtil;
import br.com.WebBakery.validator.EstoqueProdutoValidator;

@Named
@ViewScoped
public class ListaTarefasBean implements Serializable {

    private static String COMPLETE_SUCCESSFULLY = "Tarefa concluída com sucessor!";
    
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

    private EstoqueProdutoBean estoqueProdutoBean;

    @PostConstruct
    private void init() {
        this.tarefaDao = new TarefaDao(this.em);
        this.tarefasPendentes = new ArrayList<>();
        this.tarefasConcluidas = new ArrayList<>();
        initTarefasPendentes();
        initTarefasConcluidas();

        this.estoqueProdutoBean = new EstoqueProdutoBean();
        this.estoqueProdutoBean.setEm(this.em);
    }

    public void carregar(Integer tarefaID) throws Exception {
        HttpSession session = FacesUtil.getHTTPSession();
        session.setAttribute("TarefaID", tarefaID);
        context.getExternalContext().redirect("cadastroTarefa.xhtml");
    }

    @Transactional
    public void concluir(Tarefa tarefa) {
        cadastrarProdutoEstoque(tarefa);
        tarefa.setPendente(false);
        this.tarefaDao.atualizar(tarefa);
        this.tarefasPendentes.remove(tarefa);
        this.tarefasConcluidas.add(tarefa);
        context.addMessage(null, new FacesMessage(COMPLETE_SUCCESSFULLY));
    }

    private void cadastrarProdutoEstoque(Tarefa tarefa) {
        EstoqueProduto estoqueProduto = new EstoqueProduto();
        estoqueProduto.setProduto(tarefa.getProduto());
        estoqueProduto.setQuantidade(tarefa.getQuantidade());
        preparaCadastroEstoque(estoqueProduto);
    }

    private void preparaCadastroEstoque(EstoqueProduto estoqueProduto) {
        this.estoqueProdutoBean.setEstoqueProduto(estoqueProduto);
        this.estoqueProdutoBean.setValidator(new EstoqueProdutoValidator(estoqueProduto));
        this.estoqueProdutoBean.setEstoqueProdutoDao(new EstoqueProdutoDao(this.em));
        this.estoqueProdutoBean.setContext(this.context);
        this.estoqueProdutoBean.cadastrar();
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
