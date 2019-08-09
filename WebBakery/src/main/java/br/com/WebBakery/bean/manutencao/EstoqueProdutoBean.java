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
import br.com.WebBakery.to.TOEstoqueProduto;

@Named(EstoqueProdutoBean.BEAN_NAME)
@ViewScoped
public class EstoqueProdutoBean extends AbstractBaseRegisterMBean<TOEstoqueProduto> {

    public static final String BEAN_NAME = "estoqueProdutoBean";

    private static final String UPDATED_SUCCESSFULLY = "Estoque de produto atualizado com sucesso!";
    private static final String REGISTERED_SUCCESSFULLY = "Produto cadastrado no estoque com sucesso!";

    private static final long serialVersionUID = 579121007228763037L;

    private TOEstoqueProduto toEstoqueProduto;
    private TOEstoqueProduto toEstoqueProdutoDoBanco;

    @Inject
    private EstoqueProdutoDao estoqueProdutoDao;

    private List<TOEstoqueProduto> toEstoqueProdutos;
    private List<TOEstoqueProduto> toEstoqueProdutosFiltrados;

    @PostConstruct
    private void init() {
        this.toEstoqueProduto = new TOEstoqueProduto();
        initListaEstoqueProdutos();
    }

    @Transactional
    public void cadastrar() {
        try {
            this.toEstoqueProdutoDoBanco = estoqueProdutoDao
                    .existe(this.toEstoqueProduto.getToProduto().getId());
            if (this.toEstoqueProdutoDoBanco == null) {
                efetuarCadastro();
            } else {
                efetuarAtualizacao();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void efetuarCadastro() throws Exception {
        this.estoqueProdutoDao.cadastrar(this.toEstoqueProduto);
        getContext().addMessage(null, new FacesMessage(REGISTERED_SUCCESSFULLY));
    }

    private void efetuarAtualizacao() throws Exception {
        this.estoqueProdutoDao.atualizar(this.toEstoqueProdutoDoBanco);
        getContext().addMessage(null, new FacesMessage(UPDATED_SUCCESSFULLY));
    }

    private void initListaEstoqueProdutos() {
        try {
            this.toEstoqueProdutos = this.estoqueProdutoDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TOEstoqueProduto getToEstoqueProduto() {
        return toEstoqueProduto;
    }

    public void setToEstoqueProduto(TOEstoqueProduto toEstoqueProduto) {
        this.toEstoqueProduto = toEstoqueProduto;
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

}
