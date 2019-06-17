package br.com.WebBakery.bean.manutencao;

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
import javax.transaction.Transactional;

import br.com.WebBakery.dao.LogradouroDao;
import br.com.WebBakery.model.Logradouro;

@Named
@ViewScoped
public class ListaLogradouroBean implements Serializable {

    private static final long serialVersionUID = -5398966780316383519L;

    private static final String LOGRADOURO_INATIVATED_SUCCESSFULLY = "Logradouro inativado com sucesso!";

    @PersistenceContext
    private EntityManager em;
    @Inject
    transient private FacesContext context;

    private LogradouroDao logradouroDao;
    private Logradouro logradouro;
    private List<Logradouro> logradouros;
    private List<Logradouro> logradourosFiltrados;

    @PostConstruct
    private void init() {
        this.logradouroDao = new LogradouroDao(this.em);
        this.logradouro = new Logradouro();
        this.logradouros = new ArrayList<>();
        initListLogradouros();
    }

    @Transactional
    public void inativar(Logradouro logradouro) {
        logradouro.setAtivo(false);
        this.logradouroDao.atualizar(logradouro);
        initListLogradouros();
        context.addMessage(null, new FacesMessage(LOGRADOURO_INATIVATED_SUCCESSFULLY));
    }

    private void initListLogradouros() {
        this.logradouros = this.logradouroDao.listarTodos(true);
    }

    public Logradouro getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(Logradouro logradouro) {
        this.logradouro = logradouro;
    }

    public List<Logradouro> getLogradouros() {
        return logradouros;
    }

    public void setLogradouros(List<Logradouro> logradouros) {
        this.logradouros = logradouros;
    }

    public List<Logradouro> getLogradourosFiltrados() {
        return logradourosFiltrados;
    }

    public void setLogradourosFiltrados(List<Logradouro> logradourosFiltrados) {
        this.logradourosFiltrados = logradourosFiltrados;
    }

}
