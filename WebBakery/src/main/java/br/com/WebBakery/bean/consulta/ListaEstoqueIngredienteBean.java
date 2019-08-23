package br.com.WebBakery.bean.consulta;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.WebBakery.abstractClass.AbstractBaseListMBean;
import br.com.WebBakery.dao.EstoqueIngredienteDao;
import br.com.WebBakery.interfaces.IBaseListMBean;
import br.com.WebBakery.to.TOEstoqueIngrediente;

@Named(ListaEstoqueIngredienteBean.BEAN_NAME)
@ViewScoped
public class ListaEstoqueIngredienteBean extends AbstractBaseListMBean
        implements IBaseListMBean<TOEstoqueIngrediente> {

    public static final String BEAN_NAME = "listaEstoqueIngredienteBean";

    private static final long serialVersionUID = 5495188526333831332L;

    @Inject
    private EstoqueIngredienteDao estoqueIngredienteDao;
    private List<TOEstoqueIngrediente> toEstoqueIngredientes;
    private List<TOEstoqueIngrediente> toEstoqueIngredientesFiltrados;

    @PostConstruct
    private void init() {
        this.toEstoqueIngredientes = new ArrayList<>();
        initListEstoqueIngredientes();
    }

    @Override
    public void inativar(TOEstoqueIngrediente to) {
    }

    private void initListEstoqueIngredientes() {
        try {
            this.toEstoqueIngredientes = this.estoqueIngredienteDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TOEstoqueIngrediente> getToEstoqueIngredientes() {
        return toEstoqueIngredientes;
    }

    public void setToEstoqueIngredientes(List<TOEstoqueIngrediente> toEstoqueIngredientes) {
        this.toEstoqueIngredientes = toEstoqueIngredientes;
    }

    public List<TOEstoqueIngrediente> getToEstoqueIngredientesFiltrados() {
        return toEstoqueIngredientesFiltrados;
    }

    public void setToEstoqueIngredientesFiltrados(List<TOEstoqueIngrediente> toEstoqueIngredientesFiltrados) {
        this.toEstoqueIngredientesFiltrados = toEstoqueIngredientesFiltrados;
    }

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

}
