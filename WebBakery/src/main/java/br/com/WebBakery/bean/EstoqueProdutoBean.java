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

import br.com.WebBakery.dao.EstoqueProdutoDao;
import br.com.WebBakery.model.EstoqueProduto;
import br.com.WebBakery.validator.EstoqueProdutoValidator;

@Named
@ViewScoped
public class EstoqueProdutoBean implements Serializable {

    private static final String UPDATED_SUCCESSFULLY = "Estoque de produto atualizado com sucesso!";

    private static final String REGISTERED_SUCCESSFULLY = "Produto cadastrada no estoque com sucesso!";

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;
    @Inject
    transient private FacesContext context;

    private EstoqueProduto estoqueProduto;
    private EstoqueProduto epDoBanco;
    private EstoqueProdutoDao estoqueProdutoDao;

    private List<EstoqueProduto> produtosEstoque;
    private List<EstoqueProduto> produtosEstoqueFiltrados;

    private EstoqueProdutoValidator validator;

    @PostConstruct
    private void init() {
        this.estoqueProduto = new EstoqueProduto();
        this.estoqueProdutoDao = new EstoqueProdutoDao(this.em);
        this.validator = new EstoqueProdutoValidator(this.estoqueProduto);
        initListaEstoqueProdutos();
    }

    @Transactional
    public void cadastrar() {
        epDoBanco = validator.existe(this.estoqueProdutoDao.listarTodos());
        if (epDoBanco.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        if (this.validator.getMessages().isEmpty()) {
            this.estoqueProduto = new EstoqueProduto();
        }
        atualizarTela();
    }

    private void efetuarCadastro() {
        if (this.validator.isValid()) {
            this.estoqueProdutoDao.cadastrar(this.estoqueProduto);
            context.addMessage(null, new FacesMessage(REGISTERED_SUCCESSFULLY));
        }
    }

    private void efetuarAtualizacao() {
        if (this.validator.isValid()) {
            Integer qtd = 0;
            if (this.epDoBanco.getId() != null)
                qtd = this.epDoBanco.getQuantidade();
            this.estoqueProdutoDao.atualizar(this.epDoBanco, qtd);
            context.addMessage(null, new FacesMessage(UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.validator.showMessages();
        this.validator.clearMessages();
        this.validator = new EstoqueProdutoValidator(this.estoqueProduto);
    }

    private void initListaEstoqueProdutos() {
        this.produtosEstoque = this.estoqueProdutoDao.listarTodos();
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public EstoqueProduto getEstoqueProduto() {
        return estoqueProduto;
    }

    public void setEstoqueProduto(EstoqueProduto estoqueProduto) {
        this.estoqueProduto = estoqueProduto;
    }

    public EstoqueProdutoDao getEstoqueProdutoDao() {
        return estoqueProdutoDao;
    }

    public void setEstoqueProdutoDao(EstoqueProdutoDao estoqueProdutoDao) {
        this.estoqueProdutoDao = estoqueProdutoDao;
    }

    public List<EstoqueProduto> getProdutosEstoque() {
        return produtosEstoque;
    }

    public void setProdutosEstoque(List<EstoqueProduto> produtosEstoque) {
        this.produtosEstoque = produtosEstoque;
    }

    public EstoqueProdutoValidator getValidator() {
        return validator;
    }

    public void setValidator(EstoqueProdutoValidator validator) {
        this.validator = validator;
    }

    public List<EstoqueProduto> getProdutosEstoqueFiltrados() {
        return produtosEstoqueFiltrados;
    }

    public void setProdutosEstoqueFiltrados(List<EstoqueProduto> produtosEstoqueFiltrados) {
        this.produtosEstoqueFiltrados = produtosEstoqueFiltrados;
    }

    public FacesContext getContext() {
        return context;
    }

    public void setContext(FacesContext context) {
        this.context = context;
    }

}
