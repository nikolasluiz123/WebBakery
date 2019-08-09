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
    private TOIngrediente ingrediente;
    private UnidadeMedida unidadeMedida;

    @PostConstruct
    private void init() {
        this.ingrediente = new TOIngrediente();
    }

    @Transactional
    public void cadastrar() {
        this.ingredienteValidator = new IngredienteValidator(this.ingrediente);
        this.ingrediente.setUnidadeMedida(unidadeMedida);

        if (this.ingrediente.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        atualizarTela();
    }

    private void efetuarCadastro() {
        if (ingredienteValidator.isValid()) {
            this.ingrediente.setAtivo(true);
            try {
                this.ingredienteDao.cadastrar(this.ingrediente);
            } catch (Exception e) {
                e.printStackTrace();
            }
            getContext().addMessage(null, new FacesMessage(INGREDIENTE_REGISTERED_SUCCESSFULLY));
        }
    }

    private void efetuarAtualizacao() {
        if (this.ingredienteValidator.isValid()) {
            try {
                this.ingredienteDao.atualizar(this.ingrediente);
            } catch (Exception e) {
                e.printStackTrace();
            }
            getContext().addMessage(null, new FacesMessage(INGREDIENTE_UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.ingrediente = new TOIngrediente();
        this.ingredienteValidator.showMessages();
        this.ingredienteValidator.clearMessages();
    }

    public TOIngrediente getIngrediente() {
        return ingrediente;
    }

    public void setToIngrediente(TOIngrediente ingrediente) {
        this.ingrediente = ingrediente;
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
