package br.com.WebBakery.bean.manutencao;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import br.com.WebBakery.dao.ReceitaDao;
import br.com.WebBakery.model.Receita;
import br.com.WebBakery.util.FacesUtil;
import br.com.WebBakery.validator.ReceitaValidator;

@Named
@ViewScoped
public class ReceitaBean implements Serializable {

    private static final long serialVersionUID = 4300601613343189689L;

    private static final String UPDATED_SUCCESSFULLY = "Receita atualizada com sucesso!";
    private static final String REGISTERED_SUCCESSFULLY = "Receita cadastrada com sucesso!";

    @Inject
    transient private EntityManager em;
    @Inject
    transient private FacesContext context;

    private Receita receita;
    private ReceitaDao receitaDao;
    private ReceitaValidator validator;

    @PostConstruct
    private void init() {
        this.receita = new Receita();
        this.receitaDao = new ReceitaDao(this.em);
        verificaReceitaSessao();
    }

    @Transactional
    public void cadastrar() {
        this.validator = new ReceitaValidator(this.receita);
        if (this.receita.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        atualizarTela();
    }

    @Transactional
    private void efetuarCadastro() {
        if (this.validator.isValid()) {
            this.receita.setAtivo(true);
            this.receitaDao.cadastrar(this.receita);
            context.addMessage(null, new FacesMessage(REGISTERED_SUCCESSFULLY));
        }
    }

    @Transactional
    private void efetuarAtualizacao() {
        if (this.validator.isValid()) {
            this.receitaDao.atualizar(this.receita);
            context.addMessage(null, new FacesMessage(UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.receita = new Receita();
        this.validator.showMessages();
        this.validator.clearMessages();
    }

    private void verificaReceitaSessao() {
        Integer receitaId = (Integer) FacesUtil.getHTTPSession().getAttribute("ReceitaID");
        if (receitaId != null) {
            this.receita = receitaDao.buscarPorId(Receita.class, receitaId);
            FacesUtil.getHTTPSession().removeAttribute("ReceitaID");
        }
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }
}
