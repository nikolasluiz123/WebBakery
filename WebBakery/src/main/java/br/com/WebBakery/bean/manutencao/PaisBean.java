package br.com.WebBakery.bean.manutencao;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.PaisDao;
import br.com.WebBakery.to.TOPais;
import br.com.WebBakery.validator.PaisValidator;

@Named(PaisBean.BEAN_NAME)
@ViewScoped
public class PaisBean extends AbstractBaseRegisterMBean<TOPais> {

    public static final String BEAN_NAME = "paisBean";
    private static final String PAIS_UPDATED_SUCCESSFULLY = "País atualizado com sucesso!";
    private static final String REGISTRED_SUCCESSFULLY = "País cadastrado com sucesso!";

    private static final long serialVersionUID = 3302600980972231377L;

    @Inject
    private PaisDao paisDao;
    private TOPais toPais;
    private PaisValidator validator;

    @PostConstruct
    private void init() {
        this.toPais = new TOPais();
    }

    @Transactional
    public void cadastrar() {
        this.validator = new PaisValidator(this.toPais);
        if (this.toPais.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        atualizarTela();
    }

    private void efetuarCadastro() {
        if (validator.isValid()) {
            this.toPais.setAtivo(true);
            try {
                this.paisDao.cadastrar(this.toPais);
            } catch (Exception e) {
                e.printStackTrace();
            }
            getContext().addMessage(null, new FacesMessage(REGISTRED_SUCCESSFULLY));
        }
    }

    private void efetuarAtualizacao() {
        if (this.validator.isValid()) {
            try {
                this.paisDao.atualizar(this.toPais);
            } catch (Exception e) {
                e.printStackTrace();
            }
            getContext().addMessage(null, new FacesMessage(PAIS_UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.toPais = new TOPais();
        this.validator.showMessages();
        this.validator.clearMessages();
    }

    public TOPais getToPais() {
        return toPais;
    }

    public void setToPais(TOPais toPais) {
        this.toPais = toPais;
    }

}
