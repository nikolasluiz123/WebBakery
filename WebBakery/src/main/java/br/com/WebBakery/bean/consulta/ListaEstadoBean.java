package br.com.WebBakery.bean.consulta;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.WebBakery.abstractClass.AbstractBaseListMBean;
import br.com.WebBakery.dao.EstadoDao;
import br.com.WebBakery.interfaces.IBaseListMBean;
import br.com.WebBakery.to.TOEstado;

@Named(ListaEstadoBean.BEAN_NAME)
@ViewScoped
public class ListaEstadoBean extends AbstractBaseListMBean implements IBaseListMBean<TOEstado> {

    public static final String BEAN_NAME = "listaEstadoBean";

    private static final long serialVersionUID = 6531742553423518933L;

    @Inject
    private EstadoDao estadoDao;
    private List<TOEstado> toEstados;
    private List<TOEstado> toEstadosFiltrados;

    @PostConstruct
    private void init() {
        this.toEstados = new ArrayList<>();
        initListEstados();
    }

    @Override
    public void inativar(TOEstado toEstado) {
        try {
            toEstado.setAtivo(false);
            this.estadoDao.salvar(toEstado);
            initListEstados();
            getContext().addMessage(null, new FacesMessage(RECORD_INATIVATED_SUCCESSFULLY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListEstados() {
        try {
            this.toEstados = this.estadoDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TOEstado> getToEstados() {
        return toEstados;
    }

    public void setToEstados(List<TOEstado> estados) {
        this.toEstados = estados;
    }

    public List<TOEstado> getToEstadosFiltrados() {
        return toEstadosFiltrados;
    }

    public void setToEstadosFiltrados(List<TOEstado> toEstadosFiltrados) {
        this.toEstadosFiltrados = toEstadosFiltrados;
    }

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

}
