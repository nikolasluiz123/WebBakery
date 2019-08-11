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

    private TOTarefa toTarefa;
    @Inject
    private TarefaDao tarefaDao;

    @Inject
    private ProdutoDao produtoDao;
    private List<TOProduto> toProdutos;
    private List<TOProduto> toProdutosFiltrados;
    private TOProduto toProdutoSelecionado;

    private TarefaValidator validator;

    @PostConstruct
    private void init() {
        this.toTarefa = new TOTarefa();
        initQuantidadeProdutoTarefa();
        initProdutos();
    }

    @Transactional
    public void cadastrar() {
        try {
            this.validator = new TarefaValidator(this.toTarefa);
            if (this.toTarefa.getId() == null) {
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
            this.toTarefa.setAtivo(true);
            this.tarefaDao.cadastrar(this.toTarefa);
            getContext().addMessage(null, new FacesMessage(REGISTERED_SUCCESSFULLY));
        }
    }

    private void efetuarAtualizacao() throws Exception {
        if (this.validator.isValid()) {
            this.tarefaDao.atualizar(this.toTarefa);
            getContext().addMessage(null, new FacesMessage(UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.toTarefa = new TOTarefa();
        this.validator.showMessages();
        this.validator.clearMessages();
        initQuantidadeProdutoTarefa();
    }

    private void initProdutos() {
        try {
            this.toProdutos = this.produtoDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initQuantidadeProdutoTarefa() {
        this.toTarefa.setQuantidade(1);
    }

    public void setarProduto() {
        this.toTarefa.setToProduto(this.toProdutoSelecionado);
    }

    public String dataFormatada(Date data) {
        return Date_Util.formatar("dd/MM/yyyy", data);
    }

    public TOTarefa getToTarefa() {
        return toTarefa;
    }

    public void setToTarefa(TOTarefa toTarefa) {
        this.toTarefa = toTarefa;
    }

    public List<TOProduto> getToProdutos() {
        return toProdutos;
    }

    public void setToProdutos(List<TOProduto> toProdutos) {
        this.toProdutos = toProdutos;
    }

    public List<TOProduto> getToProdutosFiltrados() {
        return toProdutosFiltrados;
    }

    public void setToProdutosFiltrados(List<TOProduto> toProdutosFiltrados) {
        this.toProdutosFiltrados = toProdutosFiltrados;
    }

    public TOProduto getToProdutoSelecionado() {
        return toProdutoSelecionado;
    }

    public void setToProdutoSelecionado(TOProduto toProdutoSelecionado) {
        this.toProdutoSelecionado = toProdutoSelecionado;
    }

}
