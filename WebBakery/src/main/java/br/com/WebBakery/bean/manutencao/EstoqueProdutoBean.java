package br.com.WebBakery.bean.manutencao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.dao.EstoqueProdutoDao;
import br.com.WebBakery.to.TOEstoqueProduto;
import br.com.WebBakery.validator.EstoqueProdutoValidator;

@Named(EstoqueProdutoBean.BEAN_NAME)
@ViewScoped
public class EstoqueProdutoBean extends AbstractBaseRegisterMBean<TOEstoqueProduto> {

    public static final String BEAN_NAME = "estoqueProdutoBean";

    private static final long serialVersionUID = 579121007228763037L;

    private TOEstoqueProduto toEstoqueProdutoDoBanco;

    @Inject
    private EstoqueProdutoDao estoqueProdutoDao;

    private List<TOEstoqueProduto> toEstoqueProdutos;
    private List<TOEstoqueProduto> toEstoqueProdutosFiltrados;

    @PostConstruct
    private void init() {
        verificaObjetoSessao();

        if (getTo() == null) {
            resetTo();
        }

        initListaEstoqueProdutos();

    }

    @Transactional
    public void cadastrar() {
        try {
            this.toEstoqueProdutoDoBanco = estoqueProdutoDao.existe(getTo().getToProduto().getId());
            if (this.toEstoqueProdutoDoBanco == null) {
                getTo().setAtivo(true);
                this.estoqueProdutoDao.salvar(getTo());
                showMessageSuccess();
            }
            atualizarTela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListaEstoqueProdutos() {
        try {
            this.toEstoqueProdutos = this.estoqueProdutoDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected AbstractBaseDao<TOEstoqueProduto> getDao() {
        return estoqueProdutoDao;
    }

    @Override
    public AbstractValidator getValidator() {
        // Só é feito assim pois o validator está vazio.
        // Quando houver algo a ser validade, trocar pelo field.
        return new EstoqueProdutoValidator(getTo());
    }

    @Override
    protected TOEstoqueProduto getNewInstaceTO() {
        return new TOEstoqueProduto();
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

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

}
