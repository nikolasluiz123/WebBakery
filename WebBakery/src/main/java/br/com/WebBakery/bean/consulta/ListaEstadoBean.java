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
import br.com.WebBakery.bean.manutencao.EstadoBean;
import br.com.WebBakery.dao.EstadoDao;
import br.com.WebBakery.interfaces.IBaseListMBean;
import br.com.WebBakery.to.TOEstado;
import br.com.WebBakery.util.Faces_Util;

@Named(ListaEstadoBean.BEAN_NAME)
@ViewScoped
public class ListaEstadoBean extends AbstractBaseListMBean implements IBaseListMBean<TOEstado> {

    public static final String BEAN_NAME = "listaEstadoBean";

    private static final long serialVersionUID = 6531742553423518933L;

    private static final String ESTADO_INATIVATED_SUCCESSFULLY = "Estado inativado com sucesso!";

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
    public void carregar(Integer estadoID) throws Exception {
        String keyAtribute = "EstadoID";
        String pageRedirect = "cadastroEstado.xhtml";
        setObjetoSessao(estadoID, keyAtribute, pageRedirect);
        getRegisterBean().getObjetoSessao(keyAtribute, estadoDao);
    }

    @Transactional
    @Override
    public void inativar(TOEstado toEstado) {
        toEstado.setAtivo(false);

        try {
            this.estadoDao.atualizar(toEstado);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initListEstados();
        getContext().addMessage(null, new FacesMessage(ESTADO_INATIVATED_SUCCESSFULLY));
    }

    private void initListEstados() {
        try {
            this.toEstados = this.estadoDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private EstadoBean getRegisterBean() {
        return ((EstadoBean) Faces_Util.getBean(EstadoBean.BEAN_NAME));
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

}
