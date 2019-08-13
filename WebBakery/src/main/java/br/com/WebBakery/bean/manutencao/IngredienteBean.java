package br.com.WebBakery.bean.manutencao;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.abstractClass.AbstractValidator;
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
    private IngredienteValidator ingredienteValidator;
    private UnidadeMedida unidadeMedida;

    @Transactional
    public void cadastrar() {
        try {
            this.ingredienteValidator = new IngredienteValidator(this.getTo());
            this.getTo().setUnidadeMedida(unidadeMedida);

            if (getValidator().isValid()) {
                this.getTo().setAtivo(true);
                this.ingredienteDao.salvar(this.getTo());
                showMessageSuccess();
            }
            atualizarTela();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public AbstractValidator getValidator() {
        return ingredienteValidator;
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
