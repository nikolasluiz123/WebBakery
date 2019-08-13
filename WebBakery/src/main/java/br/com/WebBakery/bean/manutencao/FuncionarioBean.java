package br.com.WebBakery.bean.manutencao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.WebBakery.abstractClass.AbstractBaseDao;
import br.com.WebBakery.abstractClass.AbstractBaseRegisterMBean;
import br.com.WebBakery.abstractClass.AbstractValidator;
import br.com.WebBakery.dao.CidadeDao;
import br.com.WebBakery.dao.EnderecoDao;
import br.com.WebBakery.dao.EstadoDao;
import br.com.WebBakery.dao.FuncionarioDao;
import br.com.WebBakery.dao.LogradouroDao;
import br.com.WebBakery.dao.PaisDao;
import br.com.WebBakery.dao.UsuarioDao;
import br.com.WebBakery.to.TOCidade;
import br.com.WebBakery.to.TOEndereco;
import br.com.WebBakery.to.TOEstado;
import br.com.WebBakery.to.TOFuncionario;
import br.com.WebBakery.to.TOLogradouro;
import br.com.WebBakery.to.TOPais;
import br.com.WebBakery.to.TOUsuario;
import br.com.WebBakery.validator.FuncionarioValidator;

@Named(FuncionarioBean.BEAN_NAME)
@ViewScoped
public class FuncionarioBean extends AbstractBaseRegisterMBean<TOFuncionario> {

    public static final String BEAN_NAME = "funcionarioBean";

    private static final long serialVersionUID = -3087884190174464470L;

    @Inject
    private FuncionarioDao funcionarioDao;
    private FuncionarioValidator funcionarioValidator;
    @Inject
    private UsuarioDao usuarioDao;
    private TOUsuario toUsuarioSelecionado;
    private List<TOUsuario> toUsuarios;
    private List<TOUsuario> toUsuariosFiltrados;
    @Inject
    private PaisDao paisDao;
    private TOPais toPaisSelecionado;
    private List<TOPais> toPaises;
    private List<TOPais> toPaisesFiltrados;
    @Inject
    private EstadoDao estadoDao;
    private TOEstado toEstadoSelecionado;
    private List<TOEstado> toEstados;
    private List<TOEstado> toEstadosFiltrados;
    @Inject
    private CidadeDao cidadeDao;
    private TOCidade toCcidadeSelecionada;
    private List<TOCidade> toCidades;
    private List<TOCidade> toCidadesFiltradas;
    @Inject
    private LogradouroDao logradouroDao;
    @Inject
    private EnderecoDao enderecoDao;

    @PostConstruct
    private void init() {
        verificaObjetoSessao();

        if (getTo() == null) {
            resetTo();
        }

        this.funcionarioDao = new FuncionarioDao();

        this.enderecoDao = new EnderecoDao();
        this.getTo().setToEndereco(new TOEndereco());
        this.getTo().getToEndereco().setToPais(new TOPais());
        this.getTo().getToEndereco().setToEstado(new TOEstado());
        this.getTo().getToEndereco().setToCidade(new TOCidade());
        this.getTo().getToEndereco().setToLogradouro(new TOLogradouro());

        this.logradouroDao = new LogradouroDao();

        this.usuarioDao = new UsuarioDao();
        this.toUsuarioSelecionado = new TOUsuario();

        this.paisDao = new PaisDao();
        this.toPaisSelecionado = new TOPais();

        this.estadoDao = new EstadoDao();
        this.toEstadoSelecionado = new TOEstado();

        this.cidadeDao = new CidadeDao();
        this.toCcidadeSelecionada = new TOCidade();

        initListPaises();
        initListEstados();
        initListCidades();
        initListUsuarios();

    }

    @Transactional
    public void cadastrar() {
        try {
            this.funcionarioValidator = new FuncionarioValidator(this.getTo());
            if (getValidator().isValid()) {
                cadastrarLogradouroFuncionario();
                cadastrarEnderecoFuncionario();
                getTo().setAtivo(true);
                this.funcionarioDao.salvar(this.getTo());
                showMessageSuccess();
            }
            atualizarTela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cadastrarEnderecoFuncionario() throws Exception {
        this.getTo().getToEndereco().setAtivo(true);
        this.enderecoDao.salvar(this.getTo().getToEndereco());
    }

    private void cadastrarLogradouroFuncionario() throws Exception {
        this.getTo().getToEndereco().getToLogradouro().setAtivo(true);
        this.getTo().getToEndereco().getToLogradouro()
                .setToCidade(this.getTo().getToEndereco().getToCidade());
        this.logradouroDao.salvar(this.getTo().getToEndereco().getToLogradouro());
    }

    private void initListUsuarios() {
        try {
            this.toUsuarios = this.usuarioDao.listarTodos(true);
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

    private void initListEstados() {
        try {
            this.toEstados = this.estadoDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListPaises() {
        try {
            this.toPaises = this.paisDao.listarTodos(true);
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
        this.getTo().getToEndereco().setToCidade(this.toCcidadeSelecionada);
    }

    public void setarUsuario() {
        this.getTo().setToUsuario(this.toUsuarioSelecionado);
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

    public TOUsuario getToUsuarioSelecionado() {
        return toUsuarioSelecionado;
    }

    public void setToUsuarioSelecionado(TOUsuario toUsuarioSelecionado) {
        this.toUsuarioSelecionado = toUsuarioSelecionado;
    }

    public List<TOUsuario> getToUsuarios() {
        return toUsuarios;
    }

    public void setToUsuarios(List<TOUsuario> toUsuarios) {
        this.toUsuarios = toUsuarios;
    }

    public List<TOUsuario> getToUsuariosFiltrados() {
        return toUsuariosFiltrados;
    }

    public void setToUsuariosFiltrados(List<TOUsuario> toUsuariosFiltrados) {
        this.toUsuariosFiltrados = toUsuariosFiltrados;
    }

    public TOPais getToPaisSelecionado() {
        return toPaisSelecionado;
    }

    public void setToPaisSelecionado(TOPais toPaisSelecionado) {
        this.toPaisSelecionado = toPaisSelecionado;
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

    public TOEstado getToEstadoSelecionado() {
        return toEstadoSelecionado;
    }

    public void setToEstadoSelecionado(TOEstado toEstadoSelecionado) {
        this.toEstadoSelecionado = toEstadoSelecionado;
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

    public TOCidade getToCcidadeSelecionada() {
        return toCcidadeSelecionada;
    }

    public void setToCcidadeSelecionada(TOCidade toCcidadeSelecionada) {
        this.toCcidadeSelecionada = toCcidadeSelecionada;
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

    @Override
    protected AbstractBaseDao<TOFuncionario> getDao() {
        return funcionarioDao;
    }

    @Override
    public AbstractValidator getValidator() {
        return funcionarioValidator;
    }

    @Override
    protected TOFuncionario getNewInstaceTO() {
        return new TOFuncionario();
    }

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

}
