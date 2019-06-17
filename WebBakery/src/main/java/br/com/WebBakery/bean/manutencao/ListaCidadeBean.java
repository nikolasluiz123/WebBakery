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

import br.com.WebBakery.dao.CidadeDao;
import br.com.WebBakery.model.Cidade;
import br.com.WebBakery.util.FacesUtil;

@Named
@ViewScoped
public class ListaCidadeBean implements Serializable {

    private static final String CIDADE_INATIVATED_SUCCESSFULLY = "Cidade inativada com sucesso!";

    private static final long serialVersionUID = 5495188526333831332L;

    @PersistenceContext
    private EntityManager em;
    @Inject
    transient private FacesContext context;

    private CidadeDao cidadeDao;
    private List<Cidade> cidades;
    private List<Cidade> cidadesFiltradas;

    @PostConstruct
    private void init() {
        this.cidadeDao = new CidadeDao(this.em);
        this.cidades = new ArrayList<>();
        initListCidades();
    }

    @Transactional
    public void carregar(Integer cidadeID) throws IOException {
        HttpSession session = FacesUtil.getHTTPSession();
        session.setAttribute("CidadeID", cidadeID);
        context.getExternalContext().redirect("cadastroCidade.xhtml");
    }

    @Transactional
    public void inativar(Cidade cidade) {
        cidade.setAtivo(false);
        this.cidadeDao.atualizar(cidade);
        initListCidades();
        context.addMessage(null, new FacesMessage(CIDADE_INATIVATED_SUCCESSFULLY));
    }

    private void initListCidades() {
        this.cidades = this.cidadeDao.listarTodos(true);
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }

    public List<Cidade> getCidadesFiltradas() {
        return cidadesFiltradas;
    }

    public void setCidadesFiltradas(List<Cidade> cidadesFiltradas) {
        this.cidadesFiltradas = cidadesFiltradas;
    }

}
