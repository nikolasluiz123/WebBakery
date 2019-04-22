package br.com.WebBakery.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
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
import br.com.WebBakery.validator.EstadoValidator;

@Named
@ViewScoped
public class EstadoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;
    private EstadoDao estadoDao;
    private PaisDao paisDao;
    private Estado estado;
    private List<Estado> estados;
    private Pais pais;
    private Integer paisId;
    private List<Pais> paises;
    private List<SelectItem> paisesCombo;
    @Inject
    transient private FacesContext context;
    private EstadoValidator validator;

    @PostConstruct
    private void init() {
        this.estadoDao = new EstadoDao(this.em);
        this.paisDao = new PaisDao(this.em);
        this.estado = new Estado();
        this.estados = new ArrayList<>();
        this.pais = new Pais();
        this.paises = this.paisDao.listarTodos(true);
        this.paisesCombo = new ArrayList<>();
        this.validator = new EstadoValidator(this.estado);
        adicionaPaisesNaCombo();
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

    public List<Estado> getEstados() {
        this.estados = this.estadoDao.listarTodos(true);
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public List<SelectItem> getPaisesCombo() {
        return paisesCombo;
    }

    public void setPaisesCombo(List<SelectItem> paisesCombo) {
        this.paisesCombo = paisesCombo;
    }

    public Integer getPaisId() {
        return paisId;
    }

    public void setPaisId(Integer paisId) {
        this.paisId = paisId;
    }

    @Transactional
    public void cadastrar() {
        if (this.estado.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        atualizarTela();
    }

    @Transactional
    public void carregar(Estado estado) {
        Integer paisIdEstado = estado.getPais().getId();
        this.validator = new EstadoValidator(estado);
        this.estado = estado;
        this.paisId = paisIdEstado;
    }

    @Transactional
    public void inativar(Estado estado) {
        estado.setAtivo(false);
        this.estadoDao.atualizar(estado);
        this.estados = getEstados();
    }

    private void atualizarTela() {
        this.estado = new Estado();
        this.estados = getEstados();
        this.validator.mostrarMensagens();
        this.validator.clearMessages();
    }

    private void efetuarAtualizacao() {
        if (this.validator.ehValido()) {
            Pais paisComId = this.paisDao.buscarPelaId(this.paisId);
            this.estado.setPais(paisComId);
            this.estadoDao.atualizar(this.estado);
            context.addMessage("formCadastroEstado:messages",
                               new FacesMessage("Estado atualizado com sucesso!"));
        }
    }

    private void efetuarCadastro() {
        List<Estado> todosOsEstados = this.estadoDao.listarTodos();
        if (!this.validator.existe(todosOsEstados) && this.validator.ehValido()) {
            for (Pais pais : paises) {
                if (pais.getId() == this.paisId) {
                    this.pais = pais;
                    break;
                }
            }
            this.estado.setPais(this.pais);
            this.estado.setAtivo(true);
            this.estadoDao.cadastrar(this.estado);
            context.addMessage("formCadastroEstado:messages",
                               new FacesMessage("Estado cadastrado com sucesso!"));
        }
    }

    private void adicionaPaisesNaCombo() {
        for (Pais p : paises) {
            getPaisesCombo().add(new SelectItem(p.getId(), p.getNome()));
        }
    }
}
