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
import br.com.WebBakery.dao.FotoIngredienteDao;
import br.com.WebBakery.dao.IngredienteDao;
import br.com.WebBakery.interfaces.IBaseListMBean;
import br.com.WebBakery.to.TOFotoIngrediente;
import br.com.WebBakery.to.TOFotoProduto;
import br.com.WebBakery.to.TOIngrediente;
import br.com.WebBakery.to.TOIngredienteComFotos;
import br.com.WebBakery.util.File_Util;
import br.com.WebBakery.util.String_Util;

@Named(ListaIngredienteBean.BEAN_NAME)
@ViewScoped
public class ListaIngredienteBean extends AbstractBaseListMBean
        implements IBaseListMBean<TOIngrediente> {

    static final String BEAN_NAME = "listaIngredienteBean";

    private static final long serialVersionUID = -5854537667186626713L;

    private static final String PRODUTO_INATIVATED_SUCCESSFULLY = "Produto inativado com sucesso!";

    @Inject
    private IngredienteDao ingredienteDao;
    @Inject
    private FotoIngredienteDao fotoIngredienteDao;
    private List<TOIngrediente> toIngredientes;
    private TOIngrediente toIngredienteSelecionado;

    @PostConstruct
    private void init() {
        this.toIngredientes = new ArrayList<>();
        this.toIngredienteSelecionado = new TOIngrediente();
        initIngredientes();
    }

    public String getPathPrimeiraFoto(Integer idIngrediente) {
        String path = null;
        try {
            List<TOIngredienteComFotos> ingredientesComFotos = new ArrayList<>();
            ingredientesComFotos = this.ingredienteDao.listarTodosIngredienteComFotos();
            TOIngredienteComFotos ingredienteComFotos = new TOIngredienteComFotos();

            for (TOIngredienteComFotos pfs : ingredientesComFotos) {
                if (pfs.getToIngrediente().getId().equals(idIngrediente)) {
                    ingredienteComFotos = pfs;
                }
            }

            String pathCompleto = File_Util
                    .criarFotoPastaTemporaria(ingredienteComFotos.getToFotos().get(0));
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
    public void getDadosIngrediente() {
        try {
            List<TOFotoIngrediente> fotosIngrediente = new ArrayList<>();
            this.toIngredienteSelecionado = this.ingredienteDao
                    .buscarPorId(toIngredienteSelecionado.getId());
            fotosIngrediente = this.fotoIngredienteDao
                    .listarFotosIngrediente(toIngredienteSelecionado.getId());
            this.toIngredienteSelecionado.setToFotos(fotosIngrediente);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Override
    public void inativar(TOIngrediente to) {
        try {
            to.setAtivo(false);
            this.ingredienteDao.salvar(to);
            initIngredientes();
            getContext().addMessage(null, new FacesMessage(PRODUTO_INATIVATED_SUCCESSFULLY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPrecoFormatado(Double d) {
        return String_Util.formatarDoubleParaValorMonetario(d);
    }

    @Transactional
    private void initIngredientes() {
        try {
            this.toIngredientes = this.ingredienteDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TOIngrediente> getToIngredientes() {
        return toIngredientes;
    }

    public void setToIngredientes(List<TOIngrediente> toIngredientes) {
        this.toIngredientes = toIngredientes;
    }

    public TOIngrediente getToIngredienteSelecionado() {
        return toIngredienteSelecionado;
    }

    public void setToIngredienteSelecionado(TOIngrediente toIngredienteSelecionado) {
        this.toIngredienteSelecionado = toIngredienteSelecionado;
    }

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

}
