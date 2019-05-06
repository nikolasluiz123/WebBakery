package br.com.WebBakery.bean;

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
import javax.transaction.Transactional;

import br.com.WebBakery.dao.ReceitaDao;
import br.com.WebBakery.model.Receita;
import br.com.WebBakery.validator.ReceitaValidator;

@Named
@ViewScoped
public class ReceitaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    transient private EntityManager em;
    @Inject
    transient private FacesContext context;

    private Receita receita;
    private List<Receita> receitas;
    private ReceitaDao receitaDao;
    private ReceitaValidator validator;

    @PostConstruct
    private void init() {
        this.receita = new Receita();
        this.receitas = new ArrayList<>();
        this.validator = new ReceitaValidator(this.receita);
        this.receitaDao = new ReceitaDao(this.em);
        initReceitas();
    }

    @Transactional
    public void cadastrar() {
        if (this.receita.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        if (this.validator.getMessages().isEmpty()) {
            this.receita = new Receita();
        }
        atualizarTela();
    }

    @Transactional
    private void efetuarCadastro() {
        List<Receita> todasAsReceitas = this.receitaDao.listarTodos();
        if (this.validator.isValid() && !this.validator.existe(todasAsReceitas)) {
            this.receita.setAtivo(true);
            this.receitaDao.cadastrar(this.receita);
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                "Receita cadastrada com sucesso!",
                                                "Receita cadastrada com sucesso!"));
        }
    }

    @Transactional
    private void efetuarAtualizacao() {
        if (this.validator.isValid()) {
            this.receitaDao.atualizar(this.receita);
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                "Receita atualizada com sucesso!",
                                                "Receita atualizada com sucesso!"));
        }
    }

    private void atualizarTela() {
        this.validator.showMessages();
        this.validator.clearMessages();
        this.validator = new ReceitaValidator(this.receita);
        initReceitas();
    }

    public void carregar(Receita receita) {
        this.validator = new ReceitaValidator(receita);
        this.receita = receita;
    }

    @Transactional
    public void inativar(Receita receita) {
        receita.setAtivo(false);
        this.receitaDao.atualizar(receita);
        initReceitas();
    }

    private void initReceitas() {
        this.receitas = this.receitaDao.listarTodos(true);
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public List<Receita> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<Receita> receitas) {
        this.receitas = receitas;
    }

}
