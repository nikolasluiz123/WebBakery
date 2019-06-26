package br.com.WebBakery.bean.consulta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBakery.dao.ProdutoVendaDao;
import br.com.WebBakery.dao.VendaDao;
import br.com.WebBakery.model.ProdutoVenda;
import br.com.WebBakery.model.Venda;

@Named
@ViewScoped
public class ListaVendaBean implements Serializable {

    private static final long serialVersionUID = 4010909718723087342L;

    @PersistenceContext
    private EntityManager em;
    @Inject
    transient private FacesContext context;

    private VendaDao vendaDao;
    private List<Venda> vendas;
    private List<Venda> vendasFiltradas;
    private Venda vendaSelecionada;

    private ProdutoVendaDao produtoVendaDao;
    private List<ProdutoVenda> produtoVendas;
    private List<ProdutoVenda> produtoVendasFiltradas;

    @PostConstruct
    private void init() {
        this.vendaDao = new VendaDao(this.em);
        this.vendas = new ArrayList<>();
        this.produtoVendaDao = new ProdutoVendaDao(this.em);
        this.produtoVendas = new ArrayList<>();
        initListVendas();
    }

    private void initListVendas() {
        this.vendas = this.vendaDao.listarTodos();
    }

    private void initListProdutoVendas() {
        this.produtoVendas = this.produtoVendaDao.listarTodos(true, vendaSelecionada.getId());
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }

    public List<Venda> getVendasFiltradas() {
        return vendasFiltradas;
    }

    public void setVendasFiltradas(List<Venda> vendasFiltradas) {
        this.vendasFiltradas = vendasFiltradas;
    }

    public List<ProdutoVenda> getProdutoVendas() {
        return produtoVendas;
    }

    public void setProdutoVendas(List<ProdutoVenda> produtoVendas) {
        this.produtoVendas = produtoVendas;
    }

    public List<ProdutoVenda> getProdutoVendasFiltradas() {
        return produtoVendasFiltradas;
    }

    public void setProdutoVendasFiltradas(List<ProdutoVenda> produtoVendasFiltradas) {
        this.produtoVendasFiltradas = produtoVendasFiltradas;
    }

}
