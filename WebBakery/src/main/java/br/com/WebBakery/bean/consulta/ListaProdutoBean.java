package br.com.WebBakery.bean.consulta;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseListMBean;
import br.com.WebBakery.dao.FotoProdutoDao;
import br.com.WebBakery.dao.ProdutoDao;
import br.com.WebBakery.interfaces.IBaseListMBean;
import br.com.WebBakery.to.TOFotoProduto;
import br.com.WebBakery.to.TOProduto;
import br.com.WebBakery.to.TOProdutoComFoto;
import br.com.WebBakery.to.TOReceita;
import br.com.WebBakery.util.File_Util;
import br.com.WebBakery.util.String_Util;

@Named(ListaProdutoBean.BEAN_NAME)
@ViewScoped
public class ListaProdutoBean extends AbstractBaseListMBean implements IBaseListMBean<TOProduto> {

    public static final String BEAN_NAME = "listaProdutoBean";

    private static final long serialVersionUID = -5854537667186626713L;

    private static final String PRODUTO_INATIVATED_SUCCESSFULLY = "TOProduto inativado com sucesso!";

    @Inject
    private ProdutoDao produtoDao;
    @Inject
    private FotoProdutoDao fotoProdutoDao;
    private List<TOProduto> toProdutos;
    private TOProduto toProdutoSelecionado;

    @PostConstruct
    private void init() {
        this.toProdutos = new ArrayList<>();
        this.toProdutoSelecionado = new TOProduto();
        this.toProdutoSelecionado.setToReceita(new TOReceita());
        initProdutos();
    }

    public String getPathPrimeiraFoto(Integer idProduto) {
        String path = null;
        try {
            List<TOProdutoComFoto> produtosComFotos = new ArrayList<>();
            produtosComFotos = this.produtoDao.listarTodosProdutoComFotos();
            TOProdutoComFoto produtoComFotos = new TOProdutoComFoto();

            for (TOProdutoComFoto pfs : produtosComFotos) {
                if (pfs.getToProduto().getId().equals(idProduto)) {
                    produtoComFotos = pfs;
                }
            }

            String pathCompleto = File_Util
                    .criarFotoPastaTemporaria(produtoComFotos.getToFotos().get(0));
            String nomeArquivo = File_Util.getNomeArquivo(pathCompleto);
            path = File_Util.getPath(nomeArquivo);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return path;
    }

    public String getPath(TOFotoProduto fp) {
        String path = null;
        try {
            String pathCompleto = File_Util.criarFotoPastaTemporaria(fp);
            String nomeArquivo = File_Util.getNomeArquivo(pathCompleto);
            path = File_Util.getPath(nomeArquivo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return path;
    }

    @Transactional
    public void getDadosProduto() {
        try {
            this.toProdutoSelecionado = this.produtoDao.buscarPorId(toProdutoSelecionado.getId());
            this.toProdutoSelecionado.setToFotos(this.fotoProdutoDao
                    .listarFotosProduto(toProdutoSelecionado.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Override
    public void inativar(TOProduto produto) {
        try {
            produto.setAtivo(false);
            this.produtoDao.salvar(produto);
            initProdutos();
            getContext().addMessage(null, new FacesMessage(PRODUTO_INATIVATED_SUCCESSFULLY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPrecoFormatado(Double d) {
        return String_Util.formatDoubleToValueMonetary(d);
    }

    private void initProdutos() {
        try {
            this.toProdutos = this.produtoDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TOProduto> getToProdutos() {
        return toProdutos;
    }

    public void setToProdutos(List<TOProduto> toProdutos) {
        this.toProdutos = toProdutos;
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
