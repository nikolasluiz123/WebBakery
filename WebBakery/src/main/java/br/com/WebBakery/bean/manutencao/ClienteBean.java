package br.com.WebBakery.bean.manutencao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.dao.CidadeDao;
import br.com.WebBakery.dao.ClienteDao;
import br.com.WebBakery.dao.EnderecoDao;
import br.com.WebBakery.dao.EstadoDao;
import br.com.WebBakery.dao.LogradouroDao;
import br.com.WebBakery.dao.PaisDao;
import br.com.WebBakery.dao.UsuarioDao;
import br.com.WebBakery.enums.TipoUsuario;
import br.com.WebBakery.to.TOCidade;
import br.com.WebBakery.to.TOCliente;
import br.com.WebBakery.to.TOEndereco;
import br.com.WebBakery.to.TOEstado;
import br.com.WebBakery.to.TOLogradouro;
import br.com.WebBakery.to.TOPais;
import br.com.WebBakery.to.TOUsuario;
import br.com.WebBakery.util.Hash_Util;
import br.com.WebBakery.validator.ClienteValidator;
import br.com.WebBakery.validator.EnderecoValidator;

@Named(ClienteBean.BEAN_NAME)
@ViewScoped
public class ClienteBean extends AbstractBaseRegisterMBean<TOCliente> {

    private static final long serialVersionUID = -7615567443762847019L;

    private static final String REGISTERED_SUCCESSFULLY = "Cliente cadastrado com sucesso!";

    private static final String UPDATED_SUCCESSFULLY = "Cliente atualizado com sucesso!";

    public static final String BEAN_NAME = "clienteBean";

    private TOCliente toCliente;

    @Inject
    private ClienteDao clienteDao;

    @Inject
    private PaisDao paisDao;
    private List<TOPais> toPaises;
    private List<TOPais> toPaisesFiltrados;
    private TOPais paisSelecionado;

    @Inject
    private EstadoDao estadoDao;
    private List<TOEstado> toEstados;
    private List<TOEstado> toEstadosFiltrados;
    private TOEstado estadoSelecionado;
    @Inject
    private CidadeDao cidadeDao;
    private List<TOCidade> toCidades;
    private List<TOCidade> toCidadesFiltradas;
    private TOCidade cidadeSelecionada;
    @Inject
    private LogradouroDao logradouroDao;
    @Inject
    private EnderecoDao enderecoDao;
    @Inject
    private UsuarioDao usuarioDao;
    private ClienteValidator clienteValidator;
    private EnderecoValidator enderecoValidator;
    private String senha;

    @PostConstruct
    private void init() {
        this.toCliente = new TOCliente();

        this.toCliente.setToEndereco(new TOEndereco());
        this.toCliente.getToEndereco().setToPais(new TOPais());
        this.toCliente.getToEndereco().setToEstado(new TOEstado());
        this.toCliente.getToEndereco().setToCidade(new TOCidade());
        this.toCliente.getToEndereco().setToLogradouro(new TOLogradouro());

        this.toCliente.setToUsuario(new TOUsuario());
        this.paisSelecionado = new TOPais();
        this.estadoSelecionado = new TOEstado();
        this.cidadeSelecionada = new TOCidade();

        initListPaises();
        initListEstados();
        initListCidades();
    }

    @Transactional
    public void cadastrar() {
        this.clienteValidator = new ClienteValidator(this.toCliente, this.senha);
        this.enderecoValidator = new EnderecoValidator(this.toCliente.getToEndereco());
        if (this.toCliente.getId() == null) {
            efetuarCadastro();
        } else {
            efetuarAtualizacao();
        }
        atualizarTela();
    }

    private void efetuarCadastro() {
        if (clienteValidator.isValid() && enderecoValidator.isValid()) {
            cadastrarLogradouroCliente();
            cadastrarEnderecoCliente();
            cadastrarUsuarioCliente();
            this.toCliente.setAtivo(true);

            try {
                this.clienteDao.cadastrar(this.toCliente);
            } catch (Exception e) {
                e.printStackTrace();
            }

            getContext().addMessage(null, new FacesMessage(REGISTERED_SUCCESSFULLY));
        }
    }

    private void cadastrarUsuarioCliente() {
        this.toCliente.getToUsuario().setAtivo(true);
        this.toCliente.getToUsuario().setTipo(TipoUsuario.CLIENTE);
        this.toCliente.getToUsuario().setSenha(Hash_Util.getHashCode(this.senha));
        try {
            this.usuarioDao.cadastrar(this.toCliente.getToUsuario());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cadastrarEnderecoCliente() {
        this.toCliente.getToEndereco().setAtivo(true);
        try {
            this.enderecoDao.cadastrar(this.toCliente.getToEndereco());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cadastrarLogradouroCliente() {
        this.toCliente.getToEndereco().getToLogradouro().setAtivo(true);
        this.toCliente.getToEndereco().getToLogradouro().setToCidade(cidadeSelecionada);
        try {
            this.logradouroDao.cadastrar(this.toCliente.getToEndereco().getToLogradouro());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void efetuarAtualizacao() {
        if (clienteValidator.isValid() && enderecoValidator.isValid()) {

            try {
                this.clienteDao.atualizar(this.toCliente);
                this.toCliente.getToUsuario().setSenha(Hash_Util.getHashCode(this.senha));
                this.usuarioDao.atualizar(this.toCliente.getToUsuario());
                this.enderecoDao.atualizar(this.toCliente.getToEndereco());
                this.logradouroDao.atualizar(this.toCliente.getToEndereco().getToLogradouro());
            } catch (Exception e) {
                e.printStackTrace();
            }

            getContext().addMessage(null, new FacesMessage(UPDATED_SUCCESSFULLY));
        }
    }

    private void atualizarTela() {
        this.toCliente = new TOCliente();
        this.clienteValidator.showMessages();
        this.clienteValidator.clearMessages();
    }

    private void initListPaises() {
        try {
            this.toPaises = this.paisDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListEstados() {
        try {
            this.toEstados = this.estadoDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListCidades() {
        try {
            this.toCidades = this.cidadeDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setarPais() {
        this.toCliente.getToEndereco().setToPais(this.paisSelecionado);
        carregarEstado(paisSelecionado.getId());
    }

    public void setarEstado() {
        this.toCliente.getToEndereco().setToEstado(this.estadoSelecionado);
        carregarCidade(estadoSelecionado.getId());
    }

    public void setarCidade() {
        this.toCliente.getToEndereco().setToCidade(this.cidadeSelecionada);
    }

    private void carregarEstado(Integer paisId) {
        try {
            this.toEstados = this.estadoDao.listarTodos(true, paisId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregarCidade(Integer estadoId) {
        try {
            this.toCidades = this.cidadeDao.listarTodos(true, estadoId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TOCliente getToCliente() {
        return toCliente;
    }

    public void setToCliente(TOCliente toCliente) {
        this.toCliente = toCliente;
    }

    public List<TOPais> getToPaises() {
        return toPaises;
    }

    public void setToPaises(List<TOPais> toPaises) {
        this.toPaises = toPaises;
    }

    public List<TOPais> getToPaisesFiltrados() {
        return toPaisesFiltrados;
    }

    public void setToPaisesFiltrados(List<TOPais> toPaisesFiltrados) {
        this.toPaisesFiltrados = toPaisesFiltrados;
    }

    public TOPais getPaisSelecionado() {
        return paisSelecionado;
    }

    public void setPaisSelecionado(TOPais paisSelecionado) {
        this.paisSelecionado = paisSelecionado;
    }

    public List<TOEstado> getToEstados() {
        return toEstados;
    }

    public void setToEstados(List<TOEstado> toEstados) {
        this.toEstados = toEstados;
    }

    public List<TOEstado> getToEstadosFiltrados() {
        return toEstadosFiltrados;
    }

    public void setToEstadosFiltrados(List<TOEstado> toEstadosFiltrados) {
        this.toEstadosFiltrados = toEstadosFiltrados;
    }

    public TOEstado getEstadoSelecionado() {
        return estadoSelecionado;
    }

    public void setEstadoSelecionado(TOEstado estadoSelecionado) {
        this.estadoSelecionado = estadoSelecionado;
    }

    public List<TOCidade> getToCidades() {
        return toCidades;
    }

    public void setToCidades(List<TOCidade> toCidades) {
        this.toCidades = toCidades;
    }

    public List<TOCidade> getToCidadesFiltradas() {
        return toCidadesFiltradas;
    }

    public void setToCidadesFiltradas(List<TOCidade> toCidadesFiltradas) {
        this.toCidadesFiltradas = toCidadesFiltradas;
    }

    public TOCidade getCidadeSelecionada() {
        return cidadeSelecionada;
    }

    public void setCidadeSelecionada(TOCidade cidadeSelecionada) {
        this.cidadeSelecionada = cidadeSelecionada;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
