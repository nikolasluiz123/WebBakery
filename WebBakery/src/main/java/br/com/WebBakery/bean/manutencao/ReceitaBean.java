package br.com.WebBakery.bean.manutencao;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.ReceitaDao;
import br.com.WebBakery.model.Receita;
import br.com.WebBakery.validator.ReceitaValidator;

@Named
@ViewScoped
public class ReceitaBean extends AbstractBaseRegisterMBean<Receita> {

    private static final long serialVersionUID = 4300601613343189689L;

    private static final String UPDATED_SUCCESSFULLY = "Receita atualizada com sucesso!";
    private static final String REGISTERED_SUCCESSFULLY = "Receita cadastrada com sucesso!";

    private Receita receita;
    @Inject
    private ReceitaDao receitaDao;
    private ReceitaValidator validator;

    @PostConstruct
    private void init() {
        this.receita = new Receita();
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
            getContext().addMessage(null, new FacesMessage(REGISTERED_SUCCESSFULLY));
        }
    }

    @Transactional
    private void efetuarAtualizacao() {
        if (this.validator.isValid()) {
            this.receitaDao.atualizar(this.receita);
            getContext().addMessage(null, new FacesMessage(UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.receita = new Receita();
        this.validator.showMessages();
        this.validator.clearMessages();
    }

    private void verificaReceitaSessao() {
        this.receita = getObjetoSessao("ReceitaID", receitaDao, receita);
    
        if (receita == null) {
            this.receita = new Receita();
        }
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }
}
