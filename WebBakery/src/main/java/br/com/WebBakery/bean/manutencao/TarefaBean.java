package br.com.WebBakery.bean.manutencao;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.ProdutoDao;
import br.com.WebBakery.dao.TarefaDao;
import br.com.WebBakery.to.TOProduto;
import br.com.WebBakery.to.TOTarefa;
import br.com.WebBakery.util.Date_Util;
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

    @PostConstruct
    private void init() {
        verificaObjetoSessao();

        if (getTo() == null) {
            resetTo();
        }

        initQuantidadeProdutoTarefa();
        initProdutos();

    }

    @Transactional
    public void cadastrar() {
        try {
            addValidators();
            if (isValid()) {
                this.getTo().setAtivo(true);
                this.tarefaDao.salvar(this.getTo());
                showMessageSuccess();
            }
            atualizarTela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addValidators() {
        TarefaValidator tarefaValidator = new TarefaValidator(this.getTo());
        addValidator(tarefaValidator);
    }

    private void initProdutos() {
        try {
            this.toProdutos = this.produtoDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initQuantidadeProdutoTarefa() {
        this.getTo().setQuantidade(1);
    }

    public void setarProduto() {
        this.getTo().setToProduto(this.toProdutoSelecionado);
    }

    public String dataFormatada(Date data) {
        return Date_Util.formatar("dd/MM/yyyy", data);
    }

    @Override
    protected AbstractBaseDao<TOTarefa> getDao() {
        return tarefaDao;
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

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

}
