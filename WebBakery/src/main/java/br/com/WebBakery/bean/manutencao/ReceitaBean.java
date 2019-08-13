package br.com.WebBakery.bean.manutencao;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.ReceitaDao;
import br.com.WebBakery.to.TOReceita;
import br.com.WebBakery.validator.ReceitaValidator;

@Named(ReceitaBean.BEAN_NAME)
@ViewScoped
public class ReceitaBean extends AbstractBaseRegisterMBean<TOReceita> {

    public static final String BEAN_NAME = "receitaBean";

    private static final long serialVersionUID = 4300601613343189689L;

    @Inject
    private ReceitaDao receitaDao;

    @PostConstruct
    private void init() {
        verificaObjetoSessao();

        if (getTo() == null) {
            resetTo();
        }

    }

    @Transactional
    public void cadastrar() {
        try {
            addValidators();
            if (isValid()) {
                this.getTo().setAtivo(true);
                this.receitaDao.salvar(this.getTo());
                showMessageSuccess();
            }
            atualizarTela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addValidators() {
        ReceitaValidator receitaValidator = new ReceitaValidator(this.getTo());
        addValidator(receitaValidator);
    }

    @Override
    protected AbstractBaseDao<TOReceita> getDao() {
        return receitaDao;
    }

    @Override
    protected TOReceita getNewInstaceTO() {
        return new TOReceita();
    }

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

}
