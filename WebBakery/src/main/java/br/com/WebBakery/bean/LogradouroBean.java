package br.com.WebBakery.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import br.com.WebBakery.dao.CidadeDao;
import br.com.WebBakery.dao.LogradouroDao;
import br.com.WebBakery.model.Cidade;
import br.com.WebBakery.model.Logradouro;
import br.com.WebBakery.validator.LogradouroValidator;

@Named
@ViewScoped
public class LogradouroBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;
    private LogradouroDao logradouroDao;
    private CidadeDao cidadeDao;
    private Logradouro logradouro;
    private List<Logradouro> logradouros;
    private Cidade cidade;
    private Integer cidadeId;
    private List<Cidade> cidades;
    private List<SelectItem> cidadesCombo;
    @Inject
    transient private FacesContext context;
    private LogradouroValidator validator;

    @PostConstruct
    private void init() {
        this.logradouroDao = new LogradouroDao(this.em);
        this.cidadeDao = new CidadeDao(this.em);
        this.logradouro = new Logradouro();
        this.logradouros = new ArrayList<>();
        this.cidade = new Cidade();
        this.cidades = this.cidadeDao.listarTodos(true);
        this.cidadesCombo = new ArrayList<>();
        this.validator = new LogradouroValidator(this.logradouro);
        for (Cidade c : cidades) {
            getCidadesCombo().add(new SelectItem(c.getId(), c.getNome()));
        }
    }

    public Logradouro getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(Logradouro logradouro) {
        this.logradouro = logradouro;
    }

    public List<Logradouro> getLogradouros() {
        this.logradouros = this.logradouroDao.listarTodos(true);
        return logradouros;
    }

    public void setLogradouros(List<Logradouro> logradouros) {
        this.logradouros = logradouros;
    }

    public List<Cidade> getCidades() {
        this.cidades = this.cidadeDao.listarTodos(true);
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }

    public List<SelectItem> getCidadesCombo() {
        return cidadesCombo;
    }

    public void setComboCidades(List<SelectItem> comboCidades) {
        this.cidadesCombo = comboCidades;
    }

    public Integer getCidadeId() {
        return cidadeId;
    }

    public void setCidadeId(Integer cidadeId) {
        this.cidadeId = cidadeId;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    @Transactional
    public void cadastrar() {
        if (this.logradouro.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        atualizarTela();
    }

    @Transactional
    public void carregar(Logradouro logradouro) {
        Integer cidadeIdLogradouro = logradouro.getCidade().getId();
        this.validator = new LogradouroValidator(logradouro);
        this.logradouro = logradouro;
        this.cidadeId = cidadeIdLogradouro;
    }

    @Transactional
    public void inativar(Logradouro logradouro) {
        logradouro.setAtivo(false);
        this.logradouroDao.atualizar(logradouro);
        this.logradouros = getLogradouros();
    }

    private void efetuarAtualizacao() {
        if (this.validator.ehValido()) {
            Cidade cidadeComId = this.cidadeDao.buscarPelaId(this.cidadeId);
            this.logradouro.setCidade(cidadeComId);
            this.logradouroDao.atualizar(this.logradouro);
            context.addMessage("formCadastroLogradouro:messages",
                               new FacesMessage("Logradouro atualizado com sucesso!"));
        }
    }

    private void efetuarCadastro() {
        List<Logradouro> todosOsLogradouros = this.logradouroDao.listarTodos();
        if (!this.validator.existe(todosOsLogradouros) && this.validator.ehValido()) {
            for (Cidade cidade : cidades) {
                if (cidade.getId() == this.cidadeId) {
                    this.cidade = cidade;
                    break;
                }
            }
            this.logradouro.setAtivo(true);
            this.logradouro.setCidade(this.cidade);
            this.logradouroDao.cadastrar(this.logradouro);
            context.addMessage("formCadastroLogradouro:messages",
                               new FacesMessage("Logradouro cadastrado com sucesso!"));
        }
    }

    private void atualizarTela() {
        this.logradouro = new Logradouro();
        this.logradouros = getLogradouros();
        this.validator.mostrarMensagens();
        this.validator.clearMessages();
    }

}
