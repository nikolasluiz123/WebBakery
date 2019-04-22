package br.com.WebBakery.bean;

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

import br.com.WebBakery.dao.PaisDao;
import br.com.WebBakery.model.Pais;
import br.com.WebBakery.validator.PaisValidator;

@Named
@ViewScoped
public class PaisBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;
    private PaisDao paisDao;
    private Pais pais;
    private List<Pais> paises;
    @Inject
    transient private FacesContext context;
    private PaisValidator validator;

    @PostConstruct
    private void init() {
        this.paisDao = new PaisDao(this.em);
        this.pais = new Pais();
        this.paises = new ArrayList<>();
        this.validator = new PaisValidator(this.pais);
    }

    public PaisBean() {
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public List<Pais> getPaises() {
        this.paises = this.paisDao.listarTodos(true);
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }

    @Transactional
    public void cadastrar() {
        if (this.pais.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        atualizarTela();
    }

    @Transactional
    public void carregar(Pais pais) {
        this.validator = new PaisValidator(pais);
        this.pais = pais;
    }

    @Transactional
    public void inativar(Pais pais) {
        pais.setAtivo(false);
        this.paisDao.atualizar(pais);
        this.paises = this.paisDao.listarTodos(true);
    }

    private void efetuarAtualizacao() {
        if (this.validator.ehValido()) {
            this.paisDao.atualizar(this.pais);
            context.addMessage("formCadastroPais:messages",
                               new FacesMessage("País atualizado com sucesso!"));
        }
    }

    private void efetuarCadastro() {
        if (!validator.existe(this.paisDao.listarTodos()) && validator.ehValido()) {
            this.pais.setAtivo(true);
            this.paisDao.cadastrar(this.pais);
            context.addMessage("formCadastroPais:messages",
                               new FacesMessage("País cadastrado com sucesso!"));
        }
    }

    private void atualizarTela() {
        this.pais = new Pais();
        this.paises = getPaises();
        validator.mostrarMensagens();
        this.validator.clearMessages();
    }
}
