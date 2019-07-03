package br.com.WebBakery.bean.consulta;

import java.io.IOException;
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

import br.com.WebBakery.dao.ProdutoDao;
import br.com.WebBakery.model.Produto;
import br.com.WebBakery.util.Faces_Util;

@Named
@ViewScoped
public class ListaProdutoBean implements Serializable {

    private static final long serialVersionUID = -5854537667186626713L;

    private static final String PRODUTO_INATIVATED_SUCCESSFULLY = "Produto inativado com sucesso!";

    @PersistenceContext
    private EntityManager em;
    @Inject
    transient private FacesContext context;

    private ProdutoDao produtoDao;
    private List<Produto> produtos;
    private List<Produto> produtosFiltrados;

    @PostConstruct
    private void init() {
        this.produtos = new ArrayList<>();
        this.produtoDao = new ProdutoDao(this.em);
        initProdutos();
    }

    @Transactional
    public void inativar(Produto produto) {
        produto.setAtivo(false);
        this.produtoDao.atualizar(produto);
        initProdutos();
        context.addMessage(null, new FacesMessage(PRODUTO_INATIVATED_SUCCESSFULLY));
    }

    public void carregar(Integer produtoID) throws IOException {
        HttpSession session = Faces_Util.getHTTPSession();
        session.setAttribute("ProdutoID", produtoID);
        context.getExternalContext().redirect("cadastroProduto.xhtml");
    }

    private void initProdutos() {
        this.produtos = this.produtoDao.listarTodos(true);
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Produto> getProdutosFiltrados() {
        return produtosFiltrados;
    }

    public void setProdutosFiltrados(List<Produto> produtosFiltrados) {
        this.produtosFiltrados = produtosFiltrados;
    }

}
