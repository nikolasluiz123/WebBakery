package br.com.WebBakery.bean.manutencao;

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
import br.com.WebBakery.util.FacesUtil;
import br.com.WebBakery.validator.ProdutoValidator;

@Named
@ViewScoped
public class ProdutoBean implements Serializable {

    private static final long serialVersionUID = 8861448133925257777L;

    private static final String UPDATED_SUCCESSFULLY = "Produto atualizado com sucesso!";

    private static final String REGISTERED_SUCCESSFULLY = "Produto cadastrado com sucesso!";

    @PersistenceContext
    private EntityManager em;
    @Inject
    transient private FacesContext context;

    private Produto produto;
    private ProdutoDao produtoDao;

    private Receita receitaSelecionada;
    private List<Receita> receitas;
    private List<Receita> receitasFiltradas;
    private ReceitaDao receitaDao;

    private ProdutoValidator validator;

    @PostConstruct
    private void init() {
        this.produto = new Produto();
        this.produtoDao = new ProdutoDao(this.em);

        this.receitaDao = new ReceitaDao(this.em);
        this.receitaSelecionada = new Receita();
        this.receitas = new ArrayList<>();
        initReceitas();
        verificaProdutoSessao();
    }

    @Transactional
    public void cadastrar() {
        this.validator = new ProdutoValidator(this.produto);
        if (this.produto.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        atualizarTela();
    }

    private void efetuarCadastro() {
        if (this.validator.isValid()) {
            this.produto.setAtivo(true);
            this.produtoDao.cadastrar(this.produto);
            context.addMessage(null, new FacesMessage(REGISTERED_SUCCESSFULLY));
        }
    }

    private void efetuarAtualizacao() {
        if (this.validator.isValid()) {
            this.produtoDao.atualizar(this.produto);
            context.addMessage(null, new FacesMessage(UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.produto = new Produto();
        this.validator.showMessages();
        this.validator.clearMessages();
    }

    private void verificaProdutoSessao() {
        Integer produtoId = (Integer) FacesUtil.getHTTPSession().getAttribute("ProdutoID");
        if (produtoId != null) {
            this.produto = produtoDao.buscarPorId(Produto.class, produtoId);
            FacesUtil.getHTTPSession().removeAttribute("ProdutoID");
        }
    }

    private void initReceitas() {
        this.receitas = this.receitaDao.listarTodos(true);
    }

    public void setarReceita() {
        this.produto.setReceita(this.receitaSelecionada);
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

    public List<Receita> getReceitasFiltradas() {
        return receitasFiltradas;
    }

    public void setReceitasFiltradas(List<Receita> receitasFiltradas) {
        this.receitasFiltradas = receitasFiltradas;
    }

}
