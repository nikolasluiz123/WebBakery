package br.com.WebBakery.bean.consulta;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseListMBean;
import br.com.WebBakery.bean.manutencao.PaisBean;
import br.com.WebBakery.dao.PaisDao;
import br.com.WebBakery.interfaces.IBaseListMBean;
import br.com.WebBakery.to.TOPais;
import br.com.WebBakery.util.Faces_Util;

@Named(ListaPaisBean.BEAN_NAME)
@ViewScoped
public class ListaPaisBean extends AbstractBaseListMBean implements IBaseListMBean<TOPais> {

    public static final String BEAN_NAME = "listaPaisBean";

    private static final long serialVersionUID = 4296793409566608609L;

    private static final String PAIS_INATIVATED_SUCCESSFULLY = "País inativado com sucesso!";

    @Inject
    private PaisDao paisDao;
    private List<TOPais> paises;
    private List<TOPais> paisesFiltrados;

    @PostConstruct
    private void init() {
        this.paises = new ArrayList<>();
        initListPaises();
    }

    @Transactional
    @Override
    public void inativar(TOPais to) {
        to.setAtivo(false);

        try {
            this.paisDao.atualizar(to);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initListPaises();
        getContext().addMessage(null, new FacesMessage(PAIS_INATIVATED_SUCCESSFULLY));
    }

    private void initListPaises() {
        try {
            this.paises = this.paisDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void carregar(Integer paisID) throws Exception {
        String keyAtribute = "PaisID";
        String pageRedirect = "cadastroPais.xhtml";
        setObjetoSessao(paisID, keyAtribute, pageRedirect);
        PaisBean registerBean = getRegisterBean();
        registerBean.setToPais(registerBean.getObjetoSessao(keyAtribute, paisDao));
    }
    
    private PaisBean getRegisterBean() {
        return ((PaisBean) Faces_Util.getBean(PaisBean.BEAN_NAME));
    }

    public List<TOPais> getPaises() {
        return paises;
    }

    public void setPaises(List<TOPais> paises) {
        this.paises = paises;
    }

    public List<TOPais> getPaisesFiltrados() {
        return paisesFiltrados;
    }

    public void setPaisesFiltrados(List<TOPais> paisesFiltrados) {
        this.paisesFiltrados = paisesFiltrados;
    }

}
