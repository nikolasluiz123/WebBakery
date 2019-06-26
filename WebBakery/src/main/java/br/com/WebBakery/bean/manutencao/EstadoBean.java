package br.com.WebBakery.bean.manutencao;

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
import javax.transaction.Transactional;

import br.com.WebBakery.dao.EstadoDao;
import br.com.WebBakery.dao.PaisDao;
import br.com.WebBakery.model.Estado;
import br.com.WebBakery.model.Pais;
import br.com.WebBakery.util.FacesUtil;
import br.com.WebBakery.validator.EstadoValidator;

@Named
@ViewScoped
public class EstadoBean implements Serializable {

    private static final String ESTADO_UPDATED_SUCCESSFULLY = "Estado atualizado com sucesso!";
    private static final String ESTADO_REGISTRED_SUCCESSFULLY = "Estado cadastrado com sucesso!";

    private static final long serialVersionUID = -4701039486320007318L;

    @PersistenceContext
    private EntityManager em;
    @Inject
    transient private FacesContext context;

    private EstadoDao estadoDao;
    private Estado estado;

    private PaisDao paisDao;
    private Pais paisSelecionado;
    private List<Pais> paises;
    private List<Pais> paisesFiltrados;

    private EstadoValidator validator;

    @PostConstruct
    private void init() {
        this.estadoDao = new EstadoDao(this.em);
        this.estado = new Estado();

        this.paisDao = new PaisDao(this.em);
        this.paisSelecionado = new Pais();
        this.paises = new ArrayList<>();

        initListPaises();
        verificaEstadoSessao();
    }

    @Transactional
    public void cadastrar() {
        this.validator = new EstadoValidator(this.estado);
        if (this.estado.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        atualizarTela();
    }

    private void efetuarCadastro() {
        if (this.validator.isValid()) {
            this.estado.setAtivo(true);
            this.estadoDao.cadastrar(this.estado);
            context.addMessage(null, new FacesMessage(ESTADO_REGISTRED_SUCCESSFULLY));
        }
    }

    private void efetuarAtualizacao() {
        if (this.validator.isValid()) {
            this.estadoDao.atualizar(this.estado);
            context.addMessage(null, new FacesMessage(ESTADO_UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.estado = new Estado();
        this.validator.showMessages();
        this.validator.clearMessages();
    }

    private void verificaEstadoSessao() {
        Integer estadoId = (Integer) FacesUtil.getHTTPSession().getAttribute("EstadoID");
        if (estadoId != null) {
            this.estado = estadoDao.buscarPorId(Estado.class, estadoId);
            FacesUtil.getHTTPSession().removeAttribute("EstadoID");
        }
    }

    public void setarPais() {
        this.estado.setPais(this.paisSelecionado);
    }

    private void initListPaises() {
        this.paises = this.paisDao.listarTodos(true);
    }

    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<Pais> getPaises() {
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }

    public Pais getPaisSelecionado() {
        return paisSelecionado;
    }

    public void setPaisSelecionado(Pais paisSelecionado) {
        this.paisSelecionado = paisSelecionado;
    }

    public List<Pais> getPaisesFiltrados() {
        return paisesFiltrados;
    }

    public void setPaisesFiltrados(List<Pais> paisesFiltrados) {
        this.paisesFiltrados = paisesFiltrados;
    }

}
