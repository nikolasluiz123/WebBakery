package br.com.WebBakery.bean;

import java.io.Serializable;
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
import br.com.WebBakery.util.FacesUtil;
import br.com.WebBakery.validator.TarefaValidator;

@Named
@ViewScoped
public class TarefaBean implements Serializable {

    private static final String UPDATED_SUCCESSFULLY = "Tarefa atualizada com sucesso!";

    private static final String REGISTERED_SUCCESSFULLY = "Tarefa cadastrada com sucesso!";

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;
    @Inject
    transient private FacesContext context;

    private Tarefa tarefa;
    private TarefaDao tarefaDao;

    private ProdutoDao produtoDao;
    private List<Produto> produtos;
    private Produto produtoSelecionado;

    private TarefaValidator validator;

    @PostConstruct
    private void init() {
        this.tarefa = new Tarefa();
        this.tarefaDao = new TarefaDao(this.em);
        this.produtoDao = new ProdutoDao(this.em);
        this.validator = new TarefaValidator(this.tarefa);
        initQuantidadeProdutoTarefa();
        initProdutos();
        verificaTarefaSessao();
    }

    private void verificaTarefaSessao() {
        Integer tarefaId = (Integer) FacesUtil.getHTTPSession().getAttribute("TarefaID");
        if (tarefaId != null) {
            this.tarefa = tarefaDao.buscarPelaId(tarefaId);
        }
    }

    private void initProdutos() {
        this.produtos = this.produtoDao.listarTodos(true);
    }

    private void initQuantidadeProdutoTarefa() {
        this.tarefa.setQuantidade(1);
    }

    @Transactional
    public void cadastrar() {
        if (this.tarefa.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        if (this.validator.getMessages().isEmpty()) {
            this.tarefa = new Tarefa();
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
        this.validator.showMessages();
        this.validator.clearMessages();
        this.validator = new TarefaValidator(this.getTarefa());
        initQuantidadeProdutoTarefa();
    }

    public void setarProduto() {
        this.tarefa.setProduto(this.produtoSelecionado);
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

}
