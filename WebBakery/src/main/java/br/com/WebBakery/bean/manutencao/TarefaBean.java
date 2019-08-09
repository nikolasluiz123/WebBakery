package br.com.WebBakery.bean.manutencao;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.ProdutoDao;
import br.com.WebBakery.dao.TarefaDao;
import br.com.WebBakery.to.TOProduto;
import br.com.WebBakery.to.TOTarefa;
import br.com.WebBakery.util.Date_Util;
import br.com.WebBakery.validator.TarefaValidator;

@Named(TarefaBean.BEAN_NAME)
@ViewScoped
public class TarefaBean extends AbstractBaseRegisterMBean<TOTarefa> {

    public static final String BEAN_NAME = "tarefaBean";

    private static final long serialVersionUID = 2625499918546247472L;

    private static final String UPDATED_SUCCESSFULLY = "TOTarefa atualizada com sucesso!";

    private static final String REGISTERED_SUCCESSFULLY = "TOTarefa cadastrada com sucesso!";

    private TOTarefa tarefa;
    @Inject
    private TarefaDao tarefaDao;

    @Inject
    private ProdutoDao produtoDao;
    private List<TOProduto> produtos;
    private List<TOProduto> produtosFiltrados;
    private TOProduto produtoSelecionado;

    private TarefaValidator validator;

    @PostConstruct
    private void init() {
        this.tarefa = new TOTarefa();
        initQuantidadeProdutoTarefa();
        initProdutos();
    }

    @Transactional
    public void cadastrar() {
        try {
            this.validator = new TarefaValidator(this.tarefa);
            if (this.tarefa.getId() == null) {
                efetuarCadastro();
            } else {
                efetuarAtualizacao();
            }
            atualizarTela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void efetuarCadastro() throws Exception {
        if (this.validator.isValid()) {
            this.tarefa.setAtivo(true);
            this.tarefaDao.cadastrar(this.tarefa);
            getContext().addMessage(null, new FacesMessage(REGISTERED_SUCCESSFULLY));
        }
    }

    private void efetuarAtualizacao() throws Exception {
        if (this.validator.isValid()) {
            this.tarefaDao.atualizar(this.tarefa);
            getContext().addMessage(null, new FacesMessage(UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.tarefa = new TOTarefa();
        this.validator.showMessages();
        this.validator.clearMessages();
        initQuantidadeProdutoTarefa();
    }

    private void initProdutos() {
        try {
            this.produtos = this.produtoDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initQuantidadeProdutoTarefa() {
        this.tarefa.setQuantidade(1);
    }

    public void setarProduto() {
        this.tarefa.setToProduto(this.produtoSelecionado);
    }

    public String dataFormatada(Date data) {
        return Date_Util.formatar("dd/MM/yyyy", data);
    }

    public TOTarefa getTarefa() {
        return tarefa;
    }

    public void setToTarefa(TOTarefa tarefa) {
        this.tarefa = tarefa;
    }

    public List<TOProduto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<TOProduto> produtos) {
        this.produtos = produtos;
    }

    public List<TOProduto> getProdutosFiltrados() {
        return produtosFiltrados;
    }

    public void setProdutosFiltrados(List<TOProduto> produtosFiltrados) {
        this.produtosFiltrados = produtosFiltrados;
    }

    public TOProduto getProdutoSelecionado() {
        return produtoSelecionado;
    }

    public void setProdutoSelecionado(TOProduto produtoSelecionado) {
        this.produtoSelecionado = produtoSelecionado;
    }

}
