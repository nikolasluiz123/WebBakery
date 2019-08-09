package br.com.WebBakery.bean.consulta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseListMBean;
import br.com.WebBakery.dao.PaisDao;
import br.com.WebBakery.interfaces.IBaseListMBean;
import br.com.WebBakery.to.TOPais;

@Named
@ViewScoped
public class ListaPaisBean extends AbstractBaseListMBean implements IBaseListMBean<TOPais> {

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

    public void carregar(Integer paisID) throws IOException {
        setObjetoSessao(paisID, "PaisID", "cadastroPais.xhtml");
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
