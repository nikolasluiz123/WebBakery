package br.com.WebBakery.bean.manutencao;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.IngredienteDao;
import br.com.WebBakery.enums.UnidadeMedida;
import br.com.WebBakery.to.TOIngrediente;
import br.com.WebBakery.validator.IngredienteValidator;

@Named(IngredienteBean.BEAN_NAME)
@ViewScoped
public class IngredienteBean extends AbstractBaseRegisterMBean<TOIngrediente> {

    public static final String BEAN_NAME = "ingredienteBean";

    private static final long serialVersionUID = 1014269765533137572L;

    private static final String INGREDIENTE_REGISTERED_SUCCESSFULLY = "TOIngrediente cadastrado com sucesso!";

    private static final String INGREDIENTE_UPDATED_SUCCESSFULLY = "TOIngrediente atualizado com sucesso!";

    @Inject
    private IngredienteDao ingredienteDao;
    private IngredienteValidator ingredienteValidator;
    private TOIngrediente toIngrediente;
    private UnidadeMedida unidadeMedida;

    @PostConstruct
    private void init() {
        this.toIngrediente = new TOIngrediente();
    }

    @Transactional
    public void cadastrar() {
        this.ingredienteValidator = new IngredienteValidator(this.toIngrediente);
        this.toIngrediente.setUnidadeMedida(unidadeMedida);

        if (this.toIngrediente.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        atualizarTela();
    }

    private void efetuarCadastro() {
        if (ingredienteValidator.isValid()) {
            try {
                this.toIngrediente.setAtivo(true);
                this.ingredienteDao.cadastrar(this.toIngrediente);
                getContext().addMessage(null,
                                        new FacesMessage(INGREDIENTE_REGISTERED_SUCCESSFULLY));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void efetuarAtualizacao() {
        if (this.ingredienteValidator.isValid()) {
            try {
                this.ingredienteDao.atualizar(this.toIngrediente);
                getContext().addMessage(null, new FacesMessage(INGREDIENTE_UPDATED_SUCCESSFULLY));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void atualizarTela() {
        this.toIngrediente = new TOIngrediente();
        this.ingredienteValidator.showMessages();
        this.ingredienteValidator.clearMessages();
    }

    public TOIngrediente getToIngrediente() {
        return toIngrediente;
    }

    public void setToIngrediente(TOIngrediente toIngrediente) {
        this.toIngrediente = toIngrediente;
    }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public UnidadeMedida[] getUnidadeMedidas() {
        return UnidadeMedida.values();
    }
}
