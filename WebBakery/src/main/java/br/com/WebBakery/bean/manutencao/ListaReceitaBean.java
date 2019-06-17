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
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import br.com.WebBakery.dao.ReceitaDao;
import br.com.WebBakery.model.Receita;
import br.com.WebBakery.util.FacesUtil;

@Named
@ViewScoped
public class ListaReceitaBean implements Serializable {

    private static final long serialVersionUID = -187265901878598052L;

    private static final String RECEITA_INATIVATED_SUCCESSFULLY = "Receita atualizada com sucesso!";

    @Inject
    transient private EntityManager em;
    @Inject
    transient private FacesContext context;

    private ReceitaDao receitaDao;
    private List<Receita> receitas;
    private List<Receita> receitasFiltradas;

    @PostConstruct
    private void init() {
        this.receitas = new ArrayList<>();
        this.receitaDao = new ReceitaDao(this.em);
        initReceitas();
    }

    public void carregar(Integer receitaID) throws IOException {
        HttpSession session = FacesUtil.getHTTPSession();
        session.setAttribute("ReceitaID", receitaID);
        context.getExternalContext().redirect("cadastroReceita.xhtml");
    }

    @Transactional
    public void inativar(Receita receita) {
        receita.setAtivo(false);
        this.receitaDao.atualizar(receita);
        initReceitas();
        context.addMessage(null, new FacesMessage(RECEITA_INATIVATED_SUCCESSFULLY));
    }

    private void initReceitas() {
        this.receitas = this.receitaDao.listarTodos(true);
    }

    public List<Receita> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<Receita> receitas) {
        this.receitas = receitas;
    }

    public List<Receita> getReceitasFiltradas() {
        return receitasFiltradas;
    }

    public void setReceitasFiltradas(List<Receita> receitasFiltradas) {
        this.receitasFiltradas = receitasFiltradas;
    }

}
