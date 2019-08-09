package br.com.WebBakery.bean.manutencao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.primefaces.event.CellEditEvent;

import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.ClienteDao;
import br.com.WebBakery.dao.EstoqueProdutoDao;
import br.com.WebBakery.dao.FuncionarioDao;
import br.com.WebBakery.dao.ProdutoVendaDao;
import br.com.WebBakery.dao.VendaDao;
import br.com.WebBakery.enums.FormaPagamento;
import br.com.WebBakery.to.TOCliente;
import br.com.WebBakery.to.TOEstoqueProduto;
import br.com.WebBakery.to.TOFuncionario;
import br.com.WebBakery.to.TOProdutoVenda;
import br.com.WebBakery.to.TOUsuario;
import br.com.WebBakery.to.TOVenda;
import br.com.WebBakery.util.Faces_Util;
import br.com.WebBakery.validator.ProdutoVendaValidator;

@Named
@ViewScoped
public class VendaBean extends AbstractBaseRegisterMBean<TOVenda> {

    private static final long serialVersionUID = 1093115934531225702L;

    private static final String QUANTIDADE_LIMIT_EXCEDDED = "Quantidade inv�lida!";
    private static final String VENDA_REGISTRED_SUCCESSFULLY = "TOVenda realizada com sucesso!";

    private TOVenda toVenda;
    @Inject
    private VendaDao vendaDao;
    private FormaPagamento formaPagamento;
    private ProdutoVendaValidator produtoVendaValidator;

    private TOCliente toClienteSelecionado;
    private List<TOCliente> toClientes;
    private List<TOCliente> toClientesFiltrados;
    @Inject
    private ClienteDao clienteDao;

    private List<TOEstoqueProduto> toEstoqueProdutosSelecionados;
    private List<TOEstoqueProduto> toEstoqueProdutos;
    private List<TOEstoqueProduto> toEstoqueProdutosFiltrados;
    @Inject
    private EstoqueProdutoDao estoqueProdutoDao;

    private List<TOProdutoVenda> toProdutosVenda;
    private List<TOProdutoVenda> toProdutosVendaFiltrados;

    private TOProdutoVenda toProdutoVenda;
    @Inject
    private ProdutoVendaDao produtoVendaDao;
    @Inject
    private FuncionarioDao funcionarioDao;

    private Boolean quantidadeEhValida;

    @PostConstruct
    private void init() {
        this.toVenda = new TOVenda();

        this.toClienteSelecionado = new TOCliente();
        this.toClientes = new ArrayList<>();

        this.toEstoqueProdutosSelecionados = new ArrayList<>();
        this.toEstoqueProdutos = new ArrayList<>();

        this.toProdutoVenda = new TOProdutoVenda();
        this.toProdutosVenda = new ArrayList<>();

        initListProdutos();
        initListClientes();
    }

    @Transactional
    public void cadastrar() {
        this.produtoVendaValidator = new ProdutoVendaValidator(this.toEstoqueProdutosSelecionados);
        if (this.toVenda.getId() == null) {
            efetuarCadastro();
            atualizarTela();
        }
    }

    private void efetuarCadastro() {
        if (this.produtoVendaValidator.isValid()) {
            setarDataAtual();
            setarFuncionarioVenda();
            setarClienteVenda();
            try {
                this.vendaDao.cadastrar(this.toVenda);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cadastrarProdutoVenda();
            atualizarProdutosVenda();
            getContext().addMessage(null, new FacesMessage(VENDA_REGISTRED_SUCCESSFULLY));
        }
    }

    private void atualizarProdutosVenda() {
        for (TOProdutoVenda to : toProdutosVenda) {
            atualizarEstoque(to);
        }
    }

    private void atualizarEstoque(TOProdutoVenda to) {
        TOEstoqueProduto estoqueProduto;
        try {
            estoqueProduto = this.estoqueProdutoDao
                    .buscarPorIdProduto(this.toProdutoVenda.getToProduto().getId());
            estoqueProduto.setQuantidade(estoqueProduto.getQuantidade() - to.getQuantidade());
            this.estoqueProdutoDao.atualizar(estoqueProduto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void atualizarTela() {
        this.toVenda = new TOVenda();
        this.toProdutoVenda = new TOProdutoVenda();
        this.toProdutosVenda.clear();
        this.toEstoqueProdutosSelecionados.clear();
        this.produtoVendaValidator.showMessages();
        this.produtoVendaValidator.clearMessages();
        initListProdutos();
    }

    public void onCellEdit(CellEditEvent event) {
        Integer oldValue = (Integer) event.getOldValue();
        Integer newValue = (Integer) event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            for (TOProdutoVenda to : toProdutosVenda) {
                this.quantidadeEhValida = QuantidadeProdutoVendaEhValida(to,
                                                                         toEstoqueProdutos,
                                                                         newValue);

                if (quantidadeEhValida) {
                    to.setQuantidade((Integer) newValue);
                } else {
                    getContext().addMessage(null,
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                             QUANTIDADE_LIMIT_EXCEDDED,
                                                             QUANTIDADE_LIMIT_EXCEDDED));
                }
            }
        }
    }

    private Boolean QuantidadeProdutoVendaEhValida(TOProdutoVenda to,
                                                   List<TOEstoqueProduto> toEstoqueProdutos,
                                                   Integer newValue) {
        for (TOEstoqueProduto ep : toEstoqueProdutos) {
            if (ep.getToProduto().getId().equals(to.getToProduto().getId())) {
                return newValue <= ep.getQuantidade();
            }
        }
        throw new NoResultException("TOProduto n�o encontrado.");
    }

    public void adicionarCarrinho() {
        for (TOEstoqueProduto to : toEstoqueProdutosSelecionados) {
            if (toProdutosVenda.isEmpty()) {
                addProdutoVenda(to);
            } else {
                verificaExisteProduto(to);
            }
        }
    }

    private void verificaExisteProduto(TOEstoqueProduto to) {
        for (TOProdutoVenda toPV : toProdutosVenda) {
            if (!to.getToProduto().getId().equals(toPV.getToProduto().getId())) {
                addProdutoVenda(to);
            }
        }
    }

    private void addProdutoVenda(TOEstoqueProduto to) {
        TOProdutoVenda toPV = new TOProdutoVenda();
        toPV.setToProduto(to.getToProduto());
        this.toProdutosVenda.add(toPV);
    }

    public void removerCarrinho(TOProdutoVenda to) {
        this.toProdutosVenda.remove(to);
    }

    private void setarDataAtual() {
        this.toVenda.setData(new Date());
    }

    private void setarFuncionarioVenda() {
        TOUsuario u = (TOUsuario) Faces_Util.getHTTPSession().getAttribute("usuarioLogado");
        TOFuncionario f = null;
        try {
            f = funcionarioDao.buscarPorIdUsuario(u.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.toVenda.setToFuncionario(f);
    }

    private void setarClienteVenda() {
        this.toVenda.setToCliente(this.toClienteSelecionado);
    }

    private void cadastrarProdutoVenda() {
        for (TOEstoqueProduto toEP : toEstoqueProdutosSelecionados) {
            this.toProdutoVenda.setToProduto(toEP.getToProduto());
            this.toProdutoVenda.setToVenda(this.toVenda);
            for (TOProdutoVenda toPV : toProdutosVenda) {
                this.toProdutoVenda.setQuantidade(toPV.getQuantidade());
            }
            try {
                this.produtoVendaDao.cadastrar(this.toProdutoVenda);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setarCliente() {
        this.toVenda.setToCliente(toClienteSelecionado);
    }

    private void initListClientes() {
        try {
            this.toClientes = this.clienteDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListProdutos() {
        try {
            this.toEstoqueProdutos = this.estoqueProdutoDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TOVenda getToVenda() {
        return toVenda;
    }

    public void setToVenda(TOVenda toVenda) {
        this.toVenda = toVenda;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public TOCliente getToClienteSelecionado() {
        return toClienteSelecionado;
    }

    public void setToClienteSelecionado(TOCliente toClienteSelecionado) {
        this.toClienteSelecionado = toClienteSelecionado;
    }

    public List<TOCliente> getToClientes() {
        return toClientes;
    }

    public void setToClientes(List<TOCliente> toClientes) {
        this.toClientes = toClientes;
    }

    public List<TOCliente> getToClientesFiltrados() {
        return toClientesFiltrados;
    }

    public void setToClientesFiltrados(List<TOCliente> toClientesFiltrados) {
        this.toClientesFiltrados = toClientesFiltrados;
    }

    public List<TOEstoqueProduto> getToEstoqueProdutosSelecionados() {
        return toEstoqueProdutosSelecionados;
    }

    public void setToEstoqueProdutosSelecionados(List<TOEstoqueProduto> toEstoqueProdutosSelecionados) {
        this.toEstoqueProdutosSelecionados = toEstoqueProdutosSelecionados;
    }

    public List<TOEstoqueProduto> getToEstoqueProdutos() {
        return toEstoqueProdutos;
    }

    public void setToEstoqueProdutos(List<TOEstoqueProduto> toEstoqueProdutos) {
        this.toEstoqueProdutos = toEstoqueProdutos;
    }

    public List<TOEstoqueProduto> getToEstoqueProdutosFiltrados() {
        return toEstoqueProdutosFiltrados;
    }

    public void setToEstoqueProdutosFiltrados(List<TOEstoqueProduto> toEstoqueProdutosFiltrados) {
        this.toEstoqueProdutosFiltrados = toEstoqueProdutosFiltrados;
    }

    public List<TOProdutoVenda> getToProdutosVenda() {
        return toProdutosVenda;
    }

    public void setToProdutosVenda(List<TOProdutoVenda> toProdutosVenda) {
        this.toProdutosVenda = toProdutosVenda;
    }

    public List<TOProdutoVenda> getToProdutosVendaFiltrados() {
        return toProdutosVendaFiltrados;
    }

    public void setToProdutosVendaFiltrados(List<TOProdutoVenda> toProdutosVendaFiltrados) {
        this.toProdutosVendaFiltrados = toProdutosVendaFiltrados;
    }

    public TOProdutoVenda getToProdutoVenda() {
        return toProdutoVenda;
    }

    public void setToProdutoVenda(TOProdutoVenda toProdutoVenda) {
        this.toProdutoVenda = toProdutoVenda;
    }

    public Boolean getQuantidadeEhValida() {
        return quantidadeEhValida;
    }

    public void setQuantidadeEhValida(Boolean quantidadeEhValida) {
        this.quantidadeEhValida = quantidadeEhValida;
    }

}
