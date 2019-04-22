package br.com.WebBakery.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import br.com.WebBakery.validator.EnderecoValidator;
import br.com.WebBakery.validator.FuncionarioValidator;

@Named
@ViewScoped
public class FuncionarioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    transient private EntityManager em;

    private FuncionarioDao funcionarioDao;
    private Funcionario funcionario;
    private List<Funcionario> funcionarios;
    private List<Funcionario> todosOsFuncionarios;
    private FuncionarioValidator funcionarioValidator;

    private EnderecoValidator enderecoValidator;

    private UsuarioDao usuarioDao;
    private Usuario usuarioSelecionado;
    private List<Usuario> usuarios;

    private PaisDao paisDao;
    private Pais paisSelecionado;
    private List<Pais> paises;

    private EstadoDao estadoDao;
    private Estado estadoSelecionado;
    private List<Estado> estados;

    private CidadeDao cidadeDao;
    private Cidade cidadeSelecionada;
    private List<Cidade> cidades;

    private LogradouroDao logradouroDao;

    private EnderecoDao enderecoDao;
    private List<Endereco> todosOsEnderecos;

    @Inject
    transient private FacesContext context;

    @PostConstruct
    private void init() {
        this.funcionarioDao = new FuncionarioDao(this.em);
        this.funcionario = new Funcionario();
        this.funcionarios = new ArrayList<>();
        this.todosOsFuncionarios = new ArrayList<>();
        this.funcionarioValidator = new FuncionarioValidator(this.funcionario);

        this.enderecoDao = new EnderecoDao(this.em);
        this.funcionario.setEndereco(new Endereco());
        this.funcionario.getEndereco().setPais(new Pais());
        this.funcionario.getEndereco().setEstado(new Estado());
        this.funcionario.getEndereco().setCidade(new Cidade());
        this.funcionario.getEndereco().setLogradouro(new Logradouro());
        this.enderecoValidator = new EnderecoValidator(this.funcionario.getEndereco());

        this.logradouroDao = new LogradouroDao(this.em);

        this.usuarioDao = new UsuarioDao(this.em);
        this.usuarioSelecionado = new Usuario();
        this.usuarios = this.usuarioDao.listarTodos(true);

        this.paisDao = new PaisDao(this.em);
        this.paisSelecionado = new Pais();
        this.paises = this.paisDao.listarTodos(true);

        this.estadoDao = new EstadoDao(this.em);
        this.estadoSelecionado = new Estado();
        this.estados = this.estadoDao.listarTodos(true);

        this.cidadeDao = new CidadeDao(this.em);
        this.cidadeSelecionada = new Cidade();
        this.cidades = this.cidadeDao.listarTodos(true);
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public List<Funcionario> getFuncionarios() {
        this.funcionarios = this.funcionarioDao.listarTodos(true);
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
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

    public List<Endereco> getTodosOsEnderecos() {
        this.todosOsEnderecos = this.enderecoDao.listarTodos();
        return todosOsEnderecos;
    }

    public void setTodosOsEnderecos(List<Endereco> todosOsEnderecos) {
        this.todosOsEnderecos = todosOsEnderecos;
    }

    public List<Funcionario> getTodosOsFuncionarios() {
        this.todosOsFuncionarios = this.funcionarioDao.listarTodos();
        return todosOsFuncionarios;
    }

    public void setTodosOsFuncionarios(List<Funcionario> todosOsFuncionarios) {
        this.todosOsFuncionarios = todosOsFuncionarios;
    }

    public String getDataNascimentoFormatada(Date datanascimento) throws ParseException {
        if (datanascimento == null) {
            return "";
        }

        SimpleDateFormat formatBra = new SimpleDateFormat("dd/MM/yyyy");
        String newData = formatBra.format(datanascimento);
        return newData;
    }

    @Transactional
    public void cadastrar() {
        if (this.funcionario.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        atualizarTela();
    }

    @Transactional
    public void carregar(Funcionario funcionario) {
        this.funcionarioValidator = new FuncionarioValidator(funcionario);
        this.enderecoValidator = new EnderecoValidator(funcionario.getEndereco());
        this.funcionario = funcionario;
    }

    @Transactional
    public void inativar(Funcionario funcionario) {
        funcionario.setAtivo(false);
        funcionario.getEndereco().setAtivo(false);
        this.funcionarioDao.atualizar(funcionario);
        this.enderecoDao.atualizar(funcionario.getEndereco());
        this.funcionarios = getFuncionarios();
    }

    private void efetuarAtualizacao() {
        if (funcionarioValidator.ehValido() && enderecoValidator.ehValido()) {
            this.funcionarioDao.atualizar(this.funcionario);
            this.enderecoDao.atualizar(this.funcionario.getEndereco());
            context.addMessage("formCadastroFuncionario:messages",
                               new FacesMessage("Funcionário atualizado com sucesso!"));
        }
    }

    private void efetuarCadastro() {
        todosOsFuncionarios = getTodosOsFuncionarios();
        todosOsEnderecos = getTodosOsEnderecos();
        if (!funcionarioValidator.existe(todosOsFuncionarios) && funcionarioValidator.ehValido()
                && !enderecoValidator.existe(todosOsEnderecos) && enderecoValidator.ehValido()) {

            this.funcionario.getEndereco().getLogradouro().setAtivo(true);
            this.funcionario.getEndereco().getLogradouro()
                    .setCidade(this.funcionario.getEndereco().getCidade());
            this.logradouroDao.cadastrar(this.funcionario.getEndereco().getLogradouro());

            this.funcionario.getEndereco().setAtivo(true);
            this.enderecoDao.cadastrar(this.funcionario.getEndereco());

            this.funcionario.setAtivo(true);
            this.funcionarioDao.cadastrar(this.funcionario);

            context.addMessage("formCadastroFuncionario:messages",
                               new FacesMessage("Funcionario cadastrado com sucesso!"));
        }
    }

    private void atualizarTela() {
        this.funcionario = new Funcionario();
        this.funcionarios = getFuncionarios();
        this.funcionarioValidator.mostrarMensagens();
        this.funcionarioValidator.clearMessages();
    }

    public void setarPais() {
        this.funcionario.getEndereco().setPais(this.paisSelecionado);
        carregarEstado(paisSelecionado.getId());
    }

    private void carregarEstado(Integer paisId) {
        this.estados = this.estadoDao.listarTodos(true, paisId);
    }

    private void carregarCidade(Integer paisId) {
        this.cidades = this.cidadeDao.listarTodos(true, paisId);
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

}
