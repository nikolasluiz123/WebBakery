package br.com.WebBakery.bean.consulta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseListMBean;
import br.com.WebBakery.dao.EstadoDao;
import br.com.WebBakery.model.Estado;

@Named
@ViewScoped
public class ListaEstadoBean extends AbstractBaseListMBean<Estado> {

    private static final long serialVersionUID = 6531742553423518933L;

    private static final String ESTADO_INATIVATED_SUCCESSFULLY = "Estado inativado com sucesso!";

    private EstadoDao estadoDao;
    private List<Estado> estados;
    private List<Estado> estadosFiltrados;

    public void init() {
        this.estadoDao = new EstadoDao(this.em);
        this.estados = new ArrayList<>();
        initListEstados();
    }

    @Transactional
    public void carregar(Integer estadoID) throws IOException {
        setObjetoSessao(estadoID, "EstadoID", "cadastroEstado.xhtml");
    }

    @Transactional
    public void inativar(Estado estado) {
        estado.setAtivo(false);
        this.estadoDao.atualizar(estado);
        initListEstados();
        context.addMessage(null, new FacesMessage(ESTADO_INATIVATED_SUCCESSFULLY));
    }

    private void initListEstados() {
        this.estados = this.estadoDao.listarTodos(true);
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public List<Estado> getEstadosFiltrados() {
        return estadosFiltrados;
    }

    public void setEstadosFiltrados(List<Estado> estadosFiltrados) {
        this.estadosFiltrados = estadosFiltrados;
    }

}
