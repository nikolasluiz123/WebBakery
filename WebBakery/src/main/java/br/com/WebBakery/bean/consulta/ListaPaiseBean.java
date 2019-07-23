package br.com.WebBakery.bean.consulta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseListMBean;
import br.com.WebBakery.dao.PaisDao;
import br.com.WebBakery.model.Pais;

@Named
@ViewScoped
public class ListaPaiseBean extends AbstractBaseListMBean<Pais> {

    private static final long serialVersionUID = 5308260090843275203L;

    private static final String PAIS_INATIVATED_SUCCESSFULLY = "País inativado com sucesso!";

    private PaisDao paisDao;
    private List<Pais> paises;
    private List<Pais> paisesFiltrados;

    public void init() {
        this.paisDao = new PaisDao();
        this.paises = new ArrayList<>();
        initListPaises();
    }

    @Transactional
    public void inativar(Pais pais) {
        pais.setAtivo(false);
        this.paisDao.atualizar(pais);
        getContext().addMessage(null, new FacesMessage(PAIS_INATIVATED_SUCCESSFULLY));
        initListPaises();
    }

    private void initListPaises() {
        this.paises = this.paisDao.listarTodos(true);
    }

    public void carregar(Integer paisID) throws IOException {
        setObjetoSessao(paisID, "PaisID", "cadastroPais.xhtml");
    }

    public List<Pais> getPaises() {
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }

    public List<Pais> getPaisesFiltrados() {
        return paisesFiltrados;
    }

    public void setPaisesFiltrados(List<Pais> paisesFiltrados) {
        this.paisesFiltrados = paisesFiltrados;
    }

}
