package br.com.WebBakery.bean.consulta;

import java.io.Serializable;

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
import br.com.WebBakery.util.FacesUtil;
import br.com.WebBakery.validator.PaisValidator;

@Named
@ViewScoped
public class PaisBean implements Serializable {

    private static final String PAIS_UPDATED_SUCCESSFULLY = "País atualizado com sucesso!";
    private static final String REGISTRED_SUCCESSFULLY = "País cadastrado com sucesso!";

    private static final long serialVersionUID = 3302600980972231377L;

    @PersistenceContext
    private EntityManager em;
    @Inject
    transient private FacesContext context;

    private PaisDao paisDao;
    private Pais pais;
    private PaisValidator validator;

    @PostConstruct
    private void init() {
        this.paisDao = new PaisDao(this.em);
        this.pais = new Pais();
        verificaPaisSessao();
    }

    @Transactional
    public void cadastrar() {
        this.validator = new PaisValidator(this.pais);
        if (this.pais.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        atualizarTela();
    }

    private void efetuarCadastro() {
        if (validator.isValid()) {
            this.pais.setAtivo(true);
            this.paisDao.cadastrar(this.pais);
            context.addMessage(null, new FacesMessage(REGISTRED_SUCCESSFULLY));
        }
    }

    private void efetuarAtualizacao() {
        if (this.validator.isValid()) {
            this.paisDao.atualizar(this.pais);
            context.addMessage(null, new FacesMessage(PAIS_UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.pais = new Pais();
        this.validator.showMessages();
        this.validator.clearMessages();
    }

    private void verificaPaisSessao() {
        Integer paisId = (Integer) FacesUtil.getHTTPSession().getAttribute("PaisID");
        if (paisId != null) {
            this.pais = paisDao.buscarPorId(paisId);
            FacesUtil.getHTTPSession().removeAttribute("PaisID");
        }
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

}
