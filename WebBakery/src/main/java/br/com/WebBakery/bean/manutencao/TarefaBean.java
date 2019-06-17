package br.com.WebBakery.bean.manutencao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import br.com.WebBakery.dao.ProdutoDao;
import br.com.WebBakery.dao.TarefaDao;
import br.com.WebBakery.model.Produto;
import br.com.WebBakery.model.Tarefa;
import br.com.WebBakery.util.Date_Util;
import br.com.WebBakery.util.FacesUtil;
import br.com.WebBakery.validator.TarefaValidator;

@Named
@ViewScoped
public class TarefaBean implements Serializable {

    private static final long serialVersionUID = 2625499918546247472L;

    private static final String UPDATED_SUCCESSFULLY = "Tarefa atualizada com sucesso!";

    private static final String REGISTERED_SUCCESSFULLY = "Tarefa cadastrada com sucesso!";

    @PersistenceContext
    private EntityManager em;
    @Inject
    transient private FacesContext context;

    private Tarefa tarefa;
    private TarefaDao tarefaDao;

    private ProdutoDao produtoDao;
    private List<Produto> produtos;
    private List<Produto> produtosFiltrados;
    private Produto produtoSelecionado;

    private TarefaValidator validator;

    @PostConstruct
    private void init() {
        this.tarefa = new Tarefa();
        this.tarefaDao = new TarefaDao(this.em);
        this.produtoDao = new ProdutoDao(this.em);
        initQuantidadeProdutoTarefa();
        initProdutos();
        verificaTarefaSessao();
    }

    @Transactional
    public void cadastrar() {
        this.validator = new TarefaValidator(this.tarefa);
        if (this.tarefa.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        atualizarTela();
    }

    private void efetuarCadastro() {
        if (this.validator.isValid()) {
            this.tarefa.setPendente(true);
            this.tarefaDao.cadastrar(this.tarefa);
            context.addMessage(null, new FacesMessage(REGISTERED_SUCCESSFULLY));
        }
    }

    private void efetuarAtualizacao() {
        if (this.validator.isValid()) {
            this.tarefaDao.atualizar(this.tarefa);
            context.addMessage(null, new FacesMessage(UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.tarefa = new Tarefa();
        this.validator.showMessages();
        this.validator.clearMessages();
        initQuantidadeProdutoTarefa();
    }

    private void verificaTarefaSessao() {
        Integer tarefaId = (Integer) FacesUtil.getHTTPSession().getAttribute("TarefaID");
        if (tarefaId != null) {
            this.tarefa = tarefaDao.buscarPorId(tarefaId);
            FacesUtil.getHTTPSession().removeAttribute("TarefaID");
        }
    }

    private void initProdutos() {
        this.produtos = this.produtoDao.listarTodos(true);
    }

    private void initQuantidadeProdutoTarefa() {
        this.tarefa.setQuantidade(1);
    }

    public void setarProduto() {
        this.tarefa.setProduto(this.produtoSelecionado);
    }

    public String dataFormatada(Date data) {
        return Date_Util.formatar("dd/MM/yyyy", data);
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public Produto getProdutoSelecionado() {
        return produtoSelecionado;
    }

    public void setProdutoSelecionado(Produto produtoSelecionado) {
        this.produtoSelecionado = produtoSelecionado;
    }

    public List<Produto> getProdutosFiltrados() {
        return produtosFiltrados;
    }

    public void setProdutosFiltrados(List<Produto> produtosFiltrados) {
        this.produtosFiltrados = produtosFiltrados;
    }

}