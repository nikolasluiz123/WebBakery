package br.com.WebBakery.bean.consulta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseListMBean;
import br.com.WebBakery.dao.FotoIngredienteDao;
import br.com.WebBakery.dao.IngredienteDao;
import br.com.WebBakery.interfaces.IBaseListMBean;
import br.com.WebBakery.to.TOFotoIngrediente;
import br.com.WebBakery.to.TOFotoProduto;
import br.com.WebBakery.to.TOIngrediente;
import br.com.WebBakery.to.TOIngredienteComFotos;
import br.com.WebBakery.util.File_Util;
import br.com.WebBakery.util.String_Util;

@Named
@ViewScoped
public class ListaIngredienteBean extends AbstractBaseListMBean
        implements IBaseListMBean<TOIngrediente> {

    private static final long serialVersionUID = -5854537667186626713L;

    private static final String PRODUTO_INATIVATED_SUCCESSFULLY = "TOProduto inativado com sucesso!";

    @Inject
    private IngredienteDao ingredienteDao;
    @Inject
    private FotoIngredienteDao fotoIngredienteDao;
    private List<TOIngrediente> ingredientes;
    private TOIngrediente ingredienteSelecionado;

    @PostConstruct
    private void init() {
        this.ingredientes = new ArrayList<>();
        this.ingredienteSelecionado = new TOIngrediente();
        initIngredientes();
    }

    public String getPathPrimeiraFoto(Integer idIngrediente) {
        List<TOIngredienteComFotos> ingredientesComFotos = new ArrayList<>();

        try {
            ingredientesComFotos = this.ingredienteDao.listarTodosIngredienteComFotos();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        TOIngredienteComFotos ingredienteComFotos = new TOIngredienteComFotos();
        for (TOIngredienteComFotos pfs : ingredientesComFotos) {
            if (pfs.getToIngrediente().getId().equals(idIngrediente)) {
                ingredienteComFotos = pfs;
            }
        }

        String pathCompleto = null;
        try {
            pathCompleto = File_Util
                    .criarFotoPastaTemporaria(ingredienteComFotos.getToFotos().get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String nomeArquivo = File_Util.getNomeArquivo(pathCompleto);
        String path = File_Util.getPath(nomeArquivo);

        return path;
    }

    public String getPath(TOFotoProduto fp) {
        String pathCompleto = null;
        try {
            pathCompleto = File_Util.criarFotoPastaTemporaria(fp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String nomeArquivo = File_Util.getNomeArquivo(pathCompleto);
        String path = File_Util.getPath(nomeArquivo);

        return path;
    }

    public void getDadosProduto() {
        List<TOFotoIngrediente> fotosIngrediente = new ArrayList<>();

        try {
            this.ingredienteSelecionado = this.ingredienteDao
                    .buscarPorId(ingredienteSelecionado.getId());

            fotosIngrediente = this.fotoIngredienteDao
                    .listarFotosIngrediente(ingredienteSelecionado.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.ingredienteSelecionado.setToFotos(fotosIngrediente);
    }

    @Transactional
    @Override
    public void inativar(TOIngrediente to) {
        to.setAtivo(false);

        try {
            this.ingredienteDao.atualizar(to);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initIngredientes();
        getContext().addMessage(null, new FacesMessage(PRODUTO_INATIVATED_SUCCESSFULLY));

    }

    @Override
    public void carregar(Integer produtoID) throws IOException {
        setObjetoSessao(produtoID, "ProdutoID", "cadastroProduto.xhtml");
    }

    public String getPrecoFormatado(Double d) {
        return String_Util.formatarDoubleParaValorMonetario(d);
    }

    private void initIngredientes() {
        try {
            this.ingredientes = this.ingredienteDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TOIngrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<TOIngrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public TOIngrediente getIngredienteSelecionado() {
        return ingredienteSelecionado;
    }

    public void setIngredienteSelecionado(TOIngrediente ingredienteSelecionado) {
        this.ingredienteSelecionado = ingredienteSelecionado;
    }

}
