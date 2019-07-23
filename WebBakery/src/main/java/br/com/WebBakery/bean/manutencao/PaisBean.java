package br.com.WebBakery.bean.manutencao;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.PaisDao;
import br.com.WebBakery.model.Pais;
import br.com.WebBakery.validator.PaisValidator;

@Named
@ViewScoped
public class PaisBean extends AbstractBaseRegisterMBean<Pais> {

    private static final String PAIS_UPDATED_SUCCESSFULLY = "País atualizado com sucesso!";
    private static final String REGISTRED_SUCCESSFULLY = "País cadastrado com sucesso!";

    private static final long serialVersionUID = 3302600980972231377L;

    private PaisDao paisDao;
    private Pais pais;
    private PaisValidator validator;

    @Override
    public void init() {
        this.paisDao = new PaisDao();
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
            getContext().addMessage(null, new FacesMessage(REGISTRED_SUCCESSFULLY));
        }
    }

    private void efetuarAtualizacao() {
        if (this.validator.isValid()) {
            this.paisDao.atualizar(this.pais);
            getContext().addMessage(null, new FacesMessage(PAIS_UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.pais = new Pais();
        this.validator.showMessages();
        this.validator.clearMessages();
    }

    public void verificaPaisSessao() {
        this.pais = getObjetoSessao("PaisID", paisDao, pais);
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

}
