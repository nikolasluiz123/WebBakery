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
import br.com.WebBakery.dao.EnderecoDao;
import br.com.WebBakery.dao.EstadoDao;
import br.com.WebBakery.dao.FuncionarioDao;
import br.com.WebBakery.dao.LogradouroDao;
import br.com.WebBakery.dao.PaisDao;
import br.com.WebBakery.dao.UsuarioDao;
import br.com.WebBakery.to.TOCidade;
import br.com.WebBakery.to.TOEstado;
import br.com.WebBakery.to.TOFuncionario;
import br.com.WebBakery.to.TOPais;
import br.com.WebBakery.to.TOUsuario;
import br.com.WebBakery.validator.EnderecoValidator;
import br.com.WebBakery.validator.FuncionarioValidator;

@Named(FuncionarioBean.BEAN_NAME)
@ViewScoped
public class FuncionarioBean extends AbstractBaseRegisterMBean<TOFuncionario> {

    public static final String BEAN_NAME = "funcionarioBean";

    private static final long serialVersionUID = -3087884190174464470L;

    @Inject
    private FuncionarioDao funcionarioDao;
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
    private TOCidade toCidadeSelecionada;
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

        this.toUsuarioSelecionado = new TOUsuario();
        this.toPaisSelecionado = new TOPais();
        this.toEstadoSelecionado = new TOEstado();
        this.toCidadeSelecionada = new TOCidade();

        initListPaises();
        initListEstados();
        initListCidades();
        initListUsuarios();

    }

    @Transactional
    public void cadastrar() {
        try {
            addValidators();
            if (isValid()) {
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

    private void addValidators() {
        FuncionarioValidator funcionarioValidator = new FuncionarioValidator(getTo(),
                                                                             this.funcionarioDao);
        addValidator(funcionarioValidator);
        EnderecoValidator enderecoValidator = new EnderecoValidator(getTo().getToEndereco());
        addValidator(enderecoValidator);
    }

    private void cadastrarEnderecoFuncionario() throws Exception {
        this.getTo().getToEndereco().setAtivo(true);
        this.enderecoDao.salvar(this.getTo().getToEndereco());
    }

    private void cadastrarLogradouroFuncionario() throws Exception {
        getTo().getToEndereco().getToLogradouro().setAtivo(true);
        getTo().getToEndereco().getToLogradouro()
                .setToCidade(getTo().getToEndereco().getToCidade());
        this.logradouroDao.salvar(getTo().getToEndereco().getToLogradouro());
    }

    private void initListUsuarios() {
        this.toUsuarios = new ArrayList<>();
        try {
            this.toUsuarios = this.usuarioDao.listarTodos(true);
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

    private void initListEstados() {
        this.toEstados = new ArrayList<>();
        try {
            this.toEstados = this.estadoDao.listarTodos(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListPaises() {
        this.toPaises = new ArrayList<>();
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
        this.getTo().getToEndereco().setToCidade(this.toCidadeSelecionada);
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

    public TOCidade getToCidadeSelecionada() {
        return toCidadeSelecionada;
    }

    public void setToCidadeSelecionada(TOCidade toCidadeSelecionada) {
        this.toCidadeSelecionada = toCidadeSelecionada;
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
    protected TOFuncionario getNewInstaceTO() {
        return new TOFuncionario();
    }

    @Override
    protected String getBeanName() {
        return BEAN_NAME;
    }

}
