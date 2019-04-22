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
import br.com.WebBakery.dao.EstadoDao;
import br.com.WebBakery.model.Cidade;
import br.com.WebBakery.model.Estado;
import br.com.WebBakery.validator.CidadeValidator;

@Named
@ViewScoped
public class CidadeBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;
    private CidadeDao cidadeDao;
    private EstadoDao estadoDao;
    private Cidade cidade;
    private List<Cidade> cidades;
    private Estado estado;
    private Integer estadoId;
    private List<Estado> estados;
    private List<SelectItem> estadosCombo;
    @Inject
    transient private FacesContext context;
    private CidadeValidator validator;

    @PostConstruct
    private void init() {
        this.cidadeDao = new CidadeDao(this.em);
        this.estadoDao = new EstadoDao(this.em);
        this.cidade = new Cidade();
        this.cidades = new ArrayList<>();
        this.estado = new Estado();
        this.estados = this.estadoDao.listarTodos(true);
        this.estadosCombo = new ArrayList<>();
        this.validator = new CidadeValidator(this.cidade);
        for (Estado e : estados) {
            getEstadosCombo().add(new SelectItem(e.getId(), e.getNome()));
        }
    }

    @Transactional
    public void cadastrar() {
        if (this.cidade.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        atualizarTela();
    }

    private void atualizarTela() {
        this.cidade = new Cidade();
        this.cidades = getCidades();
        this.validator.mostrarMensagens();
        this.validator.clearMessages();
    }

    private void efetuarAtualizacao() {
        if (this.validator.ehValido()) {
            Estado estadoComId = this.estadoDao.buscarPelaId(this.estadoId);
            this.cidade.setEstado(estadoComId);
            this.cidadeDao.atualizar(this.cidade);
            context.addMessage("formCadastroCidade:messages",
                               new FacesMessage("Cidade atualizada com sucesso!"));
        }
    }

    private void efetuarCadastro() {
        List<Cidade> todasAsCidades = this.cidadeDao.listarTodos();
        if (!this.validator.existe(todasAsCidades) && this.validator.ehValido()) {
            for (Estado estado : estados) {
                if (estado.getId() == this.estadoId) {
                    this.estado = estado;
                    break;
                }
            }
            this.cidade.setAtivo(true);
            this.cidade.setEstado(this.estado);
            this.cidadeDao.cadastrar(this.cidade);
            context.addMessage("formCadastroCidade:messages",
                               new FacesMessage("Cidade cadastrada com sucesso!"));
        }
    }

    @Transactional
    public void carregar(Cidade cidade) {
        Integer estadoIdCidade = cidade.getEstado().getId();
        this.validator = new CidadeValidator(cidade);
        this.cidade = cidade;
        this.estadoId = estadoIdCidade;
    }

    @Transactional
    public void inativar(Cidade cidade) {
        cidade.setAtivo(false);
        this.cidadeDao.atualizar(cidade);
        this.cidades = getCidades();
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public List<Cidade> getCidades() {
        this.cidades = this.cidadeDao.listarTodos(true);
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }

    public Integer getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Integer estadoId) {
        this.estadoId = estadoId;
    }

    public List<SelectItem> getEstadosCombo() {
        return estadosCombo;
    }

    public void setEstadosCombo(List<SelectItem> estadoCombo) {
        this.estadosCombo = estadoCombo;
    }

    public List<Estado> getEstados() {
        this.estados = this.estadoDao.listarTodos(true);
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
