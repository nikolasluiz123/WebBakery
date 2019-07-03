package br.com.WebBakery.bean.manutencao;

import java.io.Serializable;
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

import br.com.WebBakery.dao.CidadeDao;
import br.com.WebBakery.dao.EnderecoDao;
import br.com.WebBakery.dao.EstadoDao;
import br.com.WebBakery.dao.FuncionarioDao;
import br.com.WebBakery.dao.LogradouroDao;
import br.com.WebBakery.dao.PaisDao;
import br.com.WebBakery.dao.UsuarioDao;
import br.com.WebBakery.model.Cidade;
import br.com.WebBakery.model.Endereco;
import br.com.WebBakery.model.Estado;
import br.com.WebBakery.model.Funcionario;
import br.com.WebBakery.model.Logradouro;
import br.com.WebBakery.model.Pais;
import br.com.WebBakery.model.Usuario;
import br.com.WebBakery.util.Faces_Util;
import br.com.WebBakery.validator.EnderecoValidator;
import br.com.WebBakery.validator.FuncionarioValidator;

@Named
@ViewScoped
public class FuncionarioBean implements Serializable {

    private static final String FUNCIONARIO_UPDATED_SUCCESSFULLY = "Funcionário atualizado com sucesso!";

    private static final String FUNCIONARIO_REGISTRED_SUCCESSFULLY = "Funcionario cadastrado com sucesso!";

    private static final long serialVersionUID = -3087884190174464470L;

    @PersistenceContext
    transient private EntityManager em;

    private FuncionarioDao funcionarioDao;
    private Funcionario funcionario;

    private FuncionarioValidator funcionarioValidator;
    private EnderecoValidator enderecoValidator;

    private UsuarioDao usuarioDao;
    private Usuario usuarioSelecionado;
    private List<Usuario> usuarios;
    private List<Usuario> usuariosFiltrados;

    private PaisDao paisDao;
    private Pais paisSelecionado;
    private List<Pais> paises;
    private List<Pais> paisesFiltrados;

    private EstadoDao estadoDao;
    private Estado estadoSelecionado;
    private List<Estado> estados;
    private List<Estado> estadosFiltrados;

    private CidadeDao cidadeDao;
    private Cidade cidadeSelecionada;
    private List<Cidade> cidades;
    private List<Cidade> cidadesFiltradas;

    private LogradouroDao logradouroDao;

    private EnderecoDao enderecoDao;

    @Inject
    transient private FacesContext context;

    @PostConstruct
    private void init() {
        this.funcionarioDao = new FuncionarioDao(this.em);
        this.funcionario = new Funcionario();

        this.enderecoDao = new EnderecoDao(this.em);
        this.funcionario.setEndereco(new Endereco());
        this.funcionario.getEndereco().setPais(new Pais());
        this.funcionario.getEndereco().setEstado(new Estado());
        this.funcionario.getEndereco().setCidade(new Cidade());
        this.funcionario.getEndereco().setLogradouro(new Logradouro());

        this.logradouroDao = new LogradouroDao(this.em);

        this.usuarioDao = new UsuarioDao(this.em);
        this.usuarioSelecionado = new Usuario();

        this.paisDao = new PaisDao(this.em);
        this.paisSelecionado = new Pais();

        this.estadoDao = new EstadoDao(this.em);
        this.estadoSelecionado = new Estado();

        this.cidadeDao = new CidadeDao(this.em);
        this.cidadeSelecionada = new Cidade();

        initListPaises();
        initListEstados();
        initListCidades();
        initListUsuarios();
        verficarFuncionarioSessao();
    }

    @Transactional
    public void cadastrar() {
        this.enderecoValidator = new EnderecoValidator(this.funcionario.getEndereco());
        this.funcionarioValidator = new FuncionarioValidator(this.funcionario);
        if (this.funcionario.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        atualizarTela();
    }

    private void efetuarCadastro() {
        if (funcionarioValidator.isValid() && enderecoValidator.ehValido()) {
            cadastrarLogradouroFuncionario();
            cadastrarEnderecoFuncionario();
            this.funcionario.setAtivo(true);
            this.funcionarioDao.cadastrar(this.funcionario);
            context.addMessage(null, new FacesMessage(FUNCIONARIO_REGISTRED_SUCCESSFULLY));
        }
    }

    private void cadastrarEnderecoFuncionario() {
        this.funcionario.getEndereco().setAtivo(true);
        this.enderecoDao.cadastrar(this.funcionario.getEndereco());
    }

    private void cadastrarLogradouroFuncionario() {
        this.funcionario.getEndereco().getLogradouro().setAtivo(true);
        this.funcionario.getEndereco().getLogradouro()
                .setCidade(this.funcionario.getEndereco().getCidade());
        this.logradouroDao.cadastrar(this.funcionario.getEndereco().getLogradouro());
    }

    private void efetuarAtualizacao() {
        if (funcionarioValidator.isValid() && enderecoValidator.ehValido()) {
            this.funcionarioDao.atualizar(this.funcionario);
            this.enderecoDao.atualizar(this.funcionario.getEndereco());
            this.logradouroDao.atualizar(this.funcionario.getEndereco().getLogradouro());
            context.addMessage(null, new FacesMessage(FUNCIONARIO_UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.funcionario = new Funcionario();
        this.funcionarioValidator.showMessages();
        this.funcionarioValidator.clearMessages();
    }

    private void verficarFuncionarioSessao() {
        Integer funcionarioId = (Integer) Faces_Util.getHTTPSession().getAttribute("FuncionarioID");
        if (funcionarioId != null) {
            this.funcionario = funcionarioDao.buscarPorId(Funcionario.class, funcionarioId);
            Faces_Util.getHTTPSession().removeAttribute("FuncionarioID");
        }
    }

    private void initListUsuarios() {
        this.usuarios = this.usuarioDao.listarTodos(true);
    }

    private void initListCidades() {
        this.cidades = this.cidadeDao.listarTodos(true);
    }

    private void initListEstados() {
        this.estados = this.estadoDao.listarTodos(true);
    }

    private void initListPaises() {
        this.paises = this.paisDao.listarTodos(true);
    }

    public void setarPais() {
        this.funcionario.getEndereco().setPais(this.paisSelecionado);
        carregarEstado(paisSelecionado.getId());
    }

    public void setarEstado() {
        this.funcionario.getEndereco().setEstado(this.estadoSelecionado);
        carregarCidade(estadoSelecionado.getId());
    }

    public void setarCidade() {
        this.funcionario.getEndereco().setCidade(this.cidadeSelecionada);
    }

    public void setarUsuario() {
        this.funcionario.setUsuario(this.usuarioSelecionado);
    }

    private void carregarEstado(Integer paisId) {
        this.estados = this.estadoDao.listarTodos(true, paisId);
    }

    private void carregarCidade(Integer estadoId) {
        this.cidades = this.cidadeDao.listarTodos(true, estadoId);
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Pais> getPaises() {
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }

    public Pais getPaisSelecionado() {
        return paisSelecionado;
    }

    public void setPaisSelecionado(Pais paisSelecionado) {
        this.paisSelecionado = paisSelecionado;
    }

    public Estado getEstadoSelecionado() {
        return estadoSelecionado;
    }

    public void setEstadoSelecionado(Estado estadoSelecionado) {
        this.estadoSelecionado = estadoSelecionado;
    }

    public Cidade getCidadeSelecionada() {
        return cidadeSelecionada;
    }

    public void setCidadeSelecionada(Cidade cidadeSelecionada) {
        this.cidadeSelecionada = cidadeSelecionada;
    }

    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }

    public List<Pais> getPaisesFiltrados() {
        return paisesFiltrados;
    }

    public void setPaisesFiltrados(List<Pais> paisesFiltrados) {
        this.paisesFiltrados = paisesFiltrados;
    }

    public List<Estado> getEstadosFiltrados() {
        return estadosFiltrados;
    }

    public void setEstadosFiltrados(List<Estado> estadosFiltrados) {
        this.estadosFiltrados = estadosFiltrados;
    }

    public List<Cidade> getCidadesFiltradas() {
        return cidadesFiltradas;
    }

    public void setCidadesFiltradas(List<Cidade> cidadesFiltradas) {
        this.cidadesFiltradas = cidadesFiltradas;
    }

    public List<Usuario> getUsuariosFiltrados() {
        return usuariosFiltrados;
    }

    public void setUsuariosFiltrados(List<Usuario> usuariosFiltrados) {
        this.usuariosFiltrados = usuariosFiltrados;
    }

}
