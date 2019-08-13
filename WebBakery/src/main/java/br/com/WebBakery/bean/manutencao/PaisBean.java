package br.com.WebBakery.bean.manutencao;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.dao.PaisDao;
import br.com.WebBakery.to.TOPais;
import br.com.WebBakery.validator.PaisValidator;

@Named(PaisBean.BEAN_NAME)
@ViewScoped
public class PaisBean extends AbstractBaseRegisterMBean<TOPais> {

    public static final String BEAN_NAME = "paisBean";

    private static final long serialVersionUID = 3302600980972231377L;

    @Inject
    private PaisDao paisDao;
    private PaisValidator validator;

    @PostConstruct
    private void init() {
        verificaObjetoSessao();

        if (getTo() == null) {
            resetTo();
        }

    }

    @Transactional
    public void salvar() {
        try {
            this.validator = new PaisValidator(getTo());
            if (getValidator().isValid()) {
                getTo().setAtivo(true);
                this.paisDao.salvar(getTo());
                showMessageSuccess();
            }
            atualizarTela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected AbstractBaseDao<TOPais> getDao() {
        return paisDao;
    }

    @Override
    protected TOPais getNewInstaceTO() {
        return new TOPais();
    }

    @Override
    public AbstractValidator getValidator() {
        return this.validator;
    }

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }
}
