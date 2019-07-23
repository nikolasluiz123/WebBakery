package br.com.WebBakery.bean.manutencao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.EstoqueProdutoDao;
import br.com.WebBakery.model.EstoqueProduto;

@Named
@ViewScoped
public class EstoqueProdutoBean extends AbstractBaseRegisterMBean<EstoqueProduto> {

    private static final String UPDATED_SUCCESSFULLY = "Estoque de produto atualizado com sucesso!";
    private static final String REGISTERED_SUCCESSFULLY = "Produto cadastrada no estoque com sucesso!";

    private static final long serialVersionUID = 579121007228763037L;

    private EstoqueProduto estoqueProduto;
    private EstoqueProduto epDoBanco;
    
    @Inject
    private EstoqueProdutoDao estoqueProdutoDao;

    private List<EstoqueProduto> produtosEstoque;
    private List<EstoqueProduto> produtosEstoqueFiltrados;

    @PostConstruct
    private void init() {
        this.estoqueProduto = new EstoqueProduto();
        initListaEstoqueProdutos();
    }

    @Transactional
    public void cadastrar() {
        this.epDoBanco = estoqueProdutoDao.existe(this.estoqueProduto.getProduto().getId());
        if (this.epDoBanco == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
    }

    private void efetuarCadastro() {
        this.estoqueProdutoDao.cadastrar(this.estoqueProduto);
        getContext().addMessage(null, new FacesMessage(REGISTERED_SUCCESSFULLY));
    }

    private void efetuarAtualizacao() {
        Integer quantidade = this.estoqueProduto.getQuantidade();
        this.estoqueProdutoDao.atualizar(this.epDoBanco, quantidade);
        getContext().addMessage(null, new FacesMessage(UPDATED_SUCCESSFULLY));
    }

    private void initListaEstoqueProdutos() {
        this.produtosEstoque = this.estoqueProdutoDao.listarTodos();
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

    public List<EstoqueProduto> getProdutosEstoqueFiltrados() {
        return produtosEstoqueFiltrados;
    }

    public void setProdutosEstoqueFiltrados(List<EstoqueProduto> produtosEstoqueFiltrados) {
        this.produtosEstoqueFiltrados = produtosEstoqueFiltrados;
    }

    public EstoqueProduto getEpDoBanco() {
        return epDoBanco;
    }

    public void setEpDoBanco(EstoqueProduto epDoBanco) {
        this.epDoBanco = epDoBanco;
    }

}
