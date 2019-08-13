package br.com.WebBakery.bean.manutencao;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
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

    @Inject
    private IngredienteDao ingredienteDao;
    private UnidadeMedida unidadeMedida;

    @Transactional
    public void cadastrar() {
        try {
            this.getTo().setUnidadeMedida(unidadeMedida);
            addValidators();

            if (isValid()) {
                this.getTo().setAtivo(true);
                this.ingredienteDao.salvar(getTo());
                showMessageSuccess();
            }
            atualizarTela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addValidators() {
        IngredienteValidator ingredienteValidator = new IngredienteValidator(getTo());
        addValidator(ingredienteValidator);
    }

    @PostConstruct
    private void init() {
        verificaObjetoSessao();

        if (getTo() == null) {
            resetTo();
        }

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

    @Override
    protected AbstractBaseDao<TOIngrediente> getDao() {
        return ingredienteDao;
    }

    @Override
    protected TOIngrediente getNewInstaceTO() {
        return new TOIngrediente();
    }

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }
}
