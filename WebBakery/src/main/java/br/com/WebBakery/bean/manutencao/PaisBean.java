package br.com.WebBakery.bean.manutencao;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.PaisDao;
import br.com.WebBakery.to.TOPais;

@Named(PaisBean.BEAN_NAME)
@ViewScoped
public class PaisBean extends AbstractBaseRegisterMBean<TOPais> {

    public static final String BEAN_NAME = "paisBean";
    private static final String PAIS_UPDATED_SUCCESSFULLY = "País atualizado com sucesso!";
    private static final String REGISTRED_SUCCESSFULLY = "País cadastrado com sucesso!";

    private static final long serialVersionUID = 3302600980972231377L;

    @Inject
    private PaisDao paisDao;
    
    @Transactional
    public void salvar() {
        try {
            if (validator.isValid()) {
                getTo().setAtivo(true);
                getDao().cadastrar(getTo());
                showMessageSuccess();
            }
            atualizarTela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showMessageSuccess() {
        if (getTo().getId() == null) {
            getContext().addMessage(null, new FacesMessage(getMsgInsert()));
        } else {
            getContext().addMessage(null, new FacesMessage(getMsgUpdate()));
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
    protected String getMsgInsert() {
        return PAIS_UPDATED_SUCCESSFULLY;
    }

    @Override
    protected String getMsgUpdate() {
        return REGISTRED_SUCCESSFULLY;
    }
}
