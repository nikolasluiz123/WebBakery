package br.com.WebBakery.bean.manutencao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.EstadoDao;
import br.com.WebBakery.dao.PaisDao;
import br.com.WebBakery.model.Estado;
import br.com.WebBakery.model.Pais;
import br.com.WebBakery.validator.EstadoValidator;

@Named
@ViewScoped
public class EstadoBean extends AbstractBaseRegisterMBean<Estado> {

    private static final String ESTADO_UPDATED_SUCCESSFULLY = "Estado atualizado com sucesso!";
    private static final String ESTADO_REGISTRED_SUCCESSFULLY = "Estado cadastrado com sucesso!";

    private static final long serialVersionUID = -4701039486320007318L;

    @Inject
    private EstadoDao estadoDao;
    private Estado estado;

    @Inject
    private PaisDao paisDao;
    private Pais paisSelecionado;
    private List<Pais> paises;
    private List<Pais> paisesFiltrados;

    private EstadoValidator validator;

    @PostConstruct
    private void init() {
        this.estado = new Estado();

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
            getContext().addMessage(null, new FacesMessage(ESTADO_REGISTRED_SUCCESSFULLY));
        }
    }

    private void efetuarAtualizacao() {
        if (this.validator.isValid()) {
            this.estadoDao.atualizar(this.estado);
            getContext().addMessage(null, new FacesMessage(ESTADO_UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.estado = new Estado();
        this.validator.showMessages();
        this.validator.clearMessages();
    }

    private void verificaEstadoSessao() {
        this.estado = getObjetoSessao("EstadoID", estadoDao, estado);
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
