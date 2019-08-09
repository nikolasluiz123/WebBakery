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
import br.com.WebBakery.bean.manutencao.ProdutoBean;
import br.com.WebBakery.dao.FotoProdutoDao;
import br.com.WebBakery.dao.ProdutoDao;
import br.com.WebBakery.interfaces.IBaseListMBean;
import br.com.WebBakery.to.TOFotoProduto;
import br.com.WebBakery.to.TOProduto;
import br.com.WebBakery.to.TOProdutoComFoto;
import br.com.WebBakery.to.TOReceita;
import br.com.WebBakery.util.Faces_Util;
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
    private List<TOProduto> produtos;
    private TOProduto produtoSelecionado;

    @PostConstruct
    private void init() {
        this.produtos = new ArrayList<>();
        this.produtoSelecionado = new TOProduto();
        this.produtoSelecionado.setToReceita(new TOReceita());
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

            String pathCompleto = File_Util.criarFotoPastaTemporaria(produtoComFotos.getToFotos().get(0));
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
            this.produtoSelecionado = this.produtoDao.buscarPorId(produtoSelecionado.getId());
            this.produtoSelecionado
                    .setToFotos(this.fotoProdutoDao.listarFotosProduto(produtoSelecionado.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Override
    public void inativar(TOProduto produto) {
        try {
            produto.setAtivo(false);
            this.produtoDao.atualizar(produto);
            initProdutos();
            getContext().addMessage(null, new FacesMessage(PRODUTO_INATIVATED_SUCCESSFULLY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void carregar(Integer produtoID) throws Exception {
        String keyAtribute = "ProdutoID";
        String pageRedirect = "cadastroProduto.xhtml";
        setObjetoSessao(produtoID, keyAtribute, pageRedirect);
        ProdutoBean registerBean = getRegisterBean();
        registerBean.setToProduto(registerBean.getObjetoSessao(keyAtribute, produtoDao));
    }

    public String getPrecoFormatado(Double d) {
        return String_Util.formatarDoubleParaValorMonetario(d);
    }

    private void initProdutos() {
        try {
            this.produtos = this.produtoDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private ProdutoBean getRegisterBean() {
        return ((ProdutoBean) Faces_Util.getBean(ProdutoBean.BEAN_NAME));
    }

    public List<TOProduto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<TOProduto> produtos) {
        this.produtos = produtos;
    }

    public TOProduto getProdutoSelecionado() {
        return produtoSelecionado;
    }

    public void setProdutoSelecionado(TOProduto produtoSelecionado) {
        this.produtoSelecionado = produtoSelecionado;
    }

}
