package br.com.WebBakery.bean.consulta;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.WebBakery.abstractClass.AbstractBaseListMBean;
import br.com.WebBakery.dao.PaisDao;
import br.com.WebBakery.interfaces.IBaseListMBean;
import br.com.WebBakery.to.TOPais;

@Named(ListaPaisBean.BEAN_NAME)
@ViewScoped
public class ListaPaisBean extends AbstractBaseListMBean implements IBaseListMBean<TOPais> {

    public static final String BEAN_NAME = "listaPaisBean";

    private static final long serialVersionUID = 4296793409566608609L;

    private static final String PAIS_INATIVATED_SUCCESSFULLY = "País inativado com sucesso!";

    @Inject
    private PaisDao paisDao;
    private List<TOPais> toPaises;
    private List<TOPais> toPaisesFiltrados;

    @PostConstruct
    private void init() {
        this.toPaises = new ArrayList<>();
        initListPaises();
    }

    private void initListPaises() {
        try {
            this.toPaises = this.paisDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inativar(TOPais to) {
        try {
            to.setAtivo(false);
            this.paisDao.salvar(to);
            initListPaises();
            getContext().addMessage(null, new FacesMessage(PAIS_INATIVATED_SUCCESSFULLY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TOPais> getToPaises() {
        return toPaises;
    }

    public void setToPaises(List<TOPais> toPaises) {
        this.toPaises = toPaises;
    }

    public List<TOPais> getToPaisesFiltrados() {
        return toPaisesFiltrados;
    }

    public void setToPaisesFiltrados(List<TOPais> toPaisesFiltrados) {
        this.toPaisesFiltrados = toPaisesFiltrados;

    }

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

}
