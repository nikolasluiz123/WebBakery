package br.com.WebBakery.bean.manutencao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.ProdutoDao;
import br.com.WebBakery.dao.ReceitaDao;
import br.com.WebBakery.model.Produto;
import br.com.WebBakery.model.Receita;
import br.com.WebBakery.validator.ProdutoValidator;

@Named
@ViewScoped
public class ProdutoBean extends AbstractBaseRegisterMBean<Produto> {

    private static final long serialVersionUID = 8861448133925257777L;

    private static final String UPDATED_SUCCESSFULLY = "Produto atualizado com sucesso!";

    private static final String REGISTERED_SUCCESSFULLY = "Produto cadastrado com sucesso!";

    private Produto produto;
    @Inject
    private ProdutoDao produtoDao;

    private Receita receitaSelecionada;
    private List<Receita> receitas;
    private List<Receita> receitasFiltradas;
    @Inject
    private ReceitaDao receitaDao;

    private ProdutoValidator validator;

    @PostConstruct
    private void init() {
        this.produto = new Produto();

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
            getContext().addMessage(null, new FacesMessage(REGISTERED_SUCCESSFULLY));
        }
    }

    private void efetuarAtualizacao() {
        if (this.validator.isValid()) {
            this.produtoDao.atualizar(this.produto);
            getContext().addMessage(null, new FacesMessage(UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.produto = new Produto();
        this.validator.showMessages();
        this.validator.clearMessages();
    }

    private void verificaProdutoSessao() {
        this.produto = getObjetoSessao("ProdutoID", produtoDao, produto);
    
        if (produto == null) {
            this.produto = new Produto();
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

    public ProdutoValidator getValidator() {
        return validator;
    }

    public void setValidator(ProdutoValidator validator) {
        this.validator = validator;
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
