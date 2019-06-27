package br.com.WebBakery.bean.consulta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBakery.dao.ProdutoVendaDao;
import br.com.WebBakery.dao.VendaDao;
import br.com.WebBakery.model.ProdutoVenda;
import br.com.WebBakery.model.Venda;
import br.com.WebBakery.util.String_Util;

@Named
@ViewScoped
public class ListaVendaBean implements Serializable {

    private static final long serialVersionUID = 4010909718723087342L;

    @PersistenceContext
    private EntityManager em;

    private VendaDao vendaDao;
    private List<Venda> vendas;
    private List<Venda> vendasFiltradas;

    private ProdutoVendaDao produtoVendaDao;
    private List<ProdutoVenda> produtosVenda;
    private List<ProdutoVenda> produtosVendaFiltradas;

    private Double valorTotalPago;
    private String valorTotalPagoFormatado;

    @PostConstruct
    private void init() {
        this.vendaDao = new VendaDao(this.em);
        this.vendas = new ArrayList<>();
        this.produtoVendaDao = new ProdutoVendaDao(this.em);
        this.produtosVenda = new ArrayList<>();
        this.valorTotalPago = 0.0;
        initListVendas();
    }

    private Double calculaValorTotalPago() {
        Double valorTotalPago = 0.0;
        for (ProdutoVenda produtoVenda : produtosVenda) {
            Double preco = produtoVenda.getProduto().getPreco();
            Integer quantidade = produtoVenda.getQuantidade();
            valorTotalPago += preco * quantidade;
        }
        this.valorTotalPagoFormatado = String_Util
                .formatarDoubleParaValorMonetario(valorTotalPago);
        return valorTotalPago;
    }

    private void initListVendas() {
        this.vendas = this.vendaDao.listarTodos();
    }

    public void initListProdutoVendas(Integer id) {
        this.produtosVenda = this.produtoVendaDao.buscarPorIdVenda(id);
        calculaValorTotalPago();
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

    public List<ProdutoVenda> getProdutosVenda() {
        return produtosVenda;
    }

    public void setProdutosVenda(List<ProdutoVenda> produtosVenda) {
        this.produtosVenda = produtosVenda;
    }

    public List<ProdutoVenda> getProdutosVendaFiltradas() {
        return produtosVendaFiltradas;
    }

    public void setProdutosVendaFiltradas(List<ProdutoVenda> produtosVendaFiltradas) {
        this.produtosVendaFiltradas = produtosVendaFiltradas;
    }

    public Double getValorTotalPago() {
        return valorTotalPago;
    }

    public void setValorTotalPago(Double valorTotalPago) {
        this.valorTotalPago = valorTotalPago;
    }

    public String getValorTotalPagoFormatado() {
        return valorTotalPagoFormatado;
    }

    public void setValorTotalPagoFormatado(String valorTotalPagoFormatado) {
        this.valorTotalPagoFormatado = valorTotalPagoFormatado;
    }

}
