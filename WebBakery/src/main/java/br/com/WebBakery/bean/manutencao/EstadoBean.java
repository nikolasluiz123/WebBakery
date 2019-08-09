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
import br.com.WebBakery.to.TOEstado;
import br.com.WebBakery.to.TOPais;
import br.com.WebBakery.validator.EstadoValidator;

@Named(EstadoBean.BEAN_NAME)
@ViewScoped
public class EstadoBean extends AbstractBaseRegisterMBean<TOEstado> {

    public static final String BEAN_NAME = "estadoBean";
    
    private static final String ESTADO_UPDATED_SUCCESSFULLY = "Estado atualizado com sucesso!";
    private static final String ESTADO_REGISTRED_SUCCESSFULLY = "Estado cadastrado com sucesso!";

    private static final long serialVersionUID = -4701039486320007318L;

    @Inject
    private EstadoDao estadoDao;
    private TOEstado toEstado;

    @Inject
    private PaisDao paisDao;
    private TOPais toPaisSelecionado;
    private List<TOPais> toPaises;
    private List<TOPais> toPaisesFiltrados;

    private EstadoValidator validator;

    @PostConstruct
    private void init() {
        this.toEstado = new TOEstado();

        this.toPaisSelecionado = new TOPais();
        this.toPaises = new ArrayList<>();

        initListPaises();
    }

    @Transactional
    public void cadastrar() {
        this.validator = new EstadoValidator(this.toEstado);
        if (this.toEstado.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        atualizarTela();
    }

    private void efetuarCadastro() {
        if (this.validator.isValid()) {
            this.toEstado.setAtivo(true);
            try {
                this.estadoDao.cadastrar(this.toEstado);
            } catch (Exception e) {
                e.printStackTrace();
            }
            getContext().addMessage(null, new FacesMessage(ESTADO_REGISTRED_SUCCESSFULLY));
        }
    }

    private void efetuarAtualizacao() {
        if (this.validator.isValid()) {
            try {
                this.estadoDao.atualizar(this.toEstado);
            } catch (Exception e) {
                e.printStackTrace();
            }
            getContext().addMessage(null, new FacesMessage(ESTADO_UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.toEstado = new TOEstado();
        this.validator.showMessages();
        this.validator.clearMessages();
    }

    public void setarPais() {
        this.toEstado.setToPais(this.toPaisSelecionado);
    }

    private void initListPaises() {
        try {
            this.toPaises = this.paisDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TOEstado getToEstado() {
        return this.toEstado;
    }

    public void setToEstado(TOEstado toEstado) {
        this.toEstado = toEstado;
    }

    public List<TOPais> getToPaises() {
        return toPaises;
    }

    public void setToPaises(List<TOPais> toPaises) {
        this.toPaises = toPaises;
    }

    public TOPais getToPaisSelecionado() {
        return toPaisSelecionado;
    }

    public void setToPaisSelecionado(TOPais toPaisSelecionado) {
        this.toPaisSelecionado = toPaisSelecionado;
    }

    public void setToPaisesFiltrados(List<TOPais> toPaisesFiltrados) {
        this.toPaisesFiltrados = toPaisesFiltrados;
    }

    public List<TOPais> getToPaisesFiltrados() {
        return toPaisesFiltrados;
    }

    public void setPaisesFiltrados(List<TOPais> toPaisesFiltrados) {
        this.toPaisesFiltrados = toPaisesFiltrados;
    }

}
