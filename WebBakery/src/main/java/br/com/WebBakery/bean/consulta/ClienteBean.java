package br.com.WebBakery.bean.consulta;

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
import br.com.WebBakery.dao.ClienteDao;
import br.com.WebBakery.dao.EnderecoDao;
import br.com.WebBakery.dao.EstadoDao;
import br.com.WebBakery.dao.LogradouroDao;
import br.com.WebBakery.dao.PaisDao;
import br.com.WebBakery.dao.UsuarioDao;
import br.com.WebBakery.enums.TipoUsuario;
import br.com.WebBakery.model.Cidade;
import br.com.WebBakery.model.Cliente;
import br.com.WebBakery.model.Endereco;
import br.com.WebBakery.model.Estado;
import br.com.WebBakery.model.Logradouro;
import br.com.WebBakery.model.Pais;
import br.com.WebBakery.model.Usuario;
import br.com.WebBakery.util.FacesUtil;
import br.com.WebBakery.validator.ClienteValidator;
import br.com.WebBakery.validator.EnderecoValidator;

@Named
@ViewScoped
public class ClienteBean implements Serializable {

    private static final long serialVersionUID = -7615567443762847019L;

    private static final String REGISTERED_SUCCESSFULLY = "Cliente cadastrado com sucesso!";

    private static final String UPDATED_SUCCESSFULLY = "Cliente atualizado com sucesso!";

    @PersistenceContext
    private EntityManager em;
    @Inject
    transient private FacesContext context;

    private Cliente cliente;
    private ClienteDao clienteDao;

    private PaisDao paisDao;
    private List<Pais> paises;
    private List<Pais> paisesFiltrados;
    private Pais paisSelecionado;

    private EstadoDao estadoDao;
    private List<Estado> estados;
    private List<Estado> estadosFiltrados;
    private Estado estadoSelecionado;

    private CidadeDao cidadeDao;
    private List<Cidade> cidades;
    private List<Cidade> cidadesFiltradas;
    private Cidade cidadeSelecionada;

    private LogradouroDao logradouroDao;

    private EnderecoDao enderecoDao;

    private UsuarioDao usuarioDao;

    private ClienteValidator clienteValidator;
    private EnderecoValidator enderecoValidator;

    @PostConstruct
    private void init() {
        this.clienteDao = new ClienteDao(this.em);
        this.cliente = new Cliente();

        this.enderecoDao = new EnderecoDao(this.em);
        this.cliente.setEndereco(new Endereco());
        this.cliente.getEndereco().setPais(new Pais());
        this.cliente.getEndereco().setEstado(new Estado());
        this.cliente.getEndereco().setCidade(new Cidade());
        this.cliente.getEndereco().setLogradouro(new Logradouro());

        this.logradouroDao = new LogradouroDao(this.em);

        this.usuarioDao = new UsuarioDao(this.em);
        this.cliente.setUsuario(new Usuario());

        this.paisDao = new PaisDao(this.em);
        this.paisSelecionado = new Pais();

        this.estadoDao = new EstadoDao(this.em);
        this.estadoSelecionado = new Estado();

        this.cidadeDao = new CidadeDao(this.em);
        this.cidadeSelecionada = new Cidade();

        initListPaises();
        initListEstados();
        initListCidades();
        verficarClienteSessao();
    }

    @Transactional
    public void cadastrar() {
        this.clienteValidator = new ClienteValidator(this.cliente, this.em);
        this.enderecoValidator = new EnderecoValidator(this.cliente.getEndereco());
        if (this.cliente.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        atualizarTela();
    }

    private void efetuarCadastro() {
        if (clienteValidator.isValid() && enderecoValidator.ehValido()) {
            cadastrarLogradouroCliente();
            cadastrarEnderecoCliente();
            cadastrarUsuarioCliente();
            this.cliente.setAtivo(true);
            this.clienteDao.cadastrar(this.cliente);
            context.addMessage(null, new FacesMessage(REGISTERED_SUCCESSFULLY));
        }
    }

    private void cadastrarUsuarioCliente() {
        this.cliente.getUsuario().setAtivo(true);
        this.cliente.getUsuario().setTipo(TipoUsuario.CLIENTE);
        this.usuarioDao.cadastrar(this.cliente.getUsuario());
    }

    private void cadastrarEnderecoCliente() {
        this.cliente.getEndereco().setAtivo(true);
        this.enderecoDao.cadastrar(this.cliente.getEndereco());
    }

    private void cadastrarLogradouroCliente() {
        this.cliente.getEndereco().getLogradouro().setAtivo(true);
        this.cliente.getEndereco().getLogradouro().setCidade(cidadeSelecionada);
        this.logradouroDao.cadastrar(this.cliente.getEndereco().getLogradouro());
    }

    private void efetuarAtualizacao() {
        if (clienteValidator.isValid() && enderecoValidator.ehValido()) {
            this.clienteDao.atualizar(this.cliente);
            this.usuarioDao.atualizar(this.cliente.getUsuario());
            this.enderecoDao.atualizar(this.cliente.getEndereco());
            this.logradouroDao.atualizar(this.cliente.getEndereco().getLogradouro());
            context.addMessage(null, new FacesMessage(UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.cliente = new Cliente();
        this.clienteValidator.showMessages();
        this.clienteValidator.clearMessages();
    }

    private void verficarClienteSessao() {
        Integer clienteId = (Integer) FacesUtil.getHTTPSession().getAttribute("ClienteID");
        if (clienteId != null) {
            this.cliente = clienteDao.buscarPorId(clienteId);
            FacesUtil.getHTTPSession().removeAttribute("ClienteID");
        }
    }

    private void initListPaises() {
        this.paises = this.paisDao.listarTodos(true);
    }

    private void initListEstados() {
        this.estados = this.estadoDao.listarTodos(true);
    }

    private void initListCidades() {
        this.cidades = this.cidadeDao.listarTodos(true);
    }

    public void setarPais() {
        this.cliente.getEndereco().setPais(this.paisSelecionado);
        carregarEstado(paisSelecionado.getId());
    }

    public void setarEstado() {
        this.cliente.getEndereco().setEstado(this.estadoSelecionado);
        carregarCidade(estadoSelecionado.getId());
    }

    public void setarCidade() {
        this.cliente.getEndereco().setCidade(this.cidadeSelecionada);
    }

    private void carregarEstado(Integer paisId) {
        this.estados = this.estadoDao.listarTodos(true, paisId);
    }

    private void carregarCidade(Integer estadoId) {
        this.cidades = this.cidadeDao.listarTodos(true, estadoId);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Pais> getPaises() {
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }

    public Pais getPaisSelecionado() {
        return paisSelecionado;
    }

    public void setPaisSelecionado(Pais paisSelecionado) {
        this.paisSelecionado = paisSelecionado;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public Estado getEstadoSelecionado() {
        return estadoSelecionado;
    }

    public void setEstadoSelecionado(Estado estadoSelecionado) {
        this.estadoSelecionado = estadoSelecionado;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }

    public Cidade getCidadeSelecionada() {
        return cidadeSelecionada;
    }

    public void setCidadeSelecionada(Cidade cidadeSelecionada) {
        this.cidadeSelecionada = cidadeSelecionada;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public ClienteDao getClienteDao() {
        return clienteDao;
    }

    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public PaisDao getPaisDao() {
        return paisDao;
    }

    public void setPaisDao(PaisDao paisDao) {
        this.paisDao = paisDao;
    }

    public EstadoDao getEstadoDao() {
        return estadoDao;
    }

    public void setEstadoDao(EstadoDao estadoDao) {
        this.estadoDao = estadoDao;
    }

    public CidadeDao getCidadeDao() {
        return cidadeDao;
    }

    public void setCidadeDao(CidadeDao cidadeDao) {
        this.cidadeDao = cidadeDao;
    }

    public LogradouroDao getLogradouroDao() {
        return logradouroDao;
    }

    public void setLogradouroDao(LogradouroDao logradouroDao) {
        this.logradouroDao = logradouroDao;
    }

    public EnderecoDao getEnderecoDao() {
        return enderecoDao;
    }

    public void setEnderecoDao(EnderecoDao enderecoDao) {
        this.enderecoDao = enderecoDao;
    }

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public ClienteValidator getClienteValidator() {
        return clienteValidator;
    }

    public void setClienteValidator(ClienteValidator clienteValidator) {
        this.clienteValidator = clienteValidator;
    }

    public EnderecoValidator getEnderecoValidator() {
        return enderecoValidator;
    }

    public void setEnderecoValidator(EnderecoValidator enderecoValidator) {
        this.enderecoValidator = enderecoValidator;
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

}
