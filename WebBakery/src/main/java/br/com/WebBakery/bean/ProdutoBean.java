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
import javax.transaction.Transactional;

import br.com.WebBakery.dao.ProdutoDao;
import br.com.WebBakery.dao.ReceitaDao;
import br.com.WebBakery.model.Produto;
import br.com.WebBakery.model.Receita;
import br.com.WebBakery.validator.ProdutoValidator;

@Named
@ViewScoped
public class ProdutoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;
    @Inject
    transient private FacesContext context;

    private Produto produto;
    private List<Produto> produtos;
    private ProdutoDao produtoDao;

    private Receita receitaSelecionada;
    private List<Receita> receitas;
    private ReceitaDao receitaDao;

    private ProdutoValidator validator;

    @PostConstruct
    private void init() {
        this.produto = new Produto();
        this.produtos = new ArrayList<>();
        this.validator = new ProdutoValidator(this.produto);
        this.produtoDao = new ProdutoDao(this.em);

        this.receitaDao = new ReceitaDao(this.em);
        this.receitaSelecionada = new Receita();
        this.receitas = new ArrayList<>();
        initProdutos();
        initReceitas();
    }

    @Transactional
    public void cadastrar() {
        if (this.produto.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        if (this.validator.getMessages().isEmpty()) {
            this.produto = new Produto();
        }
        atualizarTela();
    }

    private void efetuarCadastro() {
        List<Produto> todosOsProdutos = this.produtoDao.listarTodos();
        if (this.validator.isValid() && !this.validator.existe(todosOsProdutos)) {
            this.produto.setAtivo(true);
            this.produtoDao.cadastrar(this.produto);
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                "Produto cadastrado com sucesso!",
                                                "Produto cadastrado com sucesso!"));
        }
    }

    private void efetuarAtualizacao() {
        if (this.validator.isValid()) {
            this.produtoDao.atualizar(this.produto);
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                "Produto atualizado com sucesso!",
                                                "Produto atualizado com sucesso!"));
        }
    }

    @Transactional
    public void inativar(Produto produto) {
        produto.setAtivo(false);
        this.produtoDao.atualizar(produto);
        initProdutos();
    }

    public void carregar(Produto produto) {
        this.validator = new ProdutoValidator(produto);
        this.produto = produto;
    }

    private void atualizarTela() {
        this.validator.showMessages();
        this.validator.clearMessages();
        this.validator = new ProdutoValidator(this.produto);
        initProdutos();
    }

    private void initProdutos() {
        this.produtos = this.produtoDao.listarTodos(true);
    }

    private void initReceitas() {
        this.receitas = this.receitaDao.listarTodos(true);
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public FacesContext getContext() {
        return context;
    }

    public void setContext(FacesContext context) {
        this.context = context;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public ProdutoValidator getValidator() {
        return validator;
    }

    public void setValidator(ProdutoValidator validator) {
        this.validator = validator;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Receita getReceitaSelecionada() {
        return receitaSelecionada;
    }

    public void setReceitaSelecionada(Receita receitaSelecionada) {
        this.receitaSelecionada = receitaSelecionada;
    }

    public List<Receita> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<Receita> receitas) {
        this.receitas = receitas;
    }

}
