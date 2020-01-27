package br.com.WebBakery.bean.manutencao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.FuncionarioDao;
import br.com.WebBakery.dao.ProdutoDao;
import br.com.WebBakery.dao.TarefaDao;
import br.com.WebBakery.enums.TipoUsuario;
import br.com.WebBakery.to.TOFuncionario;
import br.com.WebBakery.to.TOProduto;
import br.com.WebBakery.to.TOTarefa;
import br.com.WebBakery.util.DateUtil;
import br.com.WebBakery.util.PrimefacesUtil;
import br.com.WebBakery.validator.TarefaValidator;

@Named(TarefaBean.BEAN_NAME)
@ViewScoped
public class TarefaBean extends AbstractBaseRegisterMBean<TOTarefa> {

    public static final String BEAN_NAME = "tarefaBean";

    private static final long serialVersionUID = 2625499918546247472L;

    @Inject
    private TarefaDao tarefaDao;

    @Inject
    private ProdutoDao produtoDao;
    private List<TOProduto> toProdutos;
    private List<TOProduto> toProdutosFiltrados;
    private TOProduto toProdutoSelecionado;

    @Inject
    private FuncionarioDao funcionarioDao;
    private List<TOFuncionario> toPadeiros;
    private List<TOFuncionario> toPadeirosFiltrados;
    private TOFuncionario toPadeiroSelecionado;

    @PostConstruct
    private void init() {
        verificaObjetoSessao();

        if (getTo() == null) {
            resetTo();
        }

        initQuantidadeProdutoTarefa();
        initProdutos();
        initFucnionarios();
    }

    @Transactional
    public void cadastrar() {
        try {
            addValidators();
            if (isValid()) {
                this.getTo().setAtivo(true);
                descontarEstoqueIngrediente(getTo());
                this.tarefaDao.salvar(getTo());
                showMessageSuccess();
            }
            atualizarTela();
            this.toProdutoSelecionado = new TOProduto();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void descontarEstoqueIngrediente(TOTarefa toTarefa) {
        this.tarefaDao.descontarEstoque(toTarefa.getToProduto().getToReceita().getId(),
                                        toTarefa.getQuantidade());
    }

    private void addValidators() {
        TarefaValidator tarefaValidator = new TarefaValidator(this.getTo(), tarefaDao);
        addValidator(tarefaValidator);
    }

    public void calcularFim() {
        try {
            Date tempoPreparo = new Date(getTo().getToProduto().getToReceita().getTempoPreparo()
                    .getTime());
            Date dataFim = DateUtil.sum(getTo().getDataInicio(), tempoPreparo);
            getTo().setDataFim(dataFim);
            PrimefacesUtil.update("formCadastroTarefa:dataFim");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void initProdutos() {
        try {
            this.toProdutos = this.produtoDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initFucnionarios() {
        try {
            this.toPadeiros = this.funcionarioDao.listarTodos(TipoUsuario.PADEIRO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initQuantidadeProdutoTarefa() {
        this.getTo().setQuantidade(1);
    }

    public void setarProduto() {
        this.getTo().setToProduto(this.toProdutoSelecionado);
        PrimefacesUtil.update("formCadastroTarefa:dataInicio");
    }

    public void setarPadeiro() {
        this.getTo().setToPadeiro(this.getToPadeiroSelecionado());
    }

    @Override
    protected AbstractBaseDao<TOTarefa> getDao() {
        return tarefaDao;
    }

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

    @Override
    protected TOTarefa getNewInstaceTO() {
        return new TOTarefa();
    }

    public List<TOProduto> getToProdutos() {
        return toProdutos;
    }

    public void setToProdutos(List<TOProduto> toProdutos) {
        this.toProdutos = toProdutos;
    }

    public List<TOProduto> getToProdutosFiltrados() {
        return toProdutosFiltrados;
    }

    public void setToProdutosFiltrados(List<TOProduto> toProdutosFiltrados) {
        this.toProdutosFiltrados = toProdutosFiltrados;
    }

    public TOProduto getToProdutoSelecionado() {
        return toProdutoSelecionado;
    }

    public void setToProdutoSelecionado(TOProduto toProdutoSelecionado) {
        this.toProdutoSelecionado = toProdutoSelecionado;
    }

    public List<TOFuncionario> getToPadeiros() {
        return toPadeiros;
    }

    public void setToPadeiros(List<TOFuncionario> toPadeiros) {
        this.toPadeiros = toPadeiros;
    }

    public List<TOFuncionario> getToPadeirosFiltrados() {
        return toPadeirosFiltrados;
    }

    public void setToPadeirosFiltrados(List<TOFuncionario> toPadeirosFiltrados) {
        this.toPadeirosFiltrados = toPadeirosFiltrados;
    }

    public TOFuncionario getToPadeiroSelecionado() {
        return toPadeiroSelecionado;
    }

    public void setToPadeiroSelecionado(TOFuncionario toPadeiroSelecionado) {
        this.toPadeiroSelecionado = toPadeiroSelecionado;
    }
}
