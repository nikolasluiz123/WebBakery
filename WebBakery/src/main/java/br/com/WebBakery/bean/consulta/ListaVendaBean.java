package br.com.WebBakery.bean.consulta;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.WebBakery.abstractClass.AbstractBaseListMBean;
import br.com.WebBakery.dao.ProdutoVendaDao;
import br.com.WebBakery.dao.VendaDao;
import br.com.WebBakery.interfaces.IBaseListMBean;
import br.com.WebBakery.to.TOProdutoVenda;
import br.com.WebBakery.to.TOVenda;
import br.com.WebBakery.util.String_Util;

@Named(ListaVendaBean.BEAN_NAME)
@ViewScoped
public class ListaVendaBean extends AbstractBaseListMBean implements IBaseListMBean<TOVenda> {

    static final String BEAN_NAME = "listaVendaBean";

    private static final long serialVersionUID = 4010909718723087342L;

    private static final String VENDA_INATIVATED_SUCCESSFULLY = "Venda inativada com sucesso!";

    @Inject
    private VendaDao vendaDao;
    private List<TOVenda> vendas;
    private List<TOVenda> vendasFiltradas;

    @Inject
    private ProdutoVendaDao produtoVendaDao;
    private List<TOProdutoVenda> produtosVenda;
    private List<TOProdutoVenda> produtosVendaFiltradas;

    private Double valorTotalPago;
    private String valorTotalPagoFormatado;

    @PostConstruct
    private void init() {
        this.vendas = new ArrayList<>();
        this.produtosVenda = new ArrayList<>();
        this.valorTotalPago = 0.0;
        initListVendas();
    }

    private Double calculaValorTotalPago() {
        Double valorTotalPago = 0.0;
        for (TOProdutoVenda produtoVenda : produtosVenda) {
            Double preco = produtoVenda.getToProduto().getPreco();
            Integer quantidade = produtoVenda.getQuantidade();
            valorTotalPago += preco * quantidade;
        }
        this.valorTotalPagoFormatado = String_Util.formatarDoubleParaValorMonetario(valorTotalPago);
        return valorTotalPago;
    }

    private void initListVendas() {
        try {
            this.vendas = this.vendaDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initListProdutoVendas(Integer id) {
        try {
            this.produtosVenda = this.produtoVendaDao.buscarPorIdVenda(id);
            calculaValorTotalPago();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void inativar(TOVenda to) {
        try {
            to.setAtivo(false);
            this.vendaDao.salvar(to);
            getContext().addMessage(null, new FacesMessage(VENDA_INATIVATED_SUCCESSFULLY));
            initListVendas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TOVenda> getVendas() {
        return vendas;
    }

    public void setVendas(List<TOVenda> vendas) {
        this.vendas = vendas;
    }

    public List<TOVenda> getVendasFiltradas() {
        return vendasFiltradas;
    }

    public void setVendasFiltradas(List<TOVenda> vendasFiltradas) {
        this.vendasFiltradas = vendasFiltradas;
    }

    public List<TOProdutoVenda> getProdutosVenda() {
        return produtosVenda;
    }

    public void setProdutosVenda(List<TOProdutoVenda> produtosVenda) {
        this.produtosVenda = produtosVenda;
    }

    public List<TOProdutoVenda> getProdutosVendaFiltradas() {
        return produtosVendaFiltradas;
    }

    public void setProdutosVendaFiltradas(List<TOProdutoVenda> produtosVendaFiltradas) {
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

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

}
