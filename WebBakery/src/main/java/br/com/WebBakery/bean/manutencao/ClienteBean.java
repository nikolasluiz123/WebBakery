package br.com.WebBakery.bean.manutencao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
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
import br.com.WebBakery.to.TOEstado;
import br.com.WebBakery.to.TOPais;
import br.com.WebBakery.util.HashTypeEnum;
import br.com.WebBakery.util.Hash_Util;
import br.com.WebBakery.validator.ClienteValidator;
import br.com.WebBakery.validator.EnderecoValidator;
import br.com.WebBakery.validator.UsuarioValidator;

@Named(ClienteBean.BEAN_NAME)
@ViewScoped
public class ClienteBean extends AbstractBaseRegisterMBean<TOCliente> {

    private static final long serialVersionUID = -7615567443762847019L;

    public static final String BEAN_NAME = "clienteBean";

    @Inject
    private ClienteDao clienteDao;
    @Inject
    private PaisDao paisDao;
    private List<TOPais> toPaises;
    private List<TOPais> toPaisesFiltrados;
    private TOPais toPaisSelecionado;
    @Inject
    private EstadoDao estadoDao;
    private List<TOEstado> toEstados;
    private List<TOEstado> toEstadosFiltrados;
    private TOEstado toEstadoSelecionado;
    @Inject
    private CidadeDao cidadeDao;
    private List<TOCidade> toCidades;
    private List<TOCidade> toCidadesFiltradas;
    private TOCidade toCidadeSelecionada;
    @Inject
    private LogradouroDao logradouroDao;
    @Inject
    private EnderecoDao enderecoDao;

    @Inject
    private UsuarioDao usuarioDao;

    private String senha;

    @PostConstruct
    private void init() {
        verificaObjetoSessao();

        if (getTo() == null) {
            resetTo();
        }

        this.toPaisSelecionado = new TOPais();
        this.toEstadoSelecionado = new TOEstado();
        this.toCidadeSelecionada = new TOCidade();
        
        initListPaises();
        initListEstados();
        initListCidades();
    }

    @Transactional
    public void cadastrar() {
        try {
            addValidators();
            if (isValid()) {
                cadastrarLogradouroCliente();
                cadastrarEnderecoCliente();
                cadastrarUsuarioCliente();
                this.getTo().setAtivo(true);
                this.clienteDao.salvar(this.getTo());
                showMessageSuccess();
            }
            atualizarTela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addValidators() {
        ClienteValidator clienteValidator = new ClienteValidator(getTo(), clienteDao);
        addValidator(clienteValidator);
        EnderecoValidator enderecoValidator = new EnderecoValidator(getTo().getToEndereco());
        addValidator(enderecoValidator);
        UsuarioValidator usuarioValidator = new UsuarioValidator(getTo().getToUsuario(),
                                                                 senha,
                                                                 usuarioDao);
        addValidator(usuarioValidator);
    }

    private void cadastrarUsuarioCliente() throws Exception {
        getTo().getToUsuario().setAtivo(true);
        getTo().getToUsuario().setTipo(TipoUsuario.CLIENTE);
        getTo().getToUsuario().setSenha(Hash_Util.generateHashMaxSecurity(this.senha));
        this.usuarioDao.salvar(this.getTo().getToUsuario());
    }

    private void cadastrarEnderecoCliente() throws Exception {
        this.getTo().getToEndereco().setAtivo(true);
        this.enderecoDao.salvar(this.getTo().getToEndereco());
    }

    private void cadastrarLogradouroCliente() throws Exception {
        this.getTo().getToEndereco().getToLogradouro().setAtivo(true);
        this.getTo().getToEndereco().getToLogradouro().setToCidade(toCidadeSelecionada);
        this.logradouroDao.salvar(this.getTo().getToEndereco().getToLogradouro());
    }

    private void initListPaises() {
        this.toPaises = new ArrayList<>();
        try {
            this.toPaises = this.paisDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListEstados() {
        this.toEstados = new ArrayList<>();
        try {
            this.toEstados = this.estadoDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListCidades() {
        this.toCidades = new ArrayList<>();
        try {
            this.toCidades = this.cidadeDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setarPais() {
        this.getTo().getToEndereco().setToPais(this.toPaisSelecionado);
        carregarEstado(toPaisSelecionado.getId());
    }

    public void setarEstado() {
        this.getTo().getToEndereco().setToEstado(this.toEstadoSelecionado);
        carregarCidade(toEstadoSelecionado.getId());
    }

    public void setarCidade() {
        this.getTo().getToEndereco().setToCidade(this.toCidadeSelecionada);
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

    public TOPais getToPaisSelecionado() {
        return toPaisSelecionado;
    }

    public void setToPaisSelecionado(TOPais toPaisSelecionado) {
        this.toPaisSelecionado = toPaisSelecionado;
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

    public TOEstado getToEstadoSelecionado() {
        return toEstadoSelecionado;
    }

    public void setToEstadoSelecionado(TOEstado toEstadoSelecionado) {
        this.toEstadoSelecionado = toEstadoSelecionado;
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

    public TOCidade getToCidadeSelecionada() {
        return toCidadeSelecionada;
    }

    public void setToCidadeSelecionada(TOCidade toCidadeSelecionada) {
        this.toCidadeSelecionada = toCidadeSelecionada;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    protected AbstractBaseDao<TOCliente> getDao() {
        return this.clienteDao;
    }

    @Override
    protected TOCliente getNewInstaceTO() {
        return new TOCliente();
    }

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

}
