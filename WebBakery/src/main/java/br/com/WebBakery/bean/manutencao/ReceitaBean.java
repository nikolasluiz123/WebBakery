package br.com.WebBakery.bean.manutencao;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.ReceitaDao;
import br.com.WebBakery.to.TOReceita;
import br.com.WebBakery.validator.ReceitaValidator;

@Named(ReceitaBean.BEAN_NAME)
@ViewScoped
public class ReceitaBean extends AbstractBaseRegisterMBean<TOReceita> {

    public static final String BEAN_NAME = "receitaBean";

    private static final long serialVersionUID = 4300601613343189689L;

    private static final String UPDATED_SUCCESSFULLY = "TOReceita atualizada com sucesso!";
    private static final String REGISTERED_SUCCESSFULLY = "TOReceita cadastrada com sucesso!";

    private TOReceita toReceita;
    @Inject
    private ReceitaDao receitaDao;
    private ReceitaValidator validator;

    @PostConstruct
    private void init() {
        this.toReceita = new TOReceita();
    }

    @Transactional
    public void cadastrar() {
        this.validator = new ReceitaValidator(this.toReceita);
        if (this.toReceita.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        atualizarTela();
    }

    @Transactional
    private void efetuarCadastro() {
        if (this.validator.isValid()) {
            this.toReceita.setAtivo(true);
            try {
                this.receitaDao.cadastrar(this.toReceita);
            } catch (Exception e) {
                e.printStackTrace();
            }
            getContext().addMessage(null, new FacesMessage(REGISTERED_SUCCESSFULLY));
        }
    }

    @Transactional
    private void efetuarAtualizacao() {
        if (this.validator.isValid()) {
            try {
                this.receitaDao.atualizar(this.toReceita);
            } catch (Exception e) {
                e.printStackTrace();
            }
            getContext().addMessage(null, new FacesMessage(UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.toReceita = new TOReceita();
        this.validator.showMessages();
        this.validator.clearMessages();
    }

    public TOReceita getToReceita() {
        return toReceita;
    }

    public void setToReceita(TOReceita toReceita) {
        this.toReceita = toReceita;
    }

}
