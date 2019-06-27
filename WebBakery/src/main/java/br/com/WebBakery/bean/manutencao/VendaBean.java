package br.com.WebBakery.bean.manutencao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.primefaces.event.CellEditEvent;

import br.com.WebBakery.dao.ClienteDao;
import br.com.WebBakery.dao.EstoqueProdutoDao;
import br.com.WebBakery.dao.FuncionarioDao;
import br.com.WebBakery.dao.ProdutoVendaDao;
import br.com.WebBakery.dao.VendaDao;
import br.com.WebBakery.enums.FormaPagamento;
import br.com.WebBakery.model.Cliente;
import br.com.WebBakery.model.EstoqueProduto;
import br.com.WebBakery.model.Funcionario;
import br.com.WebBakery.model.ProdutoVenda;
import br.com.WebBakery.model.Usuario;
import br.com.WebBakery.model.Venda;
import br.com.WebBakery.util.FacesUtil;
import br.com.WebBakery.validator.ProdutoVendaValidator;

@Named
@ViewScoped
public class VendaBean implements Serializable {

    private static final long serialVersionUID = 1093115934531225702L;

    private static final String QUANTIDADE_LIMIT_EXCEDDED = "Quantidade inválida!";
    private static final String VENDA_REGISTRED_SUCCESSFULLY = "Venda realizada com sucesso!";

    @PersistenceContext
    transient private EntityManager em;

    @Inject
    transient private FacesContext context;

    private Venda venda;
    private VendaDao vendaDao;
    private FormaPagamento formaPagamento;
    private ProdutoVendaValidator produtoVendaValidator;

    private Cliente clienteSelecionado;
    private List<Cliente> clientes;
    private List<Cliente> clientesFiltrados;
    private ClienteDao clienteDao;

    private List<EstoqueProduto> estoqueProdutosSelecionados;
    private List<EstoqueProduto> estoqueProdutos;
    private List<EstoqueProduto> estoqueProdutosFiltrados;
    private EstoqueProdutoDao estoqueProdutoDao;

    private List<ProdutoVenda> produtosVenda;
    private List<ProdutoVenda> produtosVendaFiltrados;

    private ProdutoVenda produtoVenda;
    private ProdutoVendaDao produtoVendaDao;

    private FuncionarioDao funcionarioDao;

    private Boolean quantidadeEhValida;

    @PostConstruct
    private void init() {
        this.venda = new Venda();
        this.vendaDao = new VendaDao(this.em);

        this.clienteSelecionado = new Cliente();
        this.clientes = new ArrayList<>();
        this.clienteDao = new ClienteDao(this.em);

        this.estoqueProdutosSelecionados = new ArrayList<>();
        this.estoqueProdutos = new ArrayList<>();
        this.estoqueProdutoDao = new EstoqueProdutoDao(this.em);

        this.produtoVenda = new ProdutoVenda();
        this.produtoVendaDao = new ProdutoVendaDao(this.em);
        this.produtosVenda = new ArrayList<>();

        this.funcionarioDao = new FuncionarioDao(this.em);

        initListProdutos();
        initListClientes();
    }

    @Transactional
    public void cadastrar() {
        this.produtoVendaValidator = new ProdutoVendaValidator(this.estoqueProdutosSelecionados);
        if (this.venda.getId() == null) {
            efetuarCadastro();
            atualizarTela();
        }
    }

    private void efetuarCadastro() {
        if (this.produtoVendaValidator.isValid()) {
            setarDataAtual();
            setarFuncionarioVenda();
            setarClienteVenda();
            this.vendaDao.cadastrar(this.venda);
            cadastrarProdutoVenda();
            atualizarProdutosVenda();
            context.addMessage(null, new FacesMessage(VENDA_REGISTRED_SUCCESSFULLY));
        }
    }

    private void atualizarProdutosVenda() {
        for (ProdutoVenda pv : produtosVenda) {
            atualizarEstoque(pv);
        }
    }

    private void atualizarEstoque(ProdutoVenda pv) {
        EstoqueProduto estoqueProduto = this.estoqueProdutoDao
                .buscarEstoqueProduto(this.produtoVenda.getProduto().getId());
        estoqueProduto.setQuantidade(estoqueProduto.getQuantidade() - pv.getQuantidade());
        this.estoqueProdutoDao.atualizar(estoqueProduto);
    }

    private void atualizarTela() {
        this.venda = new Venda();
        this.produtoVenda = new ProdutoVenda();
        this.produtosVenda.clear();
        this.estoqueProdutosSelecionados.clear();
        this.produtoVendaValidator.showMessages();
        this.produtoVendaValidator.clearMessages();
        initListProdutos();
    }

    public void onCellEdit(CellEditEvent event) {
        Integer oldValue = (Integer) event.getOldValue();
        Integer newValue = (Integer) event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            for (ProdutoVenda pv : produtosVenda) {
                this.quantidadeEhValida = QuantidadeProdutoVendaEhValida(pv,
                                                                         estoqueProdutos,
                                                                         newValue);

                if (quantidadeEhValida) {
                    pv.setQuantidade((Integer) newValue);
                } else {
                    context.addMessage(null,
                                       new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                        QUANTIDADE_LIMIT_EXCEDDED,
                                                        QUANTIDADE_LIMIT_EXCEDDED));
                }
            }
        }
    }

    private Boolean QuantidadeProdutoVendaEhValida(ProdutoVenda pv,
                                                   List<EstoqueProduto> estoqueProdutos,
                                                   Integer newValue) {
        for (EstoqueProduto ep : estoqueProdutos) {
            if (ep.getProduto().getId().equals(pv.getProduto().getId())) {
                return newValue <= ep.getQuantidade();
            }
        }
        throw new NoResultException("Produto não encontrado.");
    }

    public void adicionarCarrinho() {
        for (EstoqueProduto ep : estoqueProdutosSelecionados) {
            if (produtosVenda.isEmpty()) {
                addProdutoVenda(ep);
            } else {
                verificaExisteProduto(ep);
            }
        }
    }

    private void verificaExisteProduto(EstoqueProduto ep) {
        for (ProdutoVenda pv : produtosVenda) {
            if (!ep.getProduto().getId().equals(pv.getProduto().getId())) {
                addProdutoVenda(ep);
            }
        }
    }

    private void addProdutoVenda(EstoqueProduto ep) {
        ProdutoVenda produtoVenda = new ProdutoVenda();
        produtoVenda.setProduto(ep.getProduto());
        this.produtosVenda.add(produtoVenda);
    }

    public void removerCarrinho(ProdutoVenda pv) {
        this.produtosVenda.remove(pv);
    }

    private void setarDataAtual() {
        this.venda.setData(new Date());
    }

    private void setarFuncionarioVenda() {
        Usuario u = (Usuario) FacesUtil.getHTTPSession().getAttribute("usuarioLogado");
        Funcionario f = funcionarioDao.buscarPorIdUsuario(u.getId());
        this.venda.setFuncionario(f);
    }

    private void setarClienteVenda() {
        this.venda.setCliente(this.clienteSelecionado);
    }

    private void cadastrarProdutoVenda() {
        for (EstoqueProduto ep : estoqueProdutosSelecionados) {
            this.produtoVenda.setProduto(ep.getProduto());
            this.produtoVenda.setVenda(this.venda);
            for (ProdutoVenda pv : produtosVenda) {
                this.produtoVenda.setQuantidade(pv.getQuantidade());
            }
            this.produtoVendaDao.cadastrar(this.produtoVenda);
        }
    }

    public void setarCliente() {
        this.venda.setCliente(clienteSelecionado);
    }

    private void initListClientes() {
        this.clientes = this.clienteDao.listarTodos(true);
    }

    private void initListProdutos() {
        this.estoqueProdutos = this.estoqueProdutoDao.listarTodos();
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }

    public void setClienteSelecionado(Cliente clienteSelecionado) {
        this.clienteSelecionado = clienteSelecionado;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Cliente> getClientesFiltrados() {
        return clientesFiltrados;
    }

    public void setClientesFiltrados(List<Cliente> clientesFiltrados) {
        this.clientesFiltrados = clientesFiltrados;
    }

    public ProdutoVenda getProdutoVenda() {
        return produtoVenda;
    }

    public void setProdutoVenda(ProdutoVenda produtoVenda) {
        this.produtoVenda = produtoVenda;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public FormaPagamento[] getFormasPagamento() {
        return FormaPagamento.values();
    }

    public List<ProdutoVenda> getProdutosVenda() {
        return produtosVenda;
    }

    public void setProdutosVenda(List<ProdutoVenda> produtosVenda) {
        this.produtosVenda = produtosVenda;
    }

    public List<ProdutoVenda> getProdutosVendaFiltrados() {
        return produtosVendaFiltrados;
    }

    public void setProdutosVendaFiltrados(List<ProdutoVenda> produtosVendaFiltrados) {
        this.produtosVendaFiltrados = produtosVendaFiltrados;
    }

    public List<EstoqueProduto> getEstoqueProdutosSelecionados() {
        return estoqueProdutosSelecionados;
    }

    public void setEstoqueProdutosSelecionados(List<EstoqueProduto> estoqueProdutosSelecionados) {
        this.estoqueProdutosSelecionados = estoqueProdutosSelecionados;
    }

    public List<EstoqueProduto> getEstoqueProdutos() {
        return estoqueProdutos;
    }

    public void setEstoqueProdutos(List<EstoqueProduto> estoqueProdutos) {
        this.estoqueProdutos = estoqueProdutos;
    }

    public List<EstoqueProduto> getEstoqueProdutosFiltrados() {
        return estoqueProdutosFiltrados;
    }

    public void setEstoqueProdutosFiltrados(List<EstoqueProduto> estoqueProdutosFiltrados) {
        this.estoqueProdutosFiltrados = estoqueProdutosFiltrados;
    }

    public Boolean getQuantidadeEhValida() {
        return quantidadeEhValida;
    }

    public void setQuantidadeEhValida(Boolean quantidadeEhValida) {
        this.quantidadeEhValida = quantidadeEhValida;
    }

}
