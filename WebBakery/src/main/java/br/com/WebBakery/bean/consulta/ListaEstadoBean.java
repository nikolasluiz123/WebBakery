package br.com.WebBakery.bean.consulta;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import br.com.WebBakery.dao.EstadoDao;
import br.com.WebBakery.model.Estado;
import br.com.WebBakery.util.Faces_Util;

@Named
@ViewScoped
public class ListaEstadoBean implements Serializable {

    private static final long serialVersionUID = 6531742553423518933L;

    private static final String ESTADO_INATIVATED_SUCCESSFULLY = "Estado inativado com sucesso!";

    @PersistenceContext
    private EntityManager em;
    @Inject
    transient private FacesContext context;

    private EstadoDao estadoDao;
    private List<Estado> estados;
    private List<Estado> estadosFiltrados;

    @PostConstruct
    private void init() {
        this.estadoDao = new EstadoDao(this.em);
        this.estados = new ArrayList<>();
        initListEstados();
    }

    @Transactional
    public void carregar(Integer estadoID) throws IOException {
        HttpSession session = Faces_Util.getHTTPSession();
        session.setAttribute("EstadoID", estadoID);
        context.getExternalContext().redirect("cadastroEstado.xhtml");
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
