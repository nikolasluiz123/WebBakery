package br.com.WebBakery.bean.manutencao;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import br.com.WebBakery.dao.PaisDao;
import br.com.WebBakery.model.Pais;
import br.com.WebBakery.util.FacesUtil;

@Named
@ViewScoped
public class ListaPaiseBean implements Serializable {

    private static final long serialVersionUID = 5308260090843275203L;

    private static final String PAIS_INATIVATED_SUCCESSFULLY = "País inativado com sucesso!";

    @PersistenceContext
    transient private EntityManager em;
    @Inject
    transient private FacesContext context;

    private PaisDao paisDao;
    private List<Pais> paises;
    private List<Pais> paisesFiltrados;

    @PostConstruct
    private void init() {
        this.paisDao = new PaisDao(this.em);
        this.paises = new ArrayList<>();
        initListPaises();
    }

    @Transactional
    public void inativar(Pais pais) {
        pais.setAtivo(false);
        this.paisDao.atualizar(pais);
        context.addMessage(null, new FacesMessage(PAIS_INATIVATED_SUCCESSFULLY));
        initListPaises();
    }

    private void initListPaises() {
        this.paises = this.paisDao.listarTodos(true);
    }

    public void carregar(Integer paisID) throws IOException {
        HttpSession session = FacesUtil.getHTTPSession();
        session.setAttribute("PaisID", paisID);
        context.getExternalContext().redirect("cadastroPais.xhtml");
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
